package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
class ExplicitToken implements RuleElement {
    String regex

    ExplicitToken(String regex) {
        this.regex = regex
    }

    @Override
    String toString() {
        "\"" + regex + "\""
    }
}
