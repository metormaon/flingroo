package il.ac.technion.cs.flingroo.bnf.element
/**
 * @author Noam Rotem
 */
abstract class RuleElementList implements RuleElement {
    @Delegate
    final LinkedList<RuleElement> elementList = new LinkedList<>();

    RuleElementList(RuleElement... e) {
        elementList.addAll(e)
    }
}
