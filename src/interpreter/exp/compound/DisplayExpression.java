package interpreter.exp.compound;

import interpreter.Environment;
import interpreter.InOut;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.procedure.BuiltInProcedure;
import interpreter.exp.self.NilExpression;

import java.util.List;

public class DisplayExpression implements BuiltInProcedure {

    public static final DisplayExpression INSTANCE = new DisplayExpression();

    private DisplayExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 1);
        InOut.instance().out().print(args.get(0).eval(env));
        return NilExpression.INSTANCE;
    }

}
