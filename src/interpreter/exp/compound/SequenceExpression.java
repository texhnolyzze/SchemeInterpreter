package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SequenceExpression extends CompoundExpression {

    private final List<Expression> seq;

    SequenceExpression(int index, List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(index, list, 1);
        this.seq = new ArrayList<>(1);
        for (int i = index; i < list.size(); i++) {
            this.seq.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    public Expression eval(Environment env) {
        for (Iterator<Expression> iterator = seq.iterator();;) {
            Expression e = iterator.next();
            if (!iterator.hasNext()) {
                return trampoline(e, env);
            } else
                e.eval(env);
        }
    }

}
