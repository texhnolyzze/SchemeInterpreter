package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.exp.self.NumberExpression;

import java.util.List;

public class MinusExpression extends NumberCombineExpression {

    public MinusExpression(List<?> list, Analyzer analyzer) {
        super(negateSingleArgument(list), analyzer);
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.sub(right);
    }

    private static List<?> negateSingleArgument(List<?> list) {
        if (list.size() == 2)
            return List.of(list.get(0), "0", list.get(1));
        return list;
    }

}
