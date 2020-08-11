package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
class AndList {
    @Delegate
    List<RuleElement> elements = []

    AndList(RuleElement...e) {
        elements.addAll(e)
    }

    AndList and(RuleElement e) {
        elements.add(e)
        this
    }

    AndList and(Closure<RuleElement> c) {
        RuleElement e = c()
        elements.add(new ZeroOrMore(e))
        this
    }

    AndList and(List<RuleElement> l) {
        elements.add(new ZeroOrOne(l[0]))
        this
    }
}
