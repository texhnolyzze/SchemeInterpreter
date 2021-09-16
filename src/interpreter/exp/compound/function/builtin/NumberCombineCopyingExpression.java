package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.self.NumberExpression;

public abstract class NumberCombineCopyingExpression extends NumberCombineExpression {

    protected NumberCombineCopyingExpression() {
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
