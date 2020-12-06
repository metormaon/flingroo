package il.ac.technion.cs.flingroo.ebnf.element

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Noam Rotem
 */
class RuleElementTest extends Specification {
    class TestRuleElement implements RuleElement {

    }

    @Shared
    RuleElement r1 = new TestRuleElement()

    @Shared
    RuleElement r2 = new TestRuleElement()

    void setup() {
        r1
        r2 = new TestRuleElement()
    }

    @Unroll
    def "Test #action yields #result"() {
        given:

        expect:
            action[0].toString() == result.toString()

        where:
            action              | result
            [r1 & r2]           | new AndList(r1, r2)
            [r1 & "abc"]        | new AndList(r1, new Token("abc"))
            [r1 & [r2]]         | new AndList(r1, new Token("abc"))
            [r1 & ["abc"]]      | new AndList(r1, new Token("abc"))
            [r1 & {r2}]         | new AndList(r1, new Token("abc"))
            [r1 & {"abc"}]      | new AndList(r1, new Token("abc"))
            [r1 & {r2 | "abc"}] | new AndList(r1, new Token("abc"))

        [r1 | r2]       | new OrList(r1, r2)
            [r1 | "abc"]      | new OrList(r1, new Token("abc"))
    }
}
