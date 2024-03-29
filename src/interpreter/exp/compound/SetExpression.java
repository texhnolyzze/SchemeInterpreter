package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.NilExpression;

import java.util.List;
import java.util.Map;

public class SetExpression extends BaseExpression {

    private final String name;
    private final Expression definition;

    public SetExpression(List<?> list, Analyzer analyzer) {
        Util.assertNumArgs(list, 2);
        Util.assertSymbol(list.get(1));
        assertNotPredefined(list.get(1), analyzer);
        this.name = (String) list.get(1);
        this.definition = analyzer.analyze(list.get(2));
    }

    private SetExpression(
        final String name,
        final Expression definition
    ) {
        this.name = name;
        this.definition = definition;
    }

    @Override
    public Expression eval(Environment env) {
        env.set(name, definition.eval(env));
        return NilExpression.INSTANCE;
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        final String nme;
        final Expression nameParam = params.get(name);
        if (nameParam == null) {
            nme = name;
        } else {
            nme = nameParam.toString();
        }
        return new SetExpression(
            nme,
            definition.expand(params, env)
        );
    }

    @Override
    public String toString() {
        return "(set! " + name + " " + definition.toString() + ")";
    }

}
