package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;

import java.util.ArrayList;
import java.util.List;

public class SequenceExpression extends BaseExpression {

    private final List<Expression> seq;

    SequenceExpression(int index, List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertAtLeastNumArgs(index, list, 1);
        this.seq = new ArrayList<>(1);
        for (int i = index; i < list.size(); i++) {
            this.seq.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    public Expression eval(Environment env) {
        for (int i = 0;;) {
            Expression e = seq.get(i++);
            if (i < seq.size()) {
                e.eval(env);
            } else {
                return trampoline(e, env);
            }
        }
    }

}
