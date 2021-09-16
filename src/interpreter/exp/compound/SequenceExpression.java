package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private SequenceExpression(
        final List<Expression> seq
    ) {
        super(null);
        this.seq = seq;
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

    @Override
    public SequenceExpression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        final List<Expression> expanded = new ArrayList<>(seq.size());
        for (int i = 0; i < seq.size(); i++) {
            expanded.add(seq.get(i).expand(params, env));
        }
        return new SequenceExpression(expanded);
    }

    @Override
    public String toString() {
        return seq.stream().map(Objects::toString).collect(Collectors.joining(" "));
    }

}
