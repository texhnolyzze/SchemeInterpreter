package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;

import java.util.List;

public abstract class SetDefineExpression extends CompoundExpression {

    private final String var;
    private final Expression definition;

    public SetDefineExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 2);
        if (list.get(1) instanceof List) { // procedure definition
            List<?> l = (List<?>) list.get(1);
            assertAtLeastNumArgs(0, l, 1);
            assertNotPredefined(l.get(0), analyzer);
            for (Object o : l)
                assertSymbol(o);
            this.var = (String) l.get(0);
            List<?> params = l.subList(1, l.size());
            Object body = list.get(2);
            this.definition = new LambdaExpression(List.of("lambda", params, body), analyzer);
        } else {
            assertSymbol(list.get(1));
            assertNotPredefined(list.get(1), analyzer);
            this.var = (String) list.get(1);
            this.definition = analyzer.analyze(list.get(2));
        }
    }

    protected abstract void modify(Environment env, String var, Expression expression);

    @Override
    public final Expression eval(Environment env) {
        modify(env, var, definition.eval(env));
        return NilExpression.INSTANCE;
    }

}
