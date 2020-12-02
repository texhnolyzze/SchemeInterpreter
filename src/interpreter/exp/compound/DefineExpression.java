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

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public DefineExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 2);
        if (list.get(1) instanceof List<?> procedure) { // procedure definition
            assertAtLeastNumArgs(0, procedure, 1);
            assertNotPredefined(procedure.get(0), analyzer);
            for (int i = 0; i < procedure.size(); i++) {
                Object param = procedure.get(i);
                assertSymbol(param);
            }
            this.var = (String) procedure.get(0);
            List<?> params = procedure.subList(1, procedure.size());
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
