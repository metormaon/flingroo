package il.ac.technion.cs.fling

import il.ac.technion.cs.fling.grammars.api.BNFAPIAST
import il.ac.technion.cs.fling.internal.grammar.sententials.*
import il.ac.technion.cs.fling.internal.grammar.types.TypeParameter
import il.ac.technion.cs.fling.internal.util.Counter
import org.antlr.runtime.tree.Tree
import org.antlr.v4.tool.Grammar
import org.antlr.v4.tool.ast.*

import java.util.stream.Collectors

import static il.ac.technion.cs.fling.internal.grammar.sententials.Constants.intermediateVariableName
import static java.util.Collections.*
import static java.util.stream.Collectors.toSet

/**
 * A Backus-Naur form specification of formal Language, collection of derivation
 * rules of the form <code>V ::= w X | Y z.</code> Computes grammar's nullables
 * set and firsts/follows mappings.
 * 
 * @author Ori Roth
 */
@SuppressWarnings("NonAsciiCharacters")
class GroovyBNF {
  /**
   * Derivation rules collection.
   */
  public final Set<DerivationRule> rules
  /**
   * Set of nullable variables and notations.
   */
  public final Set<Symbol> nullables
  /**
   * Maps variables and notations to their firsts set.
   */
  public final Map<Symbol, Set<Verb>> firsts
  /**
   * Maps variables and notations to their follows set.
   */
  public final Map<Variable, Set<Verb>> follows
  /**
   * Verbs collection.
   */
  public final Set<Verb> Σ
  /**
   * Variables collection.
   */
  public final Set<Variable> V
  /**
   * Start variable.
   */
  public final Variable startVariable
  /**
   * Head variables set, containing variables used as API parameters.
   */
  public final Set<Variable> headVariables
  /**
   * Maps generated variables to the notation originated them. Optional.
   */
  public final Map<Variable, Notation> extensionHeadsMapping
  /**
   * Set of generated variables.
   */
  public final Set<Variable> extensionProducts

  GroovyBNF(final Set<Verb> Σ, final Set<? extends Variable> V, final Set<DerivationRule> R, final Variable startVariable,
                   final Set<Variable> headVariables, final Map<Variable, Notation> extensionHeadsMapping, final Set<Variable> extensionProducts,
                   final boolean addStartSymbolDerivationRules) {
    this.Σ = Σ
    Σ.add(Constants.$$)
    this.V = new LinkedHashSet<>(V)
    this.rules = R
    if (addStartSymbolDerivationRules) {
      this.V.add(Constants.S)
      R.add(new DerivationRule(Constants.S, new ArrayList<>()))
      rhs(Constants.S).add(new SententialForm(startVariable))
    }
    this.headVariables = headVariables
    this.startVariable = startVariable
    this.extensionHeadsMapping = extensionHeadsMapping == null ? emptyMap() : extensionHeadsMapping
    this.extensionProducts = extensionProducts == null ? emptySet() : extensionProducts
    this.nullables = getNullables()
    this.firsts = getFirsts()
    this.follows = getFollows()
  }
  /**
   * @return all grammar symbols.
   */
  Set<Symbol> symbols() {
    final Set<Symbol> $$ = new LinkedHashSet<>()
    $$.addAll(Σ)
    $$.addAll(V)
    return unmodifiableSet($$)
  }
  /**
   * @param v a variable
   * @return the right hand side of its derivation rule
   */
  List<SententialForm> rhs(final Variable v) {
    return rules.stream().filter({ r -> r.lhs.equals(v) }).findFirst().map(DerivationRule.&rhs).orElse(null)
  }
  /**
   * @param symbols sequence of grammar symbols
   * @return whether the sequence is nullable
   */
  boolean isNullable(final Symbol... symbols) {
    return isNullable(Arrays.asList(symbols))
  }
  /**
   * @param symbols sequence of grammar symbols
   * @return whether the sequence is nullable
   */
  public boolean isNullable(final List<Symbol> symbols) {
    return symbols.stream().allMatch({ symbol ->
        nullables.contains(symbol) || //
                symbol.isNotation() && symbol.asNotation().isNullable(this.&isNullable)
    })
  }
  Set<Verb> firsts(final Symbol... symbols) {
    return firsts(Arrays.asList(symbols))
  }
  Set<Verb> firsts(final Collection<Symbol> symbols) {
    final Set<Verb> $$ = new LinkedHashSet<>()
    for (final Symbol s : symbols) {
      $$.addAll(firsts.get(s))
      if (!isNullable(s))
        break
    }
    return unmodifiableSet($$)
  }
  GroovyBNF reachableSubBNF() {
    final Set<DerivationRule> subR = new LinkedHashSet<>()
    final Set<Verb> subΣ = new LinkedHashSet<>()
    final Set<Variable> subV = new LinkedHashSet<>()
    Set<Variable> newSubV = new LinkedHashSet<>()
    newSubV.add(startVariable)
    int previousCount = -1
    while (previousCount < subV.size()) {
      previousCount = subV.size()
      final Set<Variable> newestSubV = new LinkedHashSet<>()
      for (final DerivationRule rule : rules) {
        if (!newSubV.contains(rule.lhs))
          continue
        subR.add(rule)
        for (final SententialForm sf : rule.rhs)
          for (final Symbol symbol : sf)
            if (symbol.isVerb())
              subΣ.add(symbol.asVerb())
            else if (symbol.isVariable())
              newestSubV.add(symbol.asVariable())
            else
              throw new RuntimeException("problem while analyzing BNF")
      }
      subV.addAll(newSubV)
      newSubV = newestSubV
    }
    return new GroovyBNF(subΣ, subV, subR, startVariable, null, null, null, true)
  }
  boolean isOriginalVariable(final Symbol symbol) {
    return symbol.isVariable() && !extensionProducts.contains(symbol)
  }
  private Set<Symbol> getNullables() {
    final Set<Symbol> $$ = new LinkedHashSet<>()
    for (; $$.addAll(V.stream() //
        .filter({ v ->
            rhs(v).stream() //
                    .anyMatch({ sf -> sf.stream().allMatch({ symbol -> isNullable(symbol, $$) }) })
        }) //
        .collect(toSet()));)
      
    return $$
  }
    public boolean isNullable(final Symbol symbol, final Set<Symbol> knownNullables) {
    if (symbol.isVerb())
      return false
    if (symbol.isVariable())
      return knownNullables.contains(symbol)
    if (symbol.isNotation())
      return symbol.asNotation().isNullable({ s -> isNullable(s, knownNullables) })
    throw new RuntimeException("problem while analyzing BNF")
  }
  private Map<Symbol, Set<Verb>> getFirsts() {
    final Map<Symbol, Set<Verb>> $$ = new LinkedHashMap<>()
    Σ.forEach({ σ -> $$.put(σ, singleton(σ)) })
    V.forEach({ v -> $$.put(v, new LinkedHashSet<>()) })
    for (boolean changed = true; changed;) {
      changed = false
      for (final Variable v : V)
        for (final SententialForm sf : rhs(v))
          for (final Symbol symbol : sf) {
            changed |= $$.get(v).addAll(!symbol.isNotation() ? $$.get(symbol) : //
                symbol.asNotation().getFirsts($$.&get))
            if (!isNullable(symbol))
              break
          }
    }
    V.forEach({ v -> $$.put(v, unmodifiableSet($$.get(v))) })
    return unmodifiableMap($$)
  }
  private Map<Variable, Set<Verb>> getFollows() {
    final Map<Variable, Set<Verb>> $$ = new LinkedHashMap<>()
    V.forEach({ v -> $$.put(v, new LinkedHashSet<>()) })
    $$.get(Constants.S).add(Constants.$$)
    for (boolean changed = true; changed;) {
      changed = false
      for (final Variable v : V)
        for (final SententialForm sf : rhs(v))
          for (int i = 0; i < sf.size(); ++i) {
            if (!sf.get(i).isVariable())
              continue
            final Variable current = sf.get(i).asVariable()
            final List<Symbol> rest = sf.subList(i, sf.size())
            changed |= $$.get(current).addAll(firsts(rest))
            if (isNullable(rest))
              changed |= $$.get(v).addAll($$.get(current))
          }
    }
    V.forEach({ s -> $$.put(s, unmodifiableSet($$.get(s))) })
    return unmodifiableMap($$)
  }
  static GroovyBNF toBNF(final BNFAPIAST.PlainBNF specification) {
    final Builder $$ = new Builder()
    $$.start(specification.start)
    for (final BNFAPIAST.Rule rule : specification.rule)
      if (rule instanceof BNFAPIAST.Derivation) {
        // Derivation rule.
        final BNFAPIAST.Derivation derivation = (BNFAPIAST.Derivation) rule
        Variable lhs = derivation.derive
        if (derivation.ruleBody instanceof BNFAPIAST.ConcreteDerivation) {
          // Concrete derivation rule.
            BNFAPIAST.ConcreteDerivation concrete = (BNFAPIAST.ConcreteDerivation) derivation.ruleBody
          $$.derive(lhs).to((concrete).to)
          for (BNFAPIAST.RuleTail tail : concrete.ruleTail)
            if (tail instanceof BNFAPIAST.ConcreteDerivationTail)
              // Concrete tail.
              $$.derive(lhs).to(((BNFAPIAST.ConcreteDerivationTail) tail).or)
            else
              // Epsilon tail.
              $$.derive(lhs).toEpsilon()
        } else
          // Epsilon derivation rule.
          $$.derive(lhs).toEpsilon()
      } else {
        // Specialization rule.
        final BNFAPIAST.Specialization specializationRule = (BNFAPIAST.Specialization) rule
        $$.specialize(specializationRule.specialize).into(specializationRule.into)
      }
    try {
      return $$.build()
    } catch (Exception e) {
      throw new RuntimeException("problem while analyzing BNF, make sure the grammar adheres its class description (LL/LR/etc)", e)
    }
  }

  @SuppressWarnings("GrDeprecatedAPIUsage")
  @Deprecated private static class Builder {
    private final Set<Verb> Σ
    private final Set<Variable> V
    private final Set<DerivationRule> R
    private Variable start
    private final Set<Variable> heads

      @SuppressWarnings("GrDeprecatedAPIUsage")
      Builder() {
      this.Σ = new LinkedHashSet<>()
      this.V = new LinkedHashSet<>()
      this.R = new LinkedHashSet<>()
      this.heads = new LinkedHashSet<>()
    }
    Derive derive(final Variable lhs) {
      processSymbol(lhs)
      return new Derive(lhs)
    }
    Specialize specialize(final Variable lhs) {
      return new Specialize(lhs)
    }
    void processSymbol(final Symbol symbol) {
      assert !symbol.isTerminal()
      if (symbol.isVerb()) {
        Σ.add(symbol.asVerb())
        symbol.asVerb().parameters.stream() //
            .map(TypeParameter.&declaredHeadVariables) //
            .forEach(heads.&addAll)
      } else if (symbol.isNotation())
        symbol.asNotation().abbreviatedSymbols().forEach(this.&processSymbol)
      else if (symbol.isVariable()) {
        final Variable variable = symbol.asVariable()
        if (!V.contains(variable)) {
          V.add(variable)
          R.add(new DerivationRule(variable, new ArrayList<>()))
        }
      }
    }
    final Builder start(final Variable startVariable) {
      start = startVariable
      return this
    }
    GroovyBNF build() {
      assert start != null : "declare a start variable"
      return new GroovyBNF(Σ, V, R, start, heads, null, null, true)
    }
    List<SententialForm> rhs(final Variable v) {
      return R.stream().filter({ r -> r.lhs.equals(v) }).findFirst().map(DerivationRule.&rhs).orElse(null)
    }

    class Derive {
      private final Variable lhs

      Derive(final Variable lhs) {
        this.lhs = lhs
      }

        Builder to(final Symbol... sententialForm) {
        final SententialForm processedSententialForm = new SententialForm(Arrays.stream(sententialForm) //
            .map({ symbol ->
                return !symbol.isTerminal() ? symbol : new Verb(symbol.asTerminal())
            }) //
            .collect(Collectors.toList()))
        processedSententialForm.forEach(Builder.this.&processSymbol)
        rhs(lhs).add(processedSententialForm)
        return Builder.this
      }

        Builder toEpsilon() {
        final SententialForm processedSententialForm = new SententialForm()
        rhs(lhs).add(processedSententialForm)
        return Builder.this
      }
    }

    class Specialize {
      private final Variable lhs

      Specialize(final Variable lhs) {
        this.lhs = lhs
      }

        Builder into(final Variable... variables) {
        for (final Variable variable : variables) {
          final SententialForm processedSententialForm = new SententialForm(variable)
          processedSententialForm.forEach(Builder.this.&processSymbol)
          rhs(lhs).add(processedSententialForm)
        }
        return Builder.this
      }
    }
  }

  static GroovyBNF fromANTLR(Grammar grammar) {
    assert grammar.ast.getChildCount() == 2 : "ANTLR grammar is not simplified"
    Builder $$ = new Builder()
    boolean initialized = false
    Tree rules = grammar.ast.getChild(1)
    Counter counter = new Counter()
    for (int i = 0; i < rules.getChildCount(); ++i) {
      Tree rule = rules.getChild(i)
      assert rule.getChildCount() == 2
      String variableName = rule.getChild(0).getText()
      Variable variable = Variable.byName(variableName)
      if (!initialized) {
        // Assume first ANTLR variable is start variable.
        $$.start(variable)
        initialized = true
      }
      Optional<Symbol> rhs = extractANTLRSentential($$, rule.getChild(1), counter)
      if (rhs.isPresent())
        $$.derive(variable).to(rhs.get())
      else
        $$.derive(variable).toEpsilon()
    }
    return $$.build()
  }
  private static Optional<Symbol> extractANTLRSentential(Builder $$, Object element, Counter nameCounter) {
    if (element instanceof List) {
      List<?> elements = (List<?>) element
      if (elements.isEmpty())
        return Optional.empty()
      else if (elements.size() == 1)
        return extractANTLRSentential($$, elements.get(0), nameCounter)
      Variable top = Variable.byName(intermediateVariableName + nameCounter.getAndInc())
      List<Symbol> items = new ArrayList<>()
      for (Object item : elements)
        extractANTLRSentential($$, item, nameCounter).ifPresent(items.&add)
      $$.derive(top).to(items.toArray(new Symbol[items.size()]))
      return Optional.of(top)
    } else if (element instanceof AltAST) {
      return extractANTLRSentential($$, ((AltAST) element).getChildren(), nameCounter)
    } else if (element instanceof BlockAST) {
      BlockAST block = (BlockAST) element
      if (block.getChildCount() <= 1)
        return extractANTLRSentential($$, block.getChildren(), nameCounter)
      Variable top = Variable.byName(intermediateVariableName + nameCounter.getAndInc())
      List<Symbol> items = new ArrayList<>()
      for (Object item : block.getChildren())
        extractANTLRSentential($$, item, nameCounter).ifPresent(items.&add)
      items.forEach({ symbol -> $$.derive(top).to(symbol) })
      return Optional.of(top)
    } else if (element instanceof RuleRefAST) {
      return Optional.of(Variable.byName(element.toString()))
    } else if (element instanceof StarBlockAST) {
      Optional<Symbol> inner = extractANTLRSentential($$, ((StarBlockAST) element).getChildren(), nameCounter)
      return inner.map(Symbol.&noneOrMore)
    } else if (element instanceof PlusBlockAST) {
      Optional<Symbol> inner = extractANTLRSentential($$, ((PlusBlockAST) element).getChildren(), nameCounter)
      return inner.map(Symbol.&oneOrMore)
    } else if (element instanceof TerminalAST) {
      String name = ((TerminalAST) element).getText()
      name = name.substring(1, name.length() - 1)
      // Assume simple terminal.
      return Optional.of(Terminal.byName(name))
    }
    throw new RuntimeException(String.format("Grammar element %s no supported", element.getClass().getSimpleName()))
  }
}
