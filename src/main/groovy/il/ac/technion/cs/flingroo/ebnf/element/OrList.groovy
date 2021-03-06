package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
class OrList {
    @Delegate
    List<RuleElement> elements = []

    OrList(RuleElement...e) {
        elements.addAll(e)
    }

    OrList or(RuleElement e) {
        elements.add(e)
        this
    }

    OrList or(Closure<RuleElement> c) {
        elements.add(new ZeroOrMore(c()))
        this
    }

    OrList or(List<RuleElement> l) {
        elements.add(new ZeroOrOne(l[0]))
        this
    }
}
