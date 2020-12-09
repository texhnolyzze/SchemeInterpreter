package interpreter;

import interpreter.exp.Expression;
import interpreter.exp.VariableExpression;
import interpreter.exp.compound.ApplyExpression;
import interpreter.exp.self.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class Analyzer {

    private static final Pattern INT = Pattern.compile("[-+]?\\d+");
    private static final Pattern DEC = Pattern.compile("[-+]?\\d+.\\d+");

    private final InOut inOut;
    private final Map<String, Class<? extends Expression>> predefined;

    private final ConcurrentMap<Class<? extends Expression>, Constructor<? extends Expression>> constructors;

    public Analyzer(InOut inOut, Map<String, Class<? extends Expression>> predefined) {
        this.inOut = inOut;
        this.predefined = predefined;
        this.constructors = new ConcurrentHashMap<>();
    }

    public InOut inOut() {
        return inOut;
    }

    @SuppressWarnings("unchecked")
    public Expression analyze(Object exp) {
        Expression expression = analyzeString(exp);
        if (expression != null) {
            return expression;
        } else { // list
            return analyzeList((List<Object>) exp);
        }
    }

    private Expression analyzeString(Object exp) {
        if (exp.getClass() == String.class) {
            String s = (String) exp;
            if (s.startsWith("\""))
                return new StringExpression(s);
            else if (INT.matcher(s).matches())
                return new IntExpression(Long.parseLong(s));
            else if (DEC.matcher(s).matches())
                return new DecimalExpression(Double.parseDouble(s));
            else if (NilExpression.INSTANCE.toString().equals(s))
                return NilExpression.INSTANCE;
            else if (TrueExpression.INSTANCE.toString().equals(s))
                return TrueExpression.INSTANCE;
            else if (FalseExpression.INSTANCE.toString().equals(s))
                return FalseExpression.INSTANCE;
            else
                return new VariableExpression(s);
        }
        return null;
    }

    private Expression analyzeList(List<Object> exp) {
        if (exp.isEmpty())
            throw new IllegalArgumentException("Empty expression");
        Object type = exp.get(0);
        Class<? extends Expression> c = predefined.get(type);
        if (c != null) {
            Constructor<? extends Expression> constructor = constructors.computeIfAbsent(c, clazz -> {
                try {
                    return clazz.getDeclaredConstructor(List.class, Analyzer.class);
                } catch (NoSuchMethodException e) {
                    return fail((IllegalArgumentException) e.getCause());
                }
            });
            try {
                return constructor.newInstance(exp, this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                return fail((IllegalArgumentException) e.getCause());
            }
        } else {
            return new ApplyExpression(exp, this);
        }
    }

    private <T> T fail(IllegalArgumentException e) {
        throw e;
    }

    public Map<String, Class<? extends Expression>> predefined() {
        return predefined;
    }

}
