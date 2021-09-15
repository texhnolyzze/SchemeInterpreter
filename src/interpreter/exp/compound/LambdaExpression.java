package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.procedure.UserDefinedProcedure;

import java.util.List;

public class LambdaExpression extends BaseExpression {

    private final List<String> params;
    private final SequenceExpression body;

    @SuppressWarnings({"rawtypes", "unchecked", "ForLoopReplaceableByForEach"})
    public LambdaExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertAtLeastNumArgs(list, 2);
        Util.assertList(list.get(1));
        this.params = (List) list.get(1);
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            Util.assertSymbol(param);
        }
        this.body = new SequenceExpression(2, list, analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        return new UserDefinedProcedure(body, params, env);
    }

}
