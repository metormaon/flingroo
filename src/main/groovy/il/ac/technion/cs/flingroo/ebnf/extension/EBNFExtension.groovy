package il.ac.technion.cs.flingroo.ebnf.extension

import il.ac.technion.cs.flingroo.ebnf.element.*

/**
 * @author Noam Rotem
 */
class EBNFExtension {
    static AndList and(Closure<RuleElement> self, RuleElement e) {
        new AndList(new ZeroOrMore(self()), e)
    }

    static AndList and(Closure<RuleElement> self, Closure<RuleElement> c) {
        new AndList(new ZeroOrMore(self()), new ZeroOrMore(c()))
    }

    static AndList and(Closure<RuleElement> self, List<RuleElement> l) {
        new AndList(new ZeroOrMore(self()), new ZeroOrOne(l[0]))
    }

    static AndList and(Closure<RuleElement> self, String s) {
        new AndList(new ZeroOrMore(self()), new ExplicitToken(s))
    }

    static AndList and(String self, RuleElement r) {
        new AndList(new ExplicitToken(self), r)
    }

    static AndList and(String self, List<RuleElement> l) {
        new AndList(new ExplicitToken(self), new ZeroOrOne(l[0]))
    }

    static AndList and(String self, Closure<?> c) {
        Object o = c()

        if (o instanceof RuleElement) {
            new AndList(new ExplicitToken(self), o)
        } else if (o instanceof AndList) {
            List<RuleElement> lst = (o as AndList).getElements()
            lst.add(0, new ExplicitToken(self))
            new AndList(lst)
        } else throw new RuntimeException("Illegal closure type")
    }

    static AndList and(String self, String s) {
        new AndList(new ExplicitToken(self), new ExplicitToken(s))
    }

    static AndList and(List<RuleElement> self, RuleElement e) {
        new AndList(new ZeroOrOne(self[0]), e)
    }

    static AndList and(List<RuleElement> self, Closure<RuleElement> c) {
        new AndList(new ZeroOrOne(self[0]), new ZeroOrMore(c()))
    }

    static AndList and(List<RuleElement> self, List<RuleElement> l) {
        new AndList(new ZeroOrOne(self[0]), new ZeroOrOne(l[0]))
    }

    static AndList and(List<RuleElement> self, String s) {
        new AndList(new ZeroOrOne(self[0]), new ExplicitToken(s))
    }
}
