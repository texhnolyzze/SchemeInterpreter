package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NumberExpression;

import java.util.ArrayList;
import java.util.List;

public class ModExpression extends NumberCombineExpression {

    public ModExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.mod(right);
    }

}
