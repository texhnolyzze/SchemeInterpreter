package interpreter;

import java.io.InputStream;
import java.io.PrintStream;

public class InOut {

    private InputStream in;
    private final PrintStream out;
    private final PrintStream err;

    public InOut(InputStream in, PrintStream out, PrintStream err) {
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

}
