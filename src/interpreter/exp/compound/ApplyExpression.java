package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Procedure;
import interpreter.exp.self.SelfEvaluatingExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplyExpression extends BaseExpression {

    private final Expression procedure;
    private final List<Expression> args;

    public ApplyExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        this.procedure = analyzer.analyze(list.get(0));
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
            Procedure proc = lookupProc(env);
            Environment extended = extendProcEnvironment(proc, env);
            return proc.eval(extended);
        } finally {
            IN_TRAMPOLINE.set(prevState);
        }
    }

    void storeProcedureToCall(TrampolineCtx ctx) {
        ctx.proc = lookupProc(ctx.environment);
        ctx.environment = extendProcEnvironment(ctx.proc, ctx.environment);
    }

    private Procedure lookupProc(Environment env) {
        Expression exp;
        if (procedure instanceof SelfEvaluatingExpression)
            exp = env.lookup(procedure.toString());
        else
            exp = procedure.eval(env);
        assertNotNull(exp);
        assertType(exp, Procedure.class);
        return (Procedure) exp;
    }

    private Environment extendProcEnvironment(Procedure proc, Environment env) {
        List<String> params = proc.params();
        assertNumArgs(0, args, params.size());
        Environment procEnvironment = proc.env();
        Map<String, Expression> boundArgs = new HashMap<>(params.size());
        for (int i = 0; i < params.size(); i++) {
            String param = params.get(i);
            Expression arg = args.get(i).eval(env);
            boundArgs.put(param, arg);
        }
        return procEnvironment.extend(boundArgs);
    }

}
