package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;

import java.util.*;
import java.util.stream.Collectors;

public class LetExpression extends BaseExpression {

    private final List<DefineExpression> inits;
    private final List<Expression> forms;

    public LetExpression(
        final List<?> list,
        final Analyzer analyzer
    ) {
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

    private LetExpression(
        final List<DefineExpression> inits,
        final List<Expression> forms
    ) {
        this.inits = inits;
        this.forms = forms;
    }

    @Override
    public Expression eval(final Environment env) {
        final Environment extended = env.extend(Collections.emptyMap());
        for (int i = 0; i < inits.size(); i++) {
            final DefineExpression exp = inits.get(i);
            exp.eval(extended);
        }
        for (int i = 0; i < forms.size(); i++) {
            if (i == forms.size() - 1) {
                return trampoline(forms.get(i), extended);
            } else {
                forms.get(i).eval(extended);
            }
        }
        throw new AssertionError();
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        final List<DefineExpression> initsExpanded = new ArrayList<>(inits.size());
        final List<Expression> formsExpanded = new ArrayList<>(forms.size());
        for (int i = 0; i < inits.size(); i++) {
            initsExpanded.add(inits.get(i).expand(params, env));
        }
        for (int i = 0; i < forms.size(); i++) {
            formsExpanded.add(forms.get(i).expand(params, env));
        }
        return new LetExpression(initsExpanded, formsExpanded);
    }

    @Override
    public String toString() {
        return "(let (" + inits.stream().map(e -> e.name() + " " + e.definition().toString()).collect(Collectors.joining(" ", "(", ")")) + ") " + forms.stream().map(Objects::toString).collect(Collectors.joining(" ")) + ")";
    }

}
