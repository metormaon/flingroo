package il.ac.technion.cs.flingroo.ebnf.element

import il.ac.technion.cs.flingroo.ebnf.EBNF
import org.junit.jupiter.api.Test

import static il.ac.technion.cs.flingroo.ebnf.element.VariableTest.Vars.*
import static il.ac.technion.cs.flingroo.ebnf.EBNF.ebnf
import static org.junit.jupiter.api.Assertions.*

/**
 * @author Noam Rotem
 */
class VariableTest {
    enum Vars implements Variable {A, B}

    @Test
    void test() {
        EBNF grammar = ebnf {
            A >> B
        }

        assertEquals("""
A:
  B
        """.trim(), grammar.toString().trim())
    }
}
