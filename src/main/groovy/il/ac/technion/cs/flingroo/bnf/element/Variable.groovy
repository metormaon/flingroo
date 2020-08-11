package il.ac.technion.cs.flingroo.bnf.element

import il.ac.technion.cs.flingroo.bnf.BNF

/**
 * @author Noam Rotem
 */
trait Variable extends LabeledRuleElement implements Comparable<RuleElement> {
    @Override
    int compareTo(RuleElement e) {
        BNF.add(new Rule(this, e))
        0
    }
}