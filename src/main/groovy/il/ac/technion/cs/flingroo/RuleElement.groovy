package il.ac.technion.cs.flingroo

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

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
