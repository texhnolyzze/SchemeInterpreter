package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.UserDefinedFunction;

import java.util.List;
import java.util.Map;

public class LambdaExpression extends BaseExpression {

    private final List<String> params;
    private final SequenceExpression body;

    @SuppressWarnings({"rawtypes", "unchecked", "ForLoopReplaceableByForEach"})
    public LambdaExpression(List<?> list, Analyzer analyzer) {
        Util.assertAtLeastNumArgs(list, 2, this);
        Util.assertList(list.get(1));
        this.params = (List) list.get(1);
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            Util.assertSymbol(param);
        }
        this.body = new SequenceExpression(2, list, analyzer);
    }

    private LambdaExpression(
        final List<String> params,
        final SequenceExpression body
    ) {
        this.params = params;
        this.body = body;
    }

    @Override
    public Expression eval(Environment env) {
        return new UserDefinedFunction(body, params, env);
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new LambdaExpression(
            this.params,
            body.expand(params, env)
        );
    }

    @Override
    public String toString() {
        return "(lambda (" + String.join(" ", params) + ") " + body.toString() + ")";
    }

}
