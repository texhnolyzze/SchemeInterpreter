package interpreter;

import java.io.IOException;
import java.util.List;

public class DriverLoop {

    public static void main(String[] args) throws IOException {
        LispReader reader = new LispReader();
        List<Object> read = reader.read("(define (my-max3 x y z)\n" +
            "   (if (and (> x y) (> x z))\n" +
            "       x\n" +
            "       (if (> y z) \n" +
            "            y\n" +
            "            z)))  (define (my-max3 x y z)\n" +
            "   (if (and (> x y) (> x z))\n" +
            "       x\n" +
            "       (if (> y z) \n" +
            "            y\n" +
            "            z))) (define (sum a b) (+ a b)) (print (sum 1 1)) x y z () (print a)a()b(define a)(stack)(print \"\\\\1\\n\")");
        System.out.println(read);
    }

}
