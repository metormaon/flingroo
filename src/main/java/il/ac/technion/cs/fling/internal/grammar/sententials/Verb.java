package il.ac.technion.cs.fling.internal.grammar.sententials;

import il.ac.technion.cs.fling.Symbol;
import il.ac.technion.cs.fling.Terminal;
import il.ac.technion.cs.fling.internal.grammar.types.TypeParameter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Verb implements Symbol {
  public final Terminal source;
  public final List<TypeParameter> parameters;

  public Verb(final Terminal source, final TypeParameter... parameters) {
    this.source = requireNonNull(source);
    this.parameters = Arrays.asList(parameters);
  }
  @Override public String name() {
    return source.name();
  }
  @Override public int hashCode() {
    int hc = 1;
    hc = hc * 31 + source.hashCode();
    hc = hc * 31 + parameters.hashCode();
    return hc;
  }
  @Override public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj instanceof Terminal)
      return equals((Terminal) obj);
    if (!(obj instanceof Verb))
      return false;
    final Verb other = (Verb) obj;
    return source.equals(other.source) && parameters.equals(other.parameters);
  }
  public boolean equals(final Terminal terminal) {
    return source.equals(terminal);
  }
  @Override public String toString() {
    return String.format("%s<%s>", //
        name(), //
        parameters.stream().map(Object::toString).collect(Collectors.joining(", ")));
  }
}
