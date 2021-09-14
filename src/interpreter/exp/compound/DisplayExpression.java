package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;

import java.io.PrintStream;
import java.util.List;

public class DisplayExpression extends BaseExpression {

    private final PrintStream out;
    private final Expression arg;

    public DisplayExpression(List<?> list, Analyzer analyzer) {
        super(list);
        assertNumArgs(list, 1);
        this.out = analyzer.inOut().out();
        this.arg = analyzer.analyze(list.get(1));
    }

    @Override
    public Expression eval(Environment env) {
        out.print(arg.eval(env));
        return NilExpression.INSTANCE;
    }

}
