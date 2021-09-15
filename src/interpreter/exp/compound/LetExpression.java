package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;

import java.util.ArrayList;
import java.util.List;

public class LetExpression extends BaseExpression {

    private final List<DefineExpression> inits;
    private final List<Expression> forms;

    public LetExpression(
        final List<?> list,
        final Analyzer analyzer
    ) {
        super(list);
        Util.assertAtLeastNumArgs(list, 2);
        Util.assertList(list.get(1));
        final List<?> lets = (List<?>) list.get(1);
        Util.assertAtLeastNumArgs(0, lets, 1);
        this.inits = new ArrayList<>(lets.size());
        for (int i = 0; i < lets.size(); i++) {
            final Object o = lets.get(i);
            Util.assertList(o);
            final List let = (List<?>) o;
            let.add(0, "define");
            inits.add(new DefineExpression(let, analyzer));
        }
        this.forms = new ArrayList<>();
        for (int i = 2; i < list.size(); i++) {
            forms.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    public Expression eval(final Environment env) {
        for (int i = 0; i < inits.size(); i++) {
            final DefineExpression exp = inits.get(i);
            exp.eval(env);
        }
        for (int i = 0; i < forms.size(); i++) {
            if (i == forms.size() - 1) {
                return trampoline(forms.get(i), env);
            } else {
                forms.get(i).eval(env);
            }
        }
        throw new AssertionError();
    }

}
