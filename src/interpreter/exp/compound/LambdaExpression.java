package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.ProcedureExpression;

import java.util.List;

public class LambdaExpression extends CompoundExpression {

    private final List<String> params;
    private final SequenceExpression body;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public LambdaExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 2);
        assertList(list.get(1));
        List params = (List) list.get(1);
        for (Object param : params) {
            assertSymbol(param);
        }
        this.params = params;
        this.body = new SequenceExpression(2, list, analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        return new ProcedureExpression(body, params, env);
    }

}
