package il.ac.technion.cs.flingroo

/**
 * @author Noam Rotem
 */
class AndThen extends RuleElementList {
    AndThen(RuleElement... e) {
        super(e)
    }

    @Override
    String toString() {
        "(" + collect{it.toString()}.join(" ") + ")"
    }
}
