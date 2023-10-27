(ns spimp-lectures.lec5
  (:require [clojure.core.async :as a]))

(def f (future (Thread/sleep 3000) (+ 1 2)))
(println "f = " f)
(println "f = " @f)
(println "f = " (deref f))                                  ; Аналог get из джавы, блокирует поток, до завершения фьючера


(defn long-sum [a b] (Thread/sleep 2000) (+ a b))
(time (let [x (future (long-sum 1 2)) y (future (long-sum 3 4)) z (future (long-sum 5 6))] (print (+ @x @y @z))))

(def p1 (p/create
          (fn [resolve fail] (long-sum 1 2))))
(println "p1 = " p1)

(def p1 (p/create (fn [resolve fail]
                    (resolve (long-sum 1 2)))))
(println "p1 = " @p1)

(p/then p1 #(println "callback: " %))


(def p1 (p/create (fn [resolve fail]
                    (resolve (long-sum 1 2))
                    )))
(def p2 (p/then p1 #(println "p1= " %)))

(println @p1)
(println @p2)




(defn p-long-comp [x y] (p/create
                          (fn [resolve fail]
                            (future
                              (resolve
                                (long-sum x y))))))
(def p1 (p-long-comp 1 2))
(def p2 (p-long-comp 3 4))
(def psum (p/mapcat
            (fn [result1]
              (p/map
                (fn [result2]
                  (+ result1 result2)) p2)) p1))
(println "res=" @psum)

(def psum (p/let [r1 p1 r2 p2]
                 (+ r1 r2)
                 ))
(time (println "res = " @psum))

(def queue (a/chan 10))

(defn producer [x]
  (when (> x 0)
    (println "put" x)
    (a/put! queue x
            (fn [_]
              (producer (dec x))
              )
            )
    )
  )

(defn consumer []
  (a/take! queue (fn [r]
                   (println "taken " r)
                   (when true (consumer))
                   )
           )
  )

(producer 19)
(consumer)
