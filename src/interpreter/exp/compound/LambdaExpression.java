package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Procedure;

import java.util.List;

public class LambdaExpression extends BaseExpression {

    private final List<String> params;
    private final SequenceExpression body;

    @SuppressWarnings({"rawtypes", "unchecked", "ForLoopReplaceableByForEach"})
    public LambdaExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 2);
        assertList(list.get(1));
        List params = (List) list.get(1);
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            assertSymbol(param);
        }
        this.params = params;
        this.body = new SequenceExpression(2, list, analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        return new Procedure(body, params, env);
    }

}
