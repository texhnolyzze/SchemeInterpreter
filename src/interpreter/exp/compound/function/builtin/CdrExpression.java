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
        Util.assertNumArgs(0, args, 1, this);
        final Expression arg = args.get(0).eval(env);
        Util.assertNotNull(arg, this);
        Util.assertType(arg, PairExpression.class, this);
        return ((PairExpression) arg).cdr();
    }

    @Override
    public String toString() {
        return "cdr";
    }

}
