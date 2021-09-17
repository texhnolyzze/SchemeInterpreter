package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class EqExpression implements BuiltInFunction {

    public static final EqExpression INSTANCE = new EqExpression();

    private EqExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 2, this);
        return args.get(0).eval(env) == args.get(1).eval(env) ?
               TrueExpression.INSTANCE :
               FalseExpression.INSTANCE;
    }

    @Override
    public String toString() {
        return "eq?";
    }

}
