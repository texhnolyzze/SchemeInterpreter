package interpreter.exp.compound.procedure;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.compound.SequenceExpression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static interpreter.exp.Util.assertNumArgs;

public class UserDefinedProcedure implements Procedure {

    private final SequenceExpression body;
    private final List<String> params;
    private final Environment myEnvironment; // environment in which procedure was defined

    public UserDefinedProcedure(SequenceExpression body, List<String> params, Environment myEnvironment) {
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
        assertNumArgs(0, args, params.size());
        Map<String, Expression> boundArgs = new HashMap<>(params.size());
        for (int i = 0; i < params.size(); i++) {
            String param = params.get(i);
            Expression arg = args.get(i).eval(callerEnvironment);
            boundArgs.put(param, arg);
        }
        return myEnvironment.extend(boundArgs);
    }

}
