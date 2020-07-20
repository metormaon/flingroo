package il.ac.technion.cs.fling.internal.grammar.sententials;

import il.ac.technion.cs.fling.Variable;

import java.util.List;
import java.util.stream.Collectors;

public class DerivationRule {
  public final Variable lhs;
  public final List<SententialForm> rhs;

  public DerivationRule(final Variable lhs, final List<SententialForm> rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }
  public Variable lhs() {
    return lhs;
  }
  public List<SententialForm> rhs() {
    return rhs;
  }
  @Override public int hashCode() {
    int $ = 1;
    $ = $ * 31 + lhs.hashCode();
    $ = $ * 31 + rhs.hashCode();
    return $;
  }
  @Override public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (!(o instanceof DerivationRule))
      return false;
    final DerivationRule other = (DerivationRule) o;
    return lhs.equals(other.lhs) && rhs.equals(other.rhs);
  }
  @Override public String toString() {
    return String.format("%s::=%s", lhs,
        String.join("|", rhs.stream().map(sf -> sf.isEmpty() ? "ε" : sf.toString()).collect(Collectors.toList())));
  }
}
