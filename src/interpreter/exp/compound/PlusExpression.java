package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NumberExpression;

import java.util.ArrayList;
import java.util.List;

public class PlusExpression extends CompoundExpression {

    private final List<Expression> args;

    public PlusExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 2);
        this.args = new ArrayList<>(2);
        for (int i = 1; i < list.size(); i++) {
            this.args.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    public Expression eval(Environment env) {
        NumberExpression res = null;
        for (Expression e : args) {
            Expression eval = e.eval(env);
            assertNotNull(eval);
            assertType(eval, NumberExpression.class);
            res = res == null ? ((NumberExpression) eval).copy() : res.add((NumberExpression) eval);
        }
        return res;
    }

}
