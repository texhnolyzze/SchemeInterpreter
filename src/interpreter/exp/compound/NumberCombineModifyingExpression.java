package interpreter.exp.compound;

import interpreter.Analyzer;

import java.util.List;

public abstract class NumberCombineModifyingExpression extends NumberCombineExpression {

    protected NumberCombineModifyingExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected boolean mustCopy() {
        return false;
    }

}
