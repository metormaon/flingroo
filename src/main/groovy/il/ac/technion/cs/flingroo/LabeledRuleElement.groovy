package il.ac.technion.cs.flingroo

import groovy.transform.SelfType

/**
 * @author Noam Rotem
 */
@SelfType(Enum)
trait LabeledRuleElement extends RuleElement {
    String label = name()

    @Override
    String toString() {
        label
    }
}
