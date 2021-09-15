package interpreter;

import interpreter.exp.Expression;
import interpreter.exp.VariableExpression;
import interpreter.exp.compound.ApplyExpression;
import interpreter.exp.compound.procedure.BuiltInProcedure;
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

    private final Map<String, Class<? extends Expression>> predefined;

    private final ConcurrentMap<Class<? extends Expression>, Constructor<? extends Expression>> constructors;

    public Analyzer(Map<String, Class<? extends Expression>> predefined) {
        this.predefined = predefined;
        this.constructors = new ConcurrentHashMap<>();
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
            if (s.startsWith("\"")) {
                return new StringExpression(s);
            } else if (INT.matcher(s).matches()) {
                return new IntExpression(Long.parseLong(s));
            } else if (DEC.matcher(s).matches()) {
                return new DecimalExpression(Double.parseDouble(s));
            } else if (NilExpression.INSTANCE.toString().equals(s)) {
                return NilExpression.INSTANCE;
            } else if (TrueExpression.INSTANCE.toString().equals(s)) {
                return TrueExpression.INSTANCE;
            } else if (FalseExpression.INSTANCE.toString().equals(s)) {
                return FalseExpression.INSTANCE;
            } else if ("newline".equals(s)) {
                return NewLineExpression.INSTANCE;
            } else if (predefined.containsKey(s) && BuiltInProcedure.class.isAssignableFrom(predefined.get(s))) {
                final Class<? extends Expression> c = predefined.get(s);
                try {
                    return (Expression) c.getField("INSTANCE").get(c);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    throw new AssertionError();
                }
            } else {
                return new VariableExpression(s);
            }
        }
        return null;
    }

    private Expression analyzeList(List<Object> exp) {
        if (exp.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }
        Object type = exp.get(0);
        Class<? extends Expression> c = predefined.get(type);
        if (c != null) {
            if (SelfEvaluatingExpression.class.isAssignableFrom(c)) {
                try {
                    return (Expression) c.getField("INSTANCE").get(c);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new AssertionError();
                }
            } else if (BuiltInProcedure.class.isAssignableFrom(c)) {
                return new ApplyExpression(exp, this);
            } else {
                Constructor<? extends Expression> constructor = constructors.computeIfAbsent(
                    c,
                    clazz -> (Constructor<? extends Expression>) clazz.getDeclaredConstructors()[0]
                );
                final Class<?>[] params = constructor.getParameterTypes();
                try {
                    if (params.length == 2 && params[0] == List.class && params[1] == Analyzer.class) {
                        return constructor.newInstance(exp, this);
                    } else {
                        throw new AssertionError();
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    return fail((IllegalArgumentException) e.getCause());
                }
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
