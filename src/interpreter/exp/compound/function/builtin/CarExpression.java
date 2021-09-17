package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class CarExpression implements BuiltInFunction {

    public static final CarExpression INSTANCE = new CarExpression();

    private CarExpression() {
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
        return ((PairExpression) arg).car();
    }

    @Override
    public String toString() {
        return "car";
    }

}
