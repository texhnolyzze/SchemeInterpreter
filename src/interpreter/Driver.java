package interpreter;

import interpreter.exp.Expression;
import interpreter.exp.compound.*;
import interpreter.exp.compound.function.builtin.*;
import interpreter.exp.self.NewLineExpression;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Driver {

    private final Environment rootEnvironment;
    private final InOut inOut;
    private final LispReader reader;
    private final Analyzer analyzer;

    public Driver(
        Environment rootEnvironment,
        Map<String, Class<? extends Expression>> predefined,
        InOut inOut
    ) {
        this.rootEnvironment = rootEnvironment;
        this.inOut = inOut;
        this.reader = new LispReader();
        this.analyzer = new Analyzer(predefined);
    }

    public void start() throws IOException {
        inOut.out().println(
            """
            This is a simple Scheme evaluator implemented in Java language
            To use it just write as many Scheme expressions as you want and then type 'done' on the new line.
            To exit just type 'exit' on the new line.
            """
        );
        installLibrary();
        BufferedReader r = new BufferedReader(new InputStreamReader(inOut.in()));
        loop(r, false, "We're done too", true);
    }

    private void loop(BufferedReader r, boolean breakOnDone, String messageOnDone, boolean print) throws IOException {
        String s;
        StringBuilder next = new StringBuilder();
        while (true) {
            s = r.readLine().strip();
            if (s.equals("done")) {
                process(next, messageOnDone, print);
                if (breakOnDone) {
                    break;
                }
            } else if (s.equals("exit")) {
                System.exit(0);
            } else {
                next.append(s).append('\n');
            }
        }
    }

    private void installLibrary() throws IOException {
        InputStream old = inOut.in();
        BufferedInputStream in = new BufferedInputStream(Files.newInputStream(Path.of(".", "src", "lib", "lib.scm")));
        inOut.setIn(in);
        loop(new BufferedReader(new InputStreamReader(in)), true, "\nStandard library installed", false);
        inOut.setIn(old);
    }

    private void process(StringBuilder next, String messageOnDone, boolean print) {
        try {
            long total = 0L;
            long start = System.currentTimeMillis();
            List<Object> read = reader.read(next.toString());
            total += System.currentTimeMillis() - start;
            start = System.currentTimeMillis();
            for (Iterator<Object> iterator = read.iterator(); iterator.hasNext(); ) {
                Object exp = iterator.next();
                eval(exp, !iterator.hasNext(), print);
            }
            long end = System.currentTimeMillis();
            total += (end - start);
            inOut.out().println(messageOnDone);
            inOut.out().println("Run time " + total + " ms");
        } catch (IllegalArgumentException e) {
            inOut.err().println("Error during evaluation: " + e.getMessage());
        }
        next.setLength(0);
    }

    private void eval(
        final Object obj,
        final boolean last,
        final boolean print
    ) {
        final Expression analyze = analyzer.analyze(obj);
        final Expression eval = analyze.eval(rootEnvironment);
        if (
            last &&
            print &&
            (
                analyze.getClass() != ApplyExpression.class ||
                !((ApplyExpression) analyze).printingProc(rootEnvironment)
            )
        ) {
            inOut.out().println(eval);
        }
    }

    public static void main(String[] args) throws IOException {
        Environment rootEnvironment = Environment.create(Map.of());
        Map<String, Class<? extends Expression>> predefined = new HashMap<>();
        predefined.put("display", DisplayExpression.class);
        predefined.put("cons", ConsExpression.class);
        predefined.put("car", CarExpression.class);
        predefined.put("cdr", CdrExpression.class);
        predefined.put("<", LessThanExpression.class);
        predefined.put(">", GreaterThanExpression.class);
        predefined.put("and", AndExpression.class);
        predefined.put("or", OrExpression.class);
        predefined.put("if", IfExpression.class);
        predefined.put("=", NumberEqExpression.class);
        predefined.put("!=", NumberNotEqExpression.class);
        predefined.put("not", NotExpression.class);
        predefined.put("+", AddExpression.class);
        predefined.put("-", SubtractExpression.class);
        predefined.put("null?", IsNilExpression.class);
        predefined.put("eq?", EqExpression.class);
        predefined.put("define", DefineExpression.class);
        predefined.put("set!", SetExpression.class);
        predefined.put("lambda", LambdaExpression.class);
        predefined.put("begin", BeginExpression.class);
        predefined.put("*", MultiplyExpression.class);
        predefined.put("list", ListExpression.class);
        predefined.put("%", ModExpression.class);
        predefined.put("/", DivideExpression.class);
        predefined.put("while", WhileExpression.class);
        predefined.put("quote", QuoteExpression.class);
        predefined.put("eval", EvalExpression.class);
        predefined.put("set-car!", SetCarExpression.class);
        predefined.put("set-cdr!", SetCdrExpression.class);
        predefined.put("+=", AddAndSetExpression.class);
        predefined.put("-=", SubtractAndSetExpression.class);
        predefined.put("*=", MultiplyAndSetExpression.class);
        predefined.put("/=", DivideAndSetExpression.class);
        predefined.put("%=", ModAndSetExpression.class);
        predefined.put("cond", CondExpression.class);
        predefined.put("delay", DelayExpression.class);
        predefined.put("force", ForceExpression.class);
        predefined.put("assert", AssertExpression.class);
        predefined.put("pair?", IsPairExpression.class);
        predefined.put("remainder", ModExpression.class);
        predefined.put("newline", NewLineExpression.class);
        predefined.put("<=", LessThanOrEqualExpression.class);
        predefined.put(">=", GreaterThanOrEqualExpression.class);
        Driver driver = new Driver(rootEnvironment, predefined, InOut.create(System.in, System.out, System.err));
        driver.start();
    }

}
