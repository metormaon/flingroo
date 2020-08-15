package il.ac.technion.cs.flingroo.bnf

import il.ac.technion.cs.flingroo.bnf.element.Rule
import il.ac.technion.cs.flingroo.bnf.element.Terminal
import il.ac.technion.cs.flingroo.bnf.element.Variable

/**
 * @author Noam Rotem
 */

import static il.ac.technion.cs.flingroo.bnf.BNF.V.*
import static il.ac.technion.cs.flingroo.bnf.BNF.T.*

class BNF {
    private static final ThreadLocal<BNF> context = new ThreadLocal<BNF>()

    private BNF() {}

    private final Set<Rule> rules = []

    Variable root

    private static Variable findRoot(BNF bnf) {
        null
    }

    static void add(Rule r) {
        context.get().rules.add(r)
    }

    private static BNF process(Closure c) {
        context.set(new BNF())
        c()
        BNF bnf = context.get()
        context.remove()
        bnf
    }

    static BNF bnf(Closure c) {
        BNF bnf = process c
        bnf.root = findRoot bnf
        bnf
    }

    static BNF bnf(Variable v, Closure c) {
        BNF bnf = process c
        bnf.root = v
        bnf
    }

    static enum V implements Variable {E, F, G}
    static enum T implements Terminal {T1, T2("df"), T3
        T() {}
        T(String label) {
            this.label = label
        }
    }

    @Override
    String toString() {
        return rules.collect{it.toString()}.join("\n")
    }

    static void main(String[] args) {
        BNF grammar = bnf(E) {
            E <- T1 + T2 + ~F
            F <- ~(G | T3)
        }

        println(grammar)
    }
}
