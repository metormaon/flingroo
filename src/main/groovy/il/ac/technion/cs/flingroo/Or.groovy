package il.ac.technion.cs.flingroo

/**
 * @author Noam Rotem
 */
class Or extends RuleElementList {
    Or(RuleElement... e) {
        super(e)
    }

    @Override
    String toString() {
        "(" + collect{it.toString()}.join(" | ") + ")"
    }
}
