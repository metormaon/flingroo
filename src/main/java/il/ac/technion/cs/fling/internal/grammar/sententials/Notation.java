package il.ac.technion.cs.fling.internal.grammar.sententials;

import il.ac.technion.cs.fling.Symbol;
import il.ac.technion.cs.fling.Variable;
import il.ac.technion.cs.fling.internal.compiler.Namer;
import il.ac.technion.cs.fling.internal.compiler.ast.nodes.FieldNode.FieldNodeFragment;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Notation extends Symbol {
  Collection<Symbol> abbreviatedSymbols();
  Variable extend(Namer namer, Consumer<Variable> variableDeclaration, Consumer<DerivationRule> ruleDeclaration);
  List<FieldNodeFragment> getFields(Function<Symbol, List<FieldNodeFragment>> fieldTypesSolver,
      Function<String, String> nameFromBaseSolver);
  boolean isNullable(Function<Symbol, Boolean> nullabilitySolver);
  Set<Verb> getFirsts(Function<Symbol, Set<Verb>> firstsSolver);
}
