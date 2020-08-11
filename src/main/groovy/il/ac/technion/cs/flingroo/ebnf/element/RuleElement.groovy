package il.ac.technion.cs.flingroo.ebnf.element

/**
 * @author Noam Rotem
 */
trait RuleElement {
    AndList and(RuleElement e) {
        new AndList(this, e)
    }

    AndList and(Closure<RuleElement> c) {
        RuleElement e = c()
        new AndList(this, new ZeroOrMore(e))
    }

    AndList and(List<RuleElement> l) {
        new AndList(this, new ZeroOrOne(l[0]))
    }

//    RuleElement getAt(RuleElement e) {
//        return e
//    }
//
//    RuleElement and(RuleElement e) {
//        return e
//    }
//
//    RuleElement and(Closure<RuleElement> ce) {
//        return ce()
//    }
//
//    RuleElement and(List<RuleElement> le) {
//        return le[0]
//    }
//
//    RuleElement or(Closure<RuleElement> ce) {
//        return ce()
//    }
//
//
//    RuleElement or(List<RuleElement> le) {
//        return le[0]
//    }
}
