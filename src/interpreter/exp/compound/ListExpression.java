package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.PairExpression;

import java.util.ArrayList;
import java.util.List;

public class ListExpression extends BaseExpression {
    
    private final List<Expression> list;

    public ListExpression(List<?> list, Analyzer analyzer) {
        super(list);
        this.list = new ArrayList<>(0);
        for (int i = 1; i < list.size(); i++) {
            this.list.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    public Expression eval(Environment env) {
        if (list.isEmpty()) {
            return NilExpression.INSTANCE;
        }
        PairExpression head = PairExpression.cons(NilExpression.INSTANCE, NilExpression.INSTANCE);
        PairExpression curr = head;
        for (int i = 0;;) {
            Expression next = list.get(i++);
            Expression eval = next.eval(env);
            curr.setCar(eval);
            if (i < list.size()) {
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
