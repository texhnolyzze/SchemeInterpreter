package interpreter.exp.compound.function.builtin;

import interpreter.exp.Expression;
import interpreter.exp.self.IntExpression;
import interpreter.exp.self.NumberExpression;

import java.util.List;

public class SubtractExpression extends NumberCombineCopyingExpression {

    public static final SubtractExpression INSTANCE = new SubtractExpression();

    private SubtractExpression() {
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.sub(right);
    }

    /**
     * Negate single argument
     */
    @Override
    protected List<Expression> modify(final List<Expression> args) {
        if (args.size() == 1) {
            args.add(0, IntExpression.ZERO.copy());
        }
        return super.modify(args);
    }

}
