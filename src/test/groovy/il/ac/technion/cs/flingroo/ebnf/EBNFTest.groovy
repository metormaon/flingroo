package il.ac.technion.cs.flingroo.ebnf

import il.ac.technion.cs.flingroo.ebnf.element.Token
import il.ac.technion.cs.flingroo.ebnf.element.Variable
import spock.lang.*

import static il.ac.technion.cs.flingroo.ebnf.EBNFTest.T.*
import static il.ac.technion.cs.flingroo.ebnf.EBNFTest.V.*
import static il.ac.technion.cs.flingroo.ebnf.EBNF.*

/**
 * @author Noam Rotem
 */
class EBNFTest extends Specification {
    static enum V implements Variable {A, B, C}

    static enum T implements Token {D, E, F}

    def "grammar"() {
        when:
            EBNF grammar = ebnf(A) {
                A >> B & C | [D] & E & [E]
                A >> B & C | [D] & {E} & [E]
                A >> B & C | {D} & B & {E}
                A >> B | D & E & {E}
                A >> C & [B]
                A >> [B] & C
            }

        then:
            1 == 1
            println(grammar)
    }
}
