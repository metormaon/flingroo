package il.ac.technion.cs.fling.internal.compiler.api.nodes;

import java.util.Collections;
import java.util.List;

public class PolymorphicTypeNode<T> {
  public final T name;
  public final List<PolymorphicTypeNode<T>> typeArguments;
  @SuppressWarnings("rawtypes") private static final PolymorphicTypeNode TOP = new PolymorphicTypeNode<>();
  @SuppressWarnings("rawtypes") private static final PolymorphicTypeNode BOT = new PolymorphicTypeNode<>();

  public PolymorphicTypeNode(final T name, final List<PolymorphicTypeNode<T>> typeArguments) {
    this.name = name;
    this.typeArguments = Collections.unmodifiableList(typeArguments);
  }
  public PolymorphicTypeNode(final T name) {
    this.name = name;
    this.typeArguments = Collections.emptyList();
  }
  private PolymorphicTypeNode() {
    this.name = null;
    this.typeArguments = null;
  }
  @SuppressWarnings("unchecked") public static <T> PolymorphicTypeNode<T> top() {
    return TOP;
  }
  @SuppressWarnings("unchecked") public static <T> PolymorphicTypeNode<T> bot() {
    return BOT;
  }
  public boolean isTop() {
    return this == TOP;
  }
  public boolean isBot() {
    return this == BOT;
  }
}
