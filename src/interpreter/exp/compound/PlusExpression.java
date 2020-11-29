package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.exp.self.NumberExpression;

import java.util.List;

public class PlusExpression extends NumberCombineExpression {

    public PlusExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.add(right);
    }

}
