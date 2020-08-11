package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
class ZeroOrOne implements RuleElement {
    RuleElement element

    ZeroOrOne(RuleElement e) {
        element = e
    }

    AndList and(RuleElement e) {
        new AndList(this, e)
    }

    AndList and(Closure<RuleElement> c) {
        new AndList(this, new ZeroOrMore(c()))
    }

    AndList and(List<RuleElement> l) {
        new AndList(this, new ZeroOrOne(l[0]))
    }

    @Override
    String toString() {
        "[" + element.toString() + "]"
    }
}
