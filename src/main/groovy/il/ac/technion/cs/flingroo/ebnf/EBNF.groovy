package il.ac.technion.cs.flingroo.ebnf

import il.ac.technion.cs.flingroo.ebnf.element.Rule
import il.ac.technion.cs.flingroo.ebnf.element.RuleElement
import il.ac.technion.cs.flingroo.ebnf.element.Variable
import il.ac.technion.cs.flingroo.ebnf.element.ZeroOrMore

/**
 * @author Noam Rotem
 */
class EBNF {
    private static final ThreadLocal<EBNF> context = new ThreadLocal<>()

    private EBNF() {}

    private final Set<Rule> rules = []

    Variable root

    private static Variable findRoot(EBNF ebnf) {
        null
    }

    static Rule add(Rule r) {
        context.get().rules.add(r)
        r
    }

    private static EBNF process(Closure<Rule> c) {
        context.set(new EBNF())
        c()
        EBNF ebnf = context.get()
        context.remove()
        ebnf
    }

    static EBNF ebnf(Closure c) {
        EBNF ebnf = process c
        ebnf.root = findRoot ebnf
        ebnf
    }

    static EBNF ebnf(Variable v, Closure<Rule> c) {
        EBNF ebnf = process c
        ebnf.root = v
        ebnf
    }

    @Override
    String toString() {
        return rules.collect{it.toString()}.join("\n")
    }
}
