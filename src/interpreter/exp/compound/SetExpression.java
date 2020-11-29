package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;

import java.util.List;

public class SetExpression extends CompoundExpression {

    private final String var;
    private final Expression definition;

    public SetExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 2);
        assertSymbol(list.get(1));
        assertNotPredefined(list.get(1), analyzer);
        this.var = (String) list.get(1);
        this.definition = analyzer.analyze(list.get(2));
    }

    @Override
    public Expression eval(Environment env) {
        env.set(var, definition.eval(env));
        return NilExpression.INSTANCE;
    }

}
