package il.ac.technion.cs.fling.examples

import il.ac.technion.cs.fling.BNF;
import il.ac.technion.cs.fling.GroovyBNF;
import il.ac.technion.cs.fling.Terminal;
import il.ac.technion.cs.fling.Variable;

@SuppressWarnings("NonAsciiCharacters")
trait FluentLanguageAPI<Σ extends Enum<Σ> & Terminal, V extends Enum<V> & Variable> {
    String name() {
        return this.getClass().getSimpleName();
    }

    abstract BNF BNF();
    // TODO consider getting enums via reflection
    abstract Class<Σ> Σ();

    abstract Class<V> V();
}
