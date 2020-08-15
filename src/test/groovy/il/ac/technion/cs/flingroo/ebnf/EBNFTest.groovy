package il.ac.technion.cs.flingroo.ebnf

import il.ac.technion.cs.flingroo.ebnf.element.Variable
import spock.lang.Specification
import static il.ac.technion.cs.flingroo.ebnf.element.VariableTest.Vars.*
import static il.ac.technion.cs.flingroo.ebnf.EBNF.*

/**
 * @author Noam Rotem
 */
class EBNFTest extends Specification {
    enum Vars implements Variable {A, B}

    def "Test context-less grammar"() {
        when:
            A >> B

        then:
            thrown IllegalStateException
    }
}
