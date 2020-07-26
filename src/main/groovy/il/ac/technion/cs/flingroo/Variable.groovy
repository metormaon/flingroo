package il.ac.technion.cs.flingroo

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