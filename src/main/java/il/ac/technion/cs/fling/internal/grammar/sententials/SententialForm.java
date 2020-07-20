package il.ac.technion.cs.fling.internal.grammar.sententials;

import il.ac.technion.cs.fling.Symbol;

import java.util.List;

public class SententialForm extends Word<Symbol> {
  public SententialForm(final Symbol... symbols) {
    super(symbols);
  }
  public SententialForm(final List<Symbol> symbols) {
    super(symbols);
  }
}
