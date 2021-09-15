package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class ConsExpression implements BuiltInFunction {

    public static final ConsExpression INSTANCE = new ConsExpression();

    private ConsExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 2);
        final Expression car = args.get(0).eval(env);
        final Expression cdr = args.get(1).eval(env);
        return PairExpression.cons(car.eval(env), cdr.eval(env));
    }

}
