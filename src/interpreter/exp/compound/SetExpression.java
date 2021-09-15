package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.NilExpression;

import java.util.List;

public class SetExpression extends BaseExpression {

    private final String name;
    private final Expression definition;

    public SetExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertNumArgs(list, 2);
        Util.assertSymbol(list.get(1));
        assertNotPredefined(list.get(1), analyzer);
        this.name = (String) list.get(1);
        this.definition = analyzer.analyze(list.get(2));
    }

    @Override
    public Expression eval(Environment env) {
        env.set(name, definition.eval(env));
        return NilExpression.INSTANCE;
    }

}
