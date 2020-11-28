package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.VariableExpression;

import java.util.List;

public class SetExpression extends CompoundExpression {

    private final VariableExpression var;
    private final Expression definition;

    public SetExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 2);
        Expression e = analyzer.analyze(list.get(1));
        assertNotNull(e);
        assertType(e, VariableExpression.class);
        this.var = (VariableExpression) e;
        this.definition = analyzer.analyze(list.get(2));
    }

    @Override
    public Expression eval(Environment env) {
        env.set(var.getName(), definition.eval(env));
        return null;
    }

}
