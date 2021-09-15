package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class CdrExpression implements BuiltInFunction {

    public static final CdrExpression INSTANCE = new CdrExpression();

    private CdrExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 1);
        final Expression arg = args.get(0).eval(env);
        Util.assertNotNull(arg);
        Util.assertType(arg, PairExpression.class);
        return ((PairExpression) arg).cdr();
    }

}
