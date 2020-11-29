package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.BooleanExpression;

import java.util.List;

public class NotExpression extends CompoundExpression {

    private final Expression arg;

    public NotExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 1);
        this.arg = analyzer.analyze(list.get(1));
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = arg.eval(env);
        assertNotNull(eval);
        assertType(eval, BooleanExpression.class);
        return ((BooleanExpression) eval).not();
    }

}
