package il.ac.technion.cs.fling.examples.languages

import il.ac.technion.cs.fling.BNF;
import il.ac.technion.cs.fling.Symbol;
import il.ac.technion.cs.fling.Terminal;
import il.ac.technion.cs.fling.Variable;
import il.ac.technion.cs.fling.examples.FluentLanguageAPI;
import static il.ac.technion.cs.fling.grammars.api.BNFAPI.bnf;

import static il.ac.technion.cs.fling.examples.languages.BNFLang.V.*;
import static il.ac.technion.cs.fling.examples.languages.BNFLang.Σ.*;

@SuppressWarnings("NonAsciiCharacters")
class BNFLang implements FluentLanguageAPI<Σ, V> {
  enum Σ implements Terminal {
    bnf, start, derive, specialize, to, into, toEpsilon, or, orNone
  }

  enum V implements Variable {
    PlainBNF, Rule, RuleBody, RuleTail
  }

  @Override String name() {
    return "BNFAPI";
  }
  @Override Class<Σ> Σ() {
    return Σ.class;
  }
  @Override Class<V> V() {
    return V.class;
  }
  @Override BNF BNF() {
    // @formatter:off
    return (bnf(). //
            start(PlainBNF). //
            derive(PlainBNF).to(bnf, start.with(Variable.class), noneOrMore(Rule)). // PlainBNF ::= start(Symbol) Rule*
            derive(Rule).to(derive.with(Variable.class), RuleBody). // Rule ::= derive(Variable) RuleBody
            derive(Rule).to(specialize.with(Variable.class), into.many(Variable.class)). // Rule ::= specialize(Variable) into(Variable*)
            derive(RuleBody).to(to.many(Symbol.class), noneOrMore(RuleTail)).or(toEpsilon). // RuleBody ::= to(Symbol*) RuleTail* | toEpsilon()
            derive(RuleTail).to(or.many(Symbol.class)) | orNone). // RuleTail ::= or(Symbol*) | orNone()
        build();
    // @formatter:on
  }
}
