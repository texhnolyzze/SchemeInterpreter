package interpreter.exp.compound.function;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.compound.SequenceExpression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static interpreter.exp.Util.assertNumArgs;

public class UserDefinedFunction implements Function {

    private final SequenceExpression body;
    private final List<String> params;
    private final Environment myEnvironment; // environment in which function was defined

    public UserDefinedFunction(SequenceExpression body, List<String> params, Environment myEnvironment) {
        this.body = body;
        this.params = params;
        this.myEnvironment = myEnvironment;
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        return body.eval(bind(args, env));
    }

    public Expression evalBound(final Environment env) {
        return body.eval(env);
    }

    @Override
    public Environment bind(
        final List<Expression> args,
        final Environment callerEnvironment
    ) {
        assertNumArgs(0, args, params.size(), this);
        final Map<String, Expression> bound = new HashMap<>(params.size());
        for (int i = 0; i < params.size(); i++) {
            final String param = params.get(i);
            final Expression arg = args.get(i).eval(callerEnvironment);
            bound.put(param, arg);
        }
        return myEnvironment.extend(bound);
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new UserDefinedFunction(
            body.expand(params, env),
            this.params,
            myEnvironment
        );
    }

    @Override
    public String toString() {
        return "(lambda (" + String.join(" ", params) + ") " + body.toString() + ")";
    }

}
