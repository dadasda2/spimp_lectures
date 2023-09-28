(ns spimp-lectures.lec4)

(defmacro infix [a op b] (list op a b))
(println (infix 1 + 3))
(macroexpand-1 '(infix 1 + 3))

(defmacro unless [test then else] (if test else then))
(unless (> 2 3) (println "a") (println "b"))
(println (macroexpand-1 '(unless (> 2 3) (println "a") (println "b"))))

(defmacro unless [test then else] (list 'if test else then))
(unless (> 2 3) (println "a") (println "b"))

(defmacro unless [test then else] `(if ~test ~else ~then))
(println (unless (> 2 3) (println "a") (println "b")))
(println (macroexpand-1 '(unless (> 2 3) (println "a") (println "b"))))

(defmacro defn-secure [name parameters & body]  `(defn ~name {:admin-only true} ~parameters ~body))
(macroexpand-1 '(defn-secure foo [a b] (+ a b)))


(defmacro defn-secure [name parameters & body]  `(defn ~name {:admin-only true} ~parameters ~@body))
(macroexpand-1 '(defn-secure foo [a b] (+ a b)(println  "foo called")))

(map str (filter #(= 2 %) [1 2 2 4]))
(defmacro log [call] `(println call) )

(defmacro log [call] `(let [result ~call] (println result) result)) (log (+ 1 2))
(defmacro log [call] `(let [result# ~call] (println result#) result#)) (log (+ 1 2))
(defmacro log [call] `(let [result# ~call] (println (str (quote~call)) "=" result#) result#)) (log (+ 1 2))

(defmacro defn-with-not [name parameters & body]
  `(defn ~name ~parameters ~@body)
  `(defn ~(symbol(str "not-" name)) ~parameters (not ~@body))
  )
(macroexpand-1 '(defn-with-not list-contains [elem ls] (some #(= elem %) ls)))
(defmacro defn-with-not [name parameters & body]
  (list `do `(defn ~name ~parameters ~@body)
        `(defn ~(symbol(str "not-" name)) ~parameters (not ~@body))
        ))
(macroexpand-1 '(defn-with-not list-contains [elem ls] (some #(= elem %) ls)))

(defmacro my-recursive-infix [form]
  (if-not (seq? form)
    form
    (let [[x op y] form]
      `(~op (my-recursive-infix ~x) (my-recursive-infix ~y))
      )))
