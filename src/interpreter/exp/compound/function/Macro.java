package interpreter.exp.compound.function;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.compound.SequenceExpression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Macro implements Function {

    private final SequenceExpression body;
    private final List<String> params;

    public Macro(
        final SequenceExpression body,
        final List<String> params
    ) {
        this.body = body;
        this.params = params;
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        final Map<String, Expression> parms = new HashMap<>(params.size());
        for (int i = 0; i < args.size(); i++) {
            parms.put(params.get(i), args.get(i));
        }
        final Expression expanded = expand(parms, env);
        return expanded.eval(env);
    }

    /**
     * No need for binding
     */
    @Override
    public Environment bind(
        final List<Expression> args,
        final Environment callerEnvironment
    ) {
        return callerEnvironment;
    }


    @Override
    public SequenceExpression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return body.expand(params, env);
    }

    public List<String> params() {
        return params;
    }

    @Override
    public String toString() {
        return "(macro (" + String.join(" ", params) + ") " + body.toString() + ")";
    }

}
