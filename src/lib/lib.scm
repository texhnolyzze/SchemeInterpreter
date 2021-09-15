;;Stream support

(define (cons-stream x y)
	(cons (cons x y) false))

(define (stream-car stream)
	(car (car stream)))

(define (stream-cdr stream)
	(if (cdr stream)
		(cdr stream)
	(begin
		(set-cdr! stream (force (cdr (car stream))))
		(cdr stream))))

(define (stream-ref s n)
	(if (= n 0)
		(stream-car s)
	(stream-ref (stream-cdr s) (- n 1))))

(define (stream-map stream mapper)
	(if (null? stream)
		nil
	(cons-stream
	    (mapper (stream-car stream))
	    (delay (stream-map (stream-cdr stream) mapper)))))

(define (stream-filter pred stream)
    (cond   ((null? stream) nil)
            ((pred (stream-car stream))
                (cons-stream
                    (stream-car stream)
                    (delay (stream-filter pred (stream-cdr stream)))))
            (else (stream-filter pred (stream-cdr stream)))))

(define (stream-for-each proc s)
    (if (null? s)
        'done
    (begin
        (proc (stream-car s))
        (stream-for-each proc (stream-cdr s)))))

(define (display-stream s)
    (stream-for-each (lambda (elem) (print elem)) s))

;; Math functions

(define (abs x) (if (< x 0) (- 0 x) x))

(define (square x) (* x x))

(define gcd (lambda (a b)
	(if (= b 0)
		a
	(gcd b (% a b)))))

(define (divisible? x y) (= (% x y) 0))

(define (append lst tail)
  (if (null? lst)
      tail
      (cons (car lst)
            (append (cdr lst)
                    tail))))

(define (length lis)
   (cond ((null? lis)
          0)
         (else
          (+ 1 (length (cdr lis))))))

(define (zero? n) (= n 0))

(define (abs x)
  (cond ((> x 0) x)
        ((= x 0) 0)
        ((< x 0) (- x))))

done
