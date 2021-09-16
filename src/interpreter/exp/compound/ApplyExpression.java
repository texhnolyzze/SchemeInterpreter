package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.Function;
import interpreter.exp.compound.function.Macro;
import interpreter.exp.compound.function.builtin.DisplayExpression;
import interpreter.exp.self.NewLineExpression;

import java.util.*;
import java.util.stream.Collectors;

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

    private ApplyExpression(
        final Expression function,
        final List<Expression> args
    ) {
        super(null);
        this.function = function;
        this.args = args;
    }

    @Override
    public Expression eval(Environment env) {
        Boolean prevState = IN_TRAMPOLINE.get();
        IN_TRAMPOLINE.set(false);
        try {
            Function func = lookupFunc(env);
            return func.eval(env, args);
        } finally {
            IN_TRAMPOLINE.set(prevState);
        }
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        final Expression expandedFunc = this.function.expand(params, env);
        if (expandedFunc.eval(env) instanceof Macro m) {
            final Map<String, Expression> nestedExpansionParams = new HashMap<>(args.size());
            final List<String> macroParams = m.params();
            for (int i = 0; i < macroParams.size(); i++) {
                nestedExpansionParams.put(
                    macroParams.get(i),
                    args.get(i).expand(params, env)
                );
            }
            return m.expand(nestedExpansionParams, env);
        } else {
            final List<Expression> expandedArgs = new ArrayList<>(args.size());
            for (int i = 0; i < args.size(); i++) {
                expandedArgs.add(args.get(i).expand(params, env));
            }
            return new ApplyExpression(
                expandedFunc,
                expandedArgs
            );
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

    @Override
    public String toString() {
        final String s = "(" + function;
        return args.isEmpty() ? s + ")" : s + " " + args.stream().map(Objects::toString).collect(Collectors.joining(" ")) + ")";
    }

}
