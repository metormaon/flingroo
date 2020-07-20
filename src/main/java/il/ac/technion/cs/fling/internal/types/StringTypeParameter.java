package il.ac.technion.cs.fling.internal.types;

import il.ac.technion.cs.fling.internal.grammar.types.TypeParameter;

public interface StringTypeParameter extends TypeParameter {
  String typeName();
  default String parameterTypeName() {
    return typeName();
  }
}
