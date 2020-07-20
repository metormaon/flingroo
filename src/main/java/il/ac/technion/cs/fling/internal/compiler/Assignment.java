package il.ac.technion.cs.fling.internal.compiler;

import il.ac.technion.cs.fling.Terminal;

import java.util.Arrays;
import java.util.List;

/**
 * Recording of method call in fluent API invocation.
 *
 * @author Ori Roth
 */
public class Assignment {
  /**
   * Inducing terminal.
   */
  public final Terminal σ;
  /**
   * Arguments supplied.
   */
  public final List<Object> arguments;

  public Assignment(final Terminal σ, final Object... arguments) {
    this.σ = σ;
    this.arguments = Arrays.asList(arguments);
  }
}
