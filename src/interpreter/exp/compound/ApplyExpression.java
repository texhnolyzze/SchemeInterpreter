package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.Function;
import interpreter.exp.compound.function.builtin.DisplayExpression;
import interpreter.exp.self.NewLineExpression;

import java.util.ArrayList;
import java.util.List;

public class ApplyExpression extends BaseExpression {

    private final Expression function;
    private final List<Expression> args;

    public ApplyExpression(List<?> list, Analyzer analyzer) {
        super(list);
        this.function = analyzer.analyze(list.get(0));
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
            Function proc = lookupFunc(env);
            return proc.eval(env, args);
        } finally {
            IN_TRAMPOLINE.set(prevState);
        }
    }

    void storeFuncToCall(TrampolineCtx ctx) {
        ctx.func = lookupFunc(ctx.environment);
        ctx.environment = ctx.func.bind(args, ctx.environment);
        ctx.args = args;
    }

    private Function lookupFunc(Environment env) {
        Expression exp = function.eval(env);
        Util.assertNotNull(exp);
        Util.assertType(exp, Function.class);
        return (Function) exp;
    }

    public boolean printingFunc(final Environment env) {
        final Expression proc = function.eval(env);
        return proc == DisplayExpression.INSTANCE || proc == NewLineExpression.INSTANCE;
    }

}
