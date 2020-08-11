package il.ac.technion.cs.flingroo.bnf.element
/**
 * @author Noam Rotem
 */
trait RuleElement {
    RuleElement negative() {
        this
    }

    RuleElement plus(RuleElement e) {
        new AndThen(this, e)
    }

    RuleElement or(RuleElement e) {
        new Or(this, e)
    }

    RuleElement bitwiseNegate() {
        new Optional(this)
    }
}
