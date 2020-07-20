package il.ac.technion.cs.fling.internal.compiler.ast.nodes;

import il.ac.technion.cs.fling.Variable;

import java.util.List;

public class ConcreteClassNode extends ClassNode {
  public final List<AbstractClassNode> parents;
  public final List<FieldNode> fields;

  public ConcreteClassNode(final Variable source, final List<AbstractClassNode> parents, final List<FieldNode> fields) {
    super(source);
    this.parents = parents;
    this.fields = fields;
  }
  public List<FieldNode> getFields() {
    return fields;
  }
}
