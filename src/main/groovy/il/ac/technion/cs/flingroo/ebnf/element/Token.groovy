package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
class Token implements RuleElement {
    String regex

    Token(String regex) {
        this.regex = regex
    }

    @Override
    String toString() {
        "\"" + regex + "\""
    }
}
