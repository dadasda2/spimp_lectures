(ns spimp-lectures.lec2)

(defn foo

  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(println "Hello, World!")

(+ 1 2)
(+ 1 2 3)
(+)

(println (first [1 2 3 4 5]))                               ;Берём первый элемент
(println (rest [1 2 3 4 5]))                                ;Удаляем первый элемент
(println (take 3 [1 2 3 4 5]))                              ;Берём несколько первых
(println (drop 2 [1 2 3 4 5]))                              ;Удалить несколько первых
(println (if (> 4 5) "Yes" "No"))


(+ Long/MAX_VALUE 1)                                        ;будет ошибка
(+ `Long/MAX_VALUE 1)                                       ;будет автопромоутинг

(+ 3/5 8/9)                                                 ;рациональные дроби

nil                                                         ;пустой список
(count nil)                                                 ;0
(if nil "yes" "no")                                         ; nil == false


(fn [a b] :err)
(keyword "fff")

(str "fff" "ddd")

;константы
(def a 4)
(def ++ 4)
(def my? 4)

(list 1 2 3 4)
`(1 2 3 4)
(def a `(1 2 3 4))
(def a `(1 2 3 4 `(5 6 7)))


(conj (list 1 2 3 4) 5)                                     ;добавление в список
(conj (list 1 2 3 4) 5 5 6)                                 ;(6 5 5 1 2 3 4)

(pop (list 1 2 3 4))                                        ;Удаляет первый элемент, т.е. возвращает список без первого элемента.
(peek (list 1 2 3 4))                                       ;Возвращает первый элемент

(vec `(4 5 6))                                              ;[4 5 6]
(conj [1 2 3 4] 5)                                          ;[1 2 3 4 5]


(get [1 2 3 4 5] 2)                                         ;3

([1 2 3 4 5] 2)                                             ;3

(let [a [4 5 6] b 2]
  (a 2)                                                     ;6
  (assoc a 2 10))                                           ;изменит значение по индексу 2
{:a 1 :b "ddd"}


{:a 5 :inn {:bb [4 5 6]}}
({:a 4 :b "ddd"} :b)                                        ;ddd
(get-in {:a 5 :inn {:bb [4 5 6]}} [:inn :bb])               ;Запрос в глубь ассоциативного массива, вернет [4 5 6].

(into [] (list 4 5 6))                                      ;Сделает из списка вектор, можно и наоборот
(into (list) [1 2 3])                                       ;(3 2 1)
;Можно объединять списки
(into (list 1 2 3 4) [100 101 102])                         ;(102 101 100 1 2 3 4)
(into [] {:a 1 :b 2})                                       ;получим [[:a 1] [:b 2]]

(cons 1 nil)                                                ;получим список из одного элемента
(cons 1 (cons 2 (cons 3 nil)))
(def a (cons 1 (cons 2 (cons 3 nil))))

(first a)
(rest a)
(take 2 [2 4 5])
(drop 2 [1 2 3 4 5])

(repeat 5)                                                  ;бесконечно возвращает 5
(take 3 (cons 2 (repeat 5)))                                ;(2 5 5)

(cond (> 4 5) "one"
      (< 4 5) "two"
      :else "ddd")

(def value 1)
(condp = value
  1 "one"
  2 "two"
  3 "three")


(let [v [4 5 2] size (count v)]
  (loop [i 0]
    (if (< i size)
      (do
        (println i (v i))
        (recur (inc i))
        ))))

(let [v [4 5 2] size (count v)]
  (loop [i 0]
    (when (< i size)
      (println i (v i))
      (recur (inc i))
      )))

(doseq [x [1 2 3]] (println x))
(for [x [1 2 3]] (println x))
(for [x [1 2 3] y [:a :b :c :d]] [x y])

(defn nums [start end]
  (if (>= start end)
    [end]
    (cons start (lazy-seq (nums (inc start) end)))))

(defn nums [start] (cons start (lazy-seq (nums (inc start)))))

(take 100 (nums 10))

(defn removo [val l]
  (when-not (empty? l)
    (if (= val (first l))
      (recur val (rest l))
      (cons (first l) (lazy-seq (removo val (rest l))))
      )
    )
  )


(defn removo [val [head & tail]]
  (when head
    (if (val head)
      (cons head (lazy-seq (removo val tail)))
      (recur val tail))))

(println (removo pos? [1 -2 -3 -4 3 4 3 4]))

(filter (fn [a] (> a 3)) [1 2 3 4 5 6 7 8 9 10])

(filter #(> % 3) [1 2 3 4 5 6 7 8 10 9])

(map inc [1 2 3 4])

(println (reduce conj [] [1 2 3 4]))
[1 2 3 4]
(println (reduce conj () [1 2 3 4]))
(4 3 2 1)
(println (reductions conj () [1 2 3 4]))

(println (mapcat (fn [i] (range 0 i)) [1 2 3 4]))           ;(0 0 1 0 1 2 0 1 2 3)