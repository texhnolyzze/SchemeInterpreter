package interpreter.exp.compound;

import interpreter.Analyzer;

import java.util.List;

public abstract class NumberCombineCopyingExpression extends NumberCombineExpression {

    protected NumberCombineCopyingExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected boolean mustCopy() {
        return true;
    }

}
