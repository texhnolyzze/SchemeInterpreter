package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class ListExpression implements BuiltInFunction {

    public static final ListExpression INSTANCE = new ListExpression();

    private ListExpression() {
    }
    
    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        if (args.isEmpty()) {
            return NilExpression.INSTANCE;
        }
        final PairExpression head = PairExpression.cons(NilExpression.INSTANCE, NilExpression.INSTANCE);
        PairExpression curr = head;
        for (int i = 0;;) {
            Expression next = args.get(i++);
            Expression eval = next.eval(env);
            curr.setCar(eval);
            if (i < args.size()) {
                PairExpression cdr = PairExpression.cons(NilExpression.INSTANCE, NilExpression.INSTANCE);
                curr.setCdr(cdr);
                curr = cdr;
            } else {
                break;
            }
        }
        return head;
    }

}
