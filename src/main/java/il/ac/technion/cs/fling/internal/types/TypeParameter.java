package il.ac.technion.cs.fling.internal.types;

import il.ac.technion.cs.fling.Variable;
import il.ac.technion.cs.fling.internal.grammar.types.StringTypeParameter;
import il.ac.technion.cs.fling.internal.grammar.types.VarargsVariableTypeParameter;
import il.ac.technion.cs.fling.internal.grammar.types.VariableTypeParameter;

import java.util.Collections;
import java.util.Set;

public interface TypeParameter {
  String baseParameterName();
  default Set<Variable> declaredHeadVariables() {
    return Collections.emptySet();
  }
  default boolean isStringTypeParameter() {
    return this instanceof StringTypeParameter;
  }
  default boolean isVariableTypeParameter() {
    return this instanceof VariableTypeParameter;
  }
  default boolean isVarargsTypeParameter() {
    return this instanceof VarargsVariableTypeParameter;
  }
  default StringTypeParameter asStringTypeParameter() {
    return (StringTypeParameter) this;
  }
  default VariableTypeParameter asVariableTypeParameter() {
    return (VariableTypeParameter) this;
  }
  default VarargsVariableTypeParameter asVarargsVariableTypeParameter() {
    return (VarargsVariableTypeParameter) this;
  }
}
