package interpreter;

import java.io.InputStream;
import java.io.PrintStream;

public class InOut {

    private static InOut instance;

    private InputStream in;
    private final PrintStream out;
    private final PrintStream err;

    private InOut(
        final InputStream in,
        final PrintStream out,
        final PrintStream err
    ) {
        this.in = in;
        this.out = out;
        this.err = err;
    }

    public InputStream in() {
        return in;
    }

    public PrintStream out() {
        return out;
    }

    public PrintStream err() {
        return err;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public static InOut instance() {
        return instance;
    }

    public static InOut create(
        final InputStream in,
        final PrintStream out,
        final PrintStream err
    ) {
        final InOut res = new InOut(in, out, err);
        InOut.instance = res;
        return res;
    }

}
