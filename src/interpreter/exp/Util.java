package interpreter.exp;

import interpreter.EvaluationException;
import interpreter.exp.self.NilExpression;

import java.util.List;

public final class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void append(
        final List left,
        final List right
    ) {
        left.addAll(right);
    }

    public static void assertNumArgs(
        final int offset,
        final List<?> args,
        final int expected,
        final Expression source
    ) {
        if (args.size() - offset != expected) {
            throw new EvaluationException(source + ": Expected " + expected + " args, got " + (args.size() - offset));
        }
    }

    public static void assertNumArgs(
        final List<?> args,
        final int expected,
        final Expression source
    ) {
        assertNumArgs(1, args, expected, source);
    }

    public static void assertAtLeastNumArgs(
        final int offset,
        final List<?> args,
        final int expected,
        final Expression source
    ) {
        if (args.size() - offset < expected) {
            throw new EvaluationException(source + ": Expected at least " + expected + " args, got " + (args.size() - offset));
        }
    }

    public static void assertAtLeastNumArgs(
        final List<?> args,
        final int expected,
        final Expression source
    ) {
        assertAtLeastNumArgs(1, args, expected, source);
    }

    public static void assertType(
        final Object obj,
        final Class<?> expected,
        final Expression source
    ) {
        if (!expected.isAssignableFrom(obj.getClass())) {
            throw new EvaluationException(source + ": Expected arg type is " + expected.getSimpleName() + ", actual is " + obj.getClass().getSimpleName());
        }
    }

    public static void assertNotNull(final Expression expression, final Expression source) {
        if (expression == NilExpression.INSTANCE) {
            throw new EvaluationException(source + ": null pointer dereference");
        }
    }

    public static void assertSymbol(Object o) {
        final boolean symbol = o instanceof String s && s.charAt(0) != '"';
        if (!symbol) {
            throw new EvaluationException("symbol expected, got " + o);
        }
    }

    public static void assertList(Object o) {
        if (!(o instanceof List)) {
            throw new EvaluationException("list expected, got " + o);
        }
    }

}
