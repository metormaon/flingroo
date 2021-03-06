package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
class RuleOption {
    @Delegate
    List<RuleElement> sequence = []

    RuleOption() {}

    RuleOption(RuleElement e) {
        sequence.add(e)
    }

    RuleOption(AndList l) {
        sequence.addAll(l)
    }

    @Override
    String toString() {
        sequence.collect{it.toString()}.join(" ")
    }
}
