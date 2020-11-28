package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.ProcedureExpression;

import java.util.*;

public class ApplyExpression extends CompoundExpression {

    private final String procedure;
    private final List<Expression> args;

    public ApplyExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        this.procedure = (String) list.get(0);
        this.args = new ArrayList<>(0);
        for (int i = 1; i < list.size(); i++) {
            this.args.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    public Expression eval(Environment env) {
        Expression proc = env.lookup(procedure);
        assertNotNull(proc);
        assertType(proc, ProcedureExpression.class);
        List<String> params = ((ProcedureExpression) proc).params();
        assertNumArgs(0, args, params.size());
        Iterator<String> iter1 = params.iterator();
        Iterator<Expression> iter2 = args.iterator();
        Map<String, Expression> bindings = new HashMap<>(params.size());
        while (iter1.hasNext()) {
            String param = iter1.next();
            Expression arg = iter2.next().eval(env);
            bindings.put(param, arg);
        }
        return proc.eval(env.extend(bindings));
    }

}
