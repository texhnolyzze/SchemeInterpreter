package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;

import java.io.PrintStream;
import java.util.List;

public class PrintExpression extends CompoundExpression {

    private final PrintStream out;
    private final Expression arg;

    public PrintExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 1);
        this.out = analyzer.inOut().out();
        this.arg = analyzer.analyze(list.get(1));
    }

    @Override
    public Expression eval(Environment env) {
        out.println(arg.eval(env));
        return null;
    }

}
