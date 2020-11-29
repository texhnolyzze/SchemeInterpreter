package interpreter;

import interpreter.exp.compound.*;
import interpreter.exp.Expression;

import java.io.*;
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
        this.analyzer = new Analyzer(inOut, predefined);
    }

    public void start() throws IOException {
        inOut.out().println(
            "This is simple Scheme evaluator implemented in Java language.\n" +
            "To use it just write as many Scheme expressions as you want and then type 'done' on the new line.\n" +
            "To exit just type 'exit' on the new line."
        );
        String s;
        BufferedReader r = new BufferedReader(new InputStreamReader(inOut.in()));
        StringBuilder next = new StringBuilder();
        while (true) {
            s = r.readLine().strip();
            if (s.equals("done")) {
                process(next);
            } else if (s.equals("exit")) {
                System.exit(0);
            } else
                next.append(s);
        }
    }

    private void process(StringBuilder next) {
        try {
            List<Object> read = reader.read(next.toString());
            inOut.out().println(read);
            for (Iterator<Object> iterator = read.iterator(); iterator.hasNext(); ) {
                Object exp = iterator.next();
                eval(exp, !iterator.hasNext());
            }
            inOut.out().println("We're done too");
        } catch (IllegalArgumentException e) {
            inOut.out().println("Error during evaluation: " + e.getMessage());
        }
        next.setLength(0);
    }

    private void eval(Object obj, final boolean last) {
        Expression analyze = analyzer.analyze(obj);
        Expression eval = analyze.eval(rootEnvironment);
        if (last && eval.getClass() != PrintExpression.class)
            inOut.out().println(eval);
    }

    public static void main(String[] args) throws IOException {
        Environment rootEnvironment = Environment.create(Map.of());
        Map<String, Class<? extends Expression>> predefined = new HashMap<>();
        predefined.put("print", PrintExpression.class);
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
        predefined.put("+", PlusExpression.class);
        predefined.put("-", MinusExpression.class);
        predefined.put("null?", IsNilExpression.class);
        predefined.put("eq?", EqExpression.class);
        predefined.put("define", DefineExpression.class);
        predefined.put("set!", SetExpression.class);
        predefined.put("lambda", LambdaExpression.class);
        predefined.put("begin", BeginExpression.class);
        predefined.put("*", MulExpression.class);
        predefined.put("list", ListExpression.class);
        predefined.put("%", ModExpression.class);
        predefined.put("/", DivExpression.class);
        predefined.put("while", WhileExpression.class);
        Driver driver = new Driver(rootEnvironment, predefined, new InOut(System.in, System.out, System.err));
        driver.start();
    }

}
