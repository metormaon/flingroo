package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
class ZeroOrOne implements RuleElement {
    List<RuleElement> elements = []

    ZeroOrOne(RuleElement e) {
        elements.add(e)
    }

    ZeroOrOne(List<RuleElement> l) {
        elements.addAll(l)
    }

    @Override
    String toString() {
        "[" + elements.join(" ") + "]"
    }
}
