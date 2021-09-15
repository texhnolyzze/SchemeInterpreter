package interpreter.exp.compound;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.procedure.BuiltInProcedure;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class CarExpression implements BuiltInProcedure {

    public static final CarExpression INSTANCE = new CarExpression();

    private CarExpression() {
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
        return ((PairExpression) arg).car();
    }

}
