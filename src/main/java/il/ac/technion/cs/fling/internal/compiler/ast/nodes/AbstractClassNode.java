package il.ac.technion.cs.fling.internal.compiler.ast.nodes;


import il.ac.technion.cs.fling.Variable;

import java.util.List;

public class AbstractClassNode extends ClassNode {
  public final List<AbstractClassNode> parents;
  public final List<ClassNode> children;

  public AbstractClassNode(final Variable source, final List<AbstractClassNode> parents, final List<ClassNode> children) {
    super(source);
    this.parents = parents;
    this.children = children;
  }
}
