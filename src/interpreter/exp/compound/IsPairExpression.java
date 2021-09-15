package interpreter.exp.compound;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.procedure.BuiltInProcedure;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.PairExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class IsPairExpression implements BuiltInProcedure {

    public static final IsPairExpression INSTANCE = new IsPairExpression();

    private IsPairExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 1);
        final Expression eval = args.get(0).eval(env);
        return eval instanceof PairExpression ?
               TrueExpression.INSTANCE :
               FalseExpression.INSTANCE;
    }

}
