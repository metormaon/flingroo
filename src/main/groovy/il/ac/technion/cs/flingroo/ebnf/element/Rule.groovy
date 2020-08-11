package il.ac.technion.cs.flingroo.ebnf.element
/**
 * @author Noam Rotem
 */
class Rule {
    private final Variable variable
    private final List<RuleOption> options = []

    Rule(Variable v, RuleElement e) {
        variable = v

        if (options.empty) {
            options.add(new RuleOption())
        }

        options.last().add(e)

        this
    }

    Rule and(RuleElement e) {
        options.last().add(e)
        this
    }

    Rule and(Closure<RuleElement> c) {
        RuleElement e = c()
        options.last().add(new ZeroOrMore(e))
        this
    }

    Rule and(List<RuleElement> l) {
        options.last().add(new ZeroOrOne(l[0]))
        this
    }

    Rule or(RuleElement e) {
        options.add(new RuleOption(e))
        this
    }

    Rule or(AndList l) {
        options.add(new RuleOption(l))
        this
    }

    Rule or(Closure<RuleElement> c) {
        options.add(new RuleOption(new ZeroOrMore(c())))
        this
    }

    Rule or(List<RuleElement> l) {
        options.add(new RuleOption(new ZeroOrOne(l[0])))
        this
    }

    @Override
    String toString() {
        variable.toString() + ":\n" + options.collect{"  " + it.toString()}.join("\n") + "\n"
    }
}
