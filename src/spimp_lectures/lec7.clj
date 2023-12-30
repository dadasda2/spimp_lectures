(ns spimp-lectures.lec7)
;
;(def a 5)
;
;(def ^:dynamic b 5)
;
;(binding [b 6] (println b))
;(println b)
;
;(def ^:dynamic *config-path* "default/path")
;
;(with-redefs a 17)
;
;(def v (atom 1))
;(def v (atom ()))
;
;(println v)
;(println @v)
;
;(reset! v [1])
;(swap! v conj 3)
;(println @v)
;
;(println (compare-and-set! v () (list 1 2 3)))
;(println @v)

(def age (agent (list 1 2)))
(send age conj 3)
(await age)
(println @age)

(send-off age conj 3)

(pmap #(send age conj %) [ 1 2 3 4 5 6 ])
(await age)
(println @age)

;(agent-error age)
;(restart-agent age (agent (list 1 2)))


(def r (ref 1))

(println (deref r))
;(ref-set r 2)

(dosync (ref-set r 2))
(println (deref r))
(pmap #(dosync (alter r + %) (println @r)) [1 2 3 4 5 6])
