package il.ac.technion.cs.flingroo.ebnf.extension

import il.ac.technion.cs.flingroo.ebnf.element.AndList
import il.ac.technion.cs.flingroo.ebnf.element.RuleElement
import il.ac.technion.cs.flingroo.ebnf.element.ZeroOrMore
import il.ac.technion.cs.flingroo.ebnf.element.ZeroOrOne

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

    static AndList and(List<RuleElement> self, RuleElement e) {
        new AndList(new ZeroOrOne(self[0]), e)
    }

    static AndList and(List<RuleElement> self, Closure<RuleElement> c) {
        new AndList(new ZeroOrOne(self[0]), new ZeroOrMore(c()))
    }

    static AndList and(List<RuleElement> self, List<RuleElement> l) {
        new AndList(new ZeroOrOne(self[0]), new ZeroOrOne(l[0]))
    }
}
