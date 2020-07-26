package il.ac.technion.cs.flingroo

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
