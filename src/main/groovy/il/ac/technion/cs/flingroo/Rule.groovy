package il.ac.technion.cs.flingroo

/**
 * @author Noam Rotem
 */
class Rule {
    private final Variable variable
    private final RuleElement derivedTo

    Rule(Variable v, RuleElement derivedTo) {
        variable = v
        this.derivedTo = derivedTo
    }

    @Override
    String toString() {
        variable.toString() + " <- " + derivedTo.toString()
    }
}
