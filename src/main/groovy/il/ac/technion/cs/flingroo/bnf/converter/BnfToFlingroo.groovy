/**
 * @author Noam Rotem
 */

package il.ac.technion.cs.flingroo.bnf.converter

import java.util.regex.Matcher

class BnfToFlingroo {
    final String text
    final Set<String> variables = []
    final Map<String, String> terminals = [:]
    final Map<String, String> terminalImages = [:]
    final Map<String, String> escapeeToTemp = [:]
    final Map<String, String> tempToEscapee = [:]

    int terminalCount = 0
    int escapeCount = 0

    BnfToFlingroo(String text) {
        this.text = text
    }

    String bnfToFlingroo() {
        def ruleText = ""<<""

        def escapedText = escape(text)

        escapedText.split("\n").each {
            String line = it.trim()

            try {
                if (!line.isEmpty() && !line.startsWith("#")) {
                    def parts = line.split("::=")

                    def variable = makeVar(parts[0])
                    variables << variable

                    def ors = parts[1].split ("\\s*\\|\\s*")

                    def convertedOrs = []

                    for (or in ors) {
                        def items = []
                        def item = ''<<''
                        def inVar = false

                        for (c in or) {
                            if (c == '<') {
                                inVar = true
                                continue
                            }

                            if (inVar) {
                                if (c == '<') {
                                    inVar = false
                                    item << '<<'
                                }

                                if (c == '>') {
                                    items += makeVar("<${item.toString()}>")
                                    item = '' << ''
                                    inVar = false
                                } else {
                                    item << c
                                }
                            } else if (c == '?') {
                                items[items.size()-1] = "~" + items[items.size()-1]
                            } else if (c != ' ') {
                                item << c
                            } else if (item.length() > 0) {
                                String terminal, image
                                (terminal, image) = makeTerminal(item.toString())
                                items += terminal
                                terminals[terminal] = image
                                terminalImages[image] = terminal

                                item = ''<<''
                            }
                        }

                        if (item.length() > 0) {
                            if (inVar) {
                                items += makeVar("<${item.toString()}>")
                            } else {
                                String terminal, image
                                (terminal, image) = makeTerminal(item.toString())
                                items += terminal
                                terminals[terminal] = image
                                terminalImages[image] = terminal
                            }

                        }
                        convertedOrs += items.join(" + ")
                    }
                    ruleText << " "*12 << variable << " <- (" << convertedOrs.join(" | ") << ")\n"
                }
            } catch (Exception e) {
                println "Failed on $line"
                throw e
            }
        }

        """
package examples

import il.ac.technion.cs.flingroo.bnf.element.Terminal
import il.ac.technion.cs.flingroo.bnf.element.Variable
import il.ac.technion.cs.flingroo.bnf.BNF

import static examples.JavaBNF.V.*
import static examples.JavaBNF.T.*

class JavaBNF {
    static enum V implements Variable {"""<<variables.join(", ")<<"""}
    static enum T implements Terminal {"""<<declaredTerminals()<<"""
        T() {}
        T(String label) {
            this.label = label
        }
    }

    static void main(String[] args) {
        BNF grammar = BNF.bnf(CompilationUnit) {
            """<<ruleText<<"""       
        }

        println(grammar)
    }
}
"""

    }

    static String makeVar(String varString) {
        def match = (varString.replace("-", " ") =~ /<\s*([^>]+)\s*>/)

        match[0][1].split().stream().map {String word -> word.capitalize()}.collect().join("")
    }

    Tuple makeTerminal(String s) {
        if (s.contains("ESCAPED_")) {
            Matcher matches = (s =~ /(ESCAPED_[0-9]+_)/)

            for (match in matches) {
                String temp = match[1]
                s = s.replace(temp, tempToEscapee[temp].substring(1))
            }

            s = s.replace("\\", "\\\\").replace("\"", "\\\"")
        }

        def terminal

        if (s.matches("[a-zA-Z]+")) {
            terminal = s.toUpperCase()
        } else {
            terminal = terminalImages[s] ?: "T" + terminalCount++
        }

        new Tuple(terminal, s)
    }

    String declaredTerminals() {
        def declarations = []
        terminals.each {key, val ->
            if (key == val) {
                declarations += key
            } else {
                declarations += "$key(\"$val\")"
            }
        }
        declarations.join(", ")
    }

    String escape(String s) {
        int index = s.indexOf('$')

        while (index >= 0) {
            def escapee = s.substring(index, index+2)

            if (!escapeeToTemp.containsKey(escapee)) {
                def temp = "ESCAPED_" + escapeCount++ + "_"
                escapeeToTemp[escapee] = temp
                tempToEscapee[temp] = escapee
            }

            index = s.indexOf('$', index + 1)
        }

        //TODO: replacing could be much more elegant (build the string while escaping)
        escapeeToTemp.each { escapee, temp ->
            s = s.replace(escapee, temp)
        }

        s
    }

    static void main(String[] args) {
        //TODO: use args if this class survives
        //TODO: Move resource to test resources, when gradle stops hiding it from classpath
        String fileText = ClassLoader.getSystemResource('java-bnf.txt').text

        BnfToFlingroo parser = new BnfToFlingroo(fileText)

        //TODO: write to gen-test
        println(parser.bnfToFlingroo())
    }
}
