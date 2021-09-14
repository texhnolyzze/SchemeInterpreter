package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.PairExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class IsPairExpression extends BaseExpression {

    private final Expression arg;

    public IsPairExpression(
        final List<?> list,
        final Analyzer analyzer
    ) {
        super(list);
        assertNumArgs(list, 1);
        this.arg = analyzer.analyze(list.get(1));
    }

    @Override
    public Expression eval(final Environment env) {
        final Expression eval = arg.eval(env);
        return eval instanceof PairExpression ?
               TrueExpression.INSTANCE :
               FalseExpression.INSTANCE;
    }

}
