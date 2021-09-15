package interpreter.exp.compound;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.procedure.BuiltInProcedure;
import interpreter.exp.self.BooleanExpression;

import java.util.List;

public class NotExpression implements BuiltInProcedure {

    public static final NotExpression INSTANCE = new NotExpression();

    private NotExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 1);
        final Expression eval = args.get(0).eval(env);
        Util.assertNotNull(eval);
        Util.assertType(eval, BooleanExpression.class);
        return ((BooleanExpression) eval).not();
    }

}
