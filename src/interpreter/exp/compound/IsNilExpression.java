package interpreter.exp.compound;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.procedure.BuiltInProcedure;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class IsNilExpression implements BuiltInProcedure {

    public static final IsNilExpression INSTANCE = new IsNilExpression();

    private IsNilExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 1);
        final Expression arg = args.get(0).eval(env);
        return arg.eval(env) == NilExpression.INSTANCE ?
               TrueExpression.INSTANCE :
               FalseExpression.INSTANCE;
    }

}
