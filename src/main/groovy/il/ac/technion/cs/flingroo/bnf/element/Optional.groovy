package il.ac.technion.cs.flingroo.bnf.element
/**
 * @author Noam Rotem
 */
class Optional implements RuleElement {
    RuleElement optional

    Optional(RuleElement e) {
        optional = e
    }

    @Override
    String toString() {
        "(" + optional.toString() + ")?"
    }
}
