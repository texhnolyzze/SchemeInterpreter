package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class ConsExpression extends BaseExpression {

    private final Expression car;
    private final Expression cdr;

    public ConsExpression(List<?> list, Analyzer analyzer) {
        super(list);
        assertNumArgs(list, 2);
        this.car = analyzer.analyze(list.get(1));
        this.cdr = analyzer.analyze(list.get(2));
    }

    @Override
    public Expression eval(Environment env) {
        return PairExpression.cons(car.eval(env), cdr.eval(env));
    }

}
