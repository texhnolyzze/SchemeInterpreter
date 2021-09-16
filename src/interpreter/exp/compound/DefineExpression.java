package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.NilExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefineExpression extends BaseExpression {

    private final String name;
    private final Expression definition;

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public DefineExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertAtLeastNumArgs(list, 2);
        if (list.get(1) instanceof List<?> func) { // function definition
            Util.assertAtLeastNumArgs(0, func, 1);
            assertNotPredefined(func.get(0), analyzer);
            for (int i = 0; i < func.size(); i++) {
                Object param = func.get(i);
                Util.assertSymbol(param);
            }
            this.name = (String) func.get(0);
            List<?> params = func.subList(1, func.size());
            List<?> body = list.subList(2, list.size());
            ArrayList<?> lambda = new ArrayList<>(List.of("lambda", params));
            Util.append(lambda, body);
            this.definition = new LambdaExpression(lambda, analyzer);
        } else {
            Util.assertSymbol(list.get(1));
            assertNotPredefined(list.get(1), analyzer);
            this.name = (String) list.get(1);
            this.definition = analyzer.analyze(list.get(2));
        }
    }

    private DefineExpression(
        final String name,
        final Expression definition
    ) {
        super(null);
        this.name = name;
        this.definition = definition;
    }

    @Override
    public final Expression eval(Environment env) {
        env.define(name, definition.eval(env));
        return NilExpression.INSTANCE;
    }

    @Override
    public DefineExpression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new DefineExpression(
            name,
            definition.expand(params, env)
        );
    }

}
