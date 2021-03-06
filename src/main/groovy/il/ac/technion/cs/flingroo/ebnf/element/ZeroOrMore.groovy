package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
class ZeroOrMore implements RuleElement {
    List<RuleElement> elements = []

    ZeroOrMore(RuleElement e) {
        elements.add(e)
    }

    ZeroOrMore(List<RuleElement> l) {
        elements.addAll(l)
    }

    @Override
    String toString() {
        "{" + elements.join(" ") + "}"
    }
}
