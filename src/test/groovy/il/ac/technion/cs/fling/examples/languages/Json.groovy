package il.ac.technion.cs.fling.examples.languages;

import il.ac.technion.cs.fling.BNF;
import il.ac.technion.cs.fling.Terminal;
import il.ac.technion.cs.fling.Variable;
import il.ac.technion.cs.fling.examples.FluentLanguageAPI;
import il.ac.technion.cs.fling.examples.languages.Json.V;
import il.ac.technion.cs.fling.examples.languages.Json.Σ;

import static il.ac.technion.cs.fling.Symbol.noneOrMore;
import static il.ac.technion.cs.fling.examples.languages.Json.V.Json;
import static il.ac.technion.cs.fling.examples.languages.Json.V.*;
import static il.ac.technion.cs.fling.examples.languages.Json.Σ.*;
import static il.ac.technion.cs.fling.grammars.api.BNFAPI.bnf;

@SuppressWarnings("NonAsciiCharacters")
class Json implements FluentLanguageAPI<Σ, V> {
  enum Σ implements Terminal {
    array, end, isSetTo, key, object, value, _null_
  }

  public enum V implements Variable {
    Element, Field, Json, Value
  }

  @Override Class<Σ> Σ() {
    return Σ.class;
  }
  @Override Class<V> V() {
    return V.class;
  }
  @Override BNF BNF() {
    return (bnf().
            start(Json as Variable).
            derive(Json as Variable).to(Element).
            derive(Element).to(object, noneOrMore(Field), end).
            or(array, noneOrMore(Element), end).
            or(Value).
            derive(Value).to(value.with(String.class)).
            or(value.with(Number.class)).
            or(value.with(Boolean.class)) | _null_).
        derive(Field).to(key.with(String.class), isSetTo, Element).
        build();
  }
}
