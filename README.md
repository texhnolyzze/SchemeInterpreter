This is a simple scheme interpreter written by me in 
Java after reading SICP book.

It supports scheme features, such as:
1) quote expression (both characters ` and ' are considered as quotations)
2) eval expression
3) car/cdr/cons expressions
4) Function calls looks like:

```
(define (proc x) x)
(proc 10) ; 1
```

or (if no function name explicitly provided):

```
(define (proc x) x)
(define pair (cons 1 proc))  
((cdr pair) 1)
```

5) Also single `while` cycle:

```
(define i 0)
(while (< i 100) (print i) (+= i 1))
```

6) As you noticed in the previous example, operators such as:

`+=`, `-=`, `/=`, `*=` and `%=` are also supported.
I add them to reduce costs of creating wrappers over numbers. 
So this operators take the leftmost operand and modify it's value 
(instead of returning a new copy of number).

7) Tail recursion implemented via `trampoline`. You can see it's implementation in 
   `BaseExpression#trampoline(Expression, Environment)`
   
8) force/delay expressions which are just a syntactic sugar:
```
(define x (delay (1) (2) (3))) 
   is just 
(define x (lambda () (1) (2) (3)))
```

```
(force x) 
   is just 
(x)
```

Although `force` is longer -- using it is more convenient.

If you're interested -- you can see some other features in Driver class.

Few examples:

SICP streams:
```
(define (cons-stream x y)
	(cons (cons x y) false))
	
(define (stream-car stream)
	(car (car stream)))
	
(define (stream-cdr stream)
	(if (cdr stream)
		(cdr stream)
	(begin 
		(set-cdr! stream ((cdr (car stream))))
		(cdr stream))))
		
(define (stream-ref s n)
	(if (= n 0)
		(stream-car s)
	(stream-ref (stream-cdr s) (- n 1))))
	
(define (fibgen a b) 
    (cons-stream a (lambda () (fibgen b (+ a b)))))

(define fibs (fibgen 0 1))

(stream-ref fibs 20) ;6765
```

Tail recursion:
```
(define (f x) 
    (if (< x 100000) 
        (f (+ x 1)) 
    "done"))
(f 0) ;; will fail with StackOverflowError if tree recursion wasn't implemented
```

Also nested tail recursion (in which one tail recursive function 
in the middle of it's body calls another tail recursive function and so on) works. For example: 

```
(define (f x)
	(display x)
	(newline)
    (if (< x 1000000)
		(if (= (% x 2) 0) 
			(g (+ x 1)) 
			(z (+ x 1)))
	"done"))

(define (g x)
	(display x)
	(newline)
	(if (< x 1000000)
		(if (= (% x 2) 0) 
			(z (+ x 1)) 
			(p (+ x 1)))
	"done"))
	
(define (z x)
	(display x)
	(newline)
	(if (< x 1000000)
		(if (= (% x 2) 0) 
			(p (+ x 1)) 
			(f (+ x 1)))
	"done"))
	
(define (p x)
	(display x)
	(newline)
	(if (< x 1000000)
		(if (= (% x 2) 0) 
			(f (+ x 1)) 
			(g (+ x 1)))
	"done"))
	
(f 0)
```

quote and eval:

```
(define (proc x) x)
(eval `(proc 1))
```