package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class WhileExpression extends BaseExpression {

    private final Expression predicate;
    private final SequenceExpression body;

    public WhileExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertAtLeastNumArgs(list, 2);
        this.predicate = analyzer.analyze(list.get(1));
        this.body = new SequenceExpression(2, list, analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        while (predicate.eval(env) == TrueExpression.INSTANCE) {
            body.eval(env);
        }
        return NilExpression.INSTANCE;
    }

}
