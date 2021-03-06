package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.exp.self.NumberExpression;

import java.util.List;

public class MultiplyAndSetExpression extends NumberCombineModifyingExpression {

    public MultiplyAndSetExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.mul(right);
    }

}
