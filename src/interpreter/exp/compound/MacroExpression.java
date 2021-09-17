package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.Macro;

import java.util.List;
import java.util.Map;

public class MacroExpression extends BaseExpression {

    private final List<String> params;
    private final SequenceExpression body;

    public MacroExpression(final List<?> list, final Analyzer analyzer) {
        Util.assertAtLeastNumArgs(list, 2);
        Util.assertList(list.get(1));
        this.params = ((List<?>) list.get(1)).stream().peek(Util::assertSymbol).map(String.class::cast).toList();
        this.body = new SequenceExpression(2, list, analyzer);
    }

    public MacroExpression(
        final List<String> params,
        final SequenceExpression body
    ) {
        this.params = params;
        this.body = body;
    }

    @Override
    public Expression eval(final Environment env) {
        return new Macro(body, params);
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new MacroExpression(
            this.params,
            body.expand(params, env)
        );
    }

    @Override
    public String toString() {
        return "(macro (" + String.join(" ", params) + ") " + body.toString() + ")";
    }

}
