package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.self.NumberExpression;

import java.util.List;

public abstract class NumberCombineCopyingExpression extends NumberCombineExpression {

    protected NumberCombineCopyingExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected boolean mustCopy() {
        return true;
    }

    @Override
    protected void onTypeChanged(Environment env, NumberExpression prev, NumberExpression newType) {
//        do nothing
    }

}
