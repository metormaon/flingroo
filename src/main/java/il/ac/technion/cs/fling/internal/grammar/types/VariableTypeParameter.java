package il.ac.technion.cs.fling.internal.grammar.types;

import il.ac.technion.cs.fling.Variable;
import il.ac.technion.cs.fling.namers.NaiveNamer;

import java.util.Collections;
import java.util.Set;

public class VariableTypeParameter implements TypeParameter {
  public final Variable variable;

  public VariableTypeParameter(final Variable variable) {
    this.variable = variable;
  }
  @Override public String baseParameterName() {
    return NaiveNamer.lowerCamelCase(variable.name());
  }
  @Override public Set<Variable> declaredHeadVariables() {
    return Collections.singleton(variable);
  }
  @Override public int hashCode() {
    return variable.hashCode();
  }
  @Override public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof VariableTypeParameter))
      return false;
    final VariableTypeParameter other = (VariableTypeParameter) obj;
    return variable.equals(other.variable);
  }
  @Override public String toString() {
    return variable.name();
  }
}
