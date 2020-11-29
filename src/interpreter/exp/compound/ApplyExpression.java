package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.ProcedureExpression;

import java.util.*;

public class ApplyExpression extends CompoundExpression {

    private final String procedure;
    private final List<Expression> args;

    public ApplyExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        this.procedure = (String) list.get(0);
        this.args = new ArrayList<>(0);
        for (int i = 1; i < list.size(); i++) {
            this.args.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    public Expression eval(Environment env) {
        Boolean prevState = IN_TRAMPOLINE.get();
        IN_TRAMPOLINE.set(false);
        try {
            ProcedureExpression proc = lookupProc(env);
            Environment extended = extendProcEnvironment(proc, env);
            return proc.eval(extended);
        } finally {
            IN_TRAMPOLINE.set(prevState);
        }
    }

    void storeProcedureToCall(Environment env, TrampolineCtx ctx) {
        ProcedureExpression proc = lookupProc(env);
        Environment environment = extendProcEnvironment(proc, env);
        ctx.proc = proc;
        ctx.extendedEnvironment = environment;
    }

    private ProcedureExpression lookupProc(Environment env) {
        Expression exp = env.lookup(procedure);
        assertNotNull(exp);
        assertType(exp, ProcedureExpression.class);
        return (ProcedureExpression) exp;
    }

    private Environment extendProcEnvironment(ProcedureExpression proc, Environment env) {
        List<String> params = proc.params();
        assertNumArgs(0, args, params.size());
        Iterator<String> iter1 = params.iterator();
        Iterator<Expression> iter2 = args.iterator();
        Environment procEnvironment = proc.env();
        Map<String, Expression> boundArgs = new HashMap<>(params.size());
        while (iter1.hasNext()) {
            String param = iter1.next();
            Expression arg = iter2.next().eval(env);
            boundArgs.put(param, arg);
        }
        return procEnvironment.extend(boundArgs);
    }

}
