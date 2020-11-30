package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;

import java.util.ArrayList;
import java.util.List;

public class DefineExpression extends BaseExpression {

    private final String var;
    private final Expression definition;

    public DefineExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 2);
        if (list.get(1) instanceof List) { // procedure definition
            List<?> l = (List<?>) list.get(1);
            assertAtLeastNumArgs(0, l, 1);
            assertNotPredefined(l.get(0), analyzer);
            for (Object o : l)
                assertSymbol(o);
            this.var = (String) l.get(0);
            List<?> params = l.subList(1, l.size());
            List<?> body = list.subList(2, list.size());
            ArrayList<?> lambda = new ArrayList<>(List.of("lambda", params));
            append(lambda, body);
            this.definition = new LambdaExpression(lambda, analyzer);
        } else {
            assertSymbol(list.get(1));
            assertNotPredefined(list.get(1), analyzer);
            this.var = (String) list.get(1);
            this.definition = analyzer.analyze(list.get(2));
        }
    }

    @Override
    public final Expression eval(Environment env) {
        env.define(var, definition.eval(env));
        return NilExpression.INSTANCE;
    }

}
