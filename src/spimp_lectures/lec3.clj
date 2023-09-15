(ns spimp-lectures.lec3)

(println (mapcat (fn [i] (range 0 i)) [1 2 3 4]))
(reduce + (filter #(> % 2) (map count ["aesdrtjykuh" "wasg" "sadf" "aa" "d"])))
(->> ["aesdrtjykuh" "wasg" "sadf" "aa" "d"] (map count) (filter #(> % 2)) (reduce +))
(-> 1 (+ 2) (* 3))

(println (for [str ["aesdrtjykuh" "wasg" "sadf" "aa" "d"]
               :let [l (count str)]
               :when (> l 2)]
           l))

(for [str ["aaaeeeesdrtjykuh" "wasg" "sadf" "aa" "d"]
      c str
      :let [l (count (filter #(= % c) str))]
      :when (> l 1)]
  [c str])

(println (for [str ["aaaeeeesdrtjykuh" "wasg" "sadf" "aa" "d"]
               c (set str)
               :let [l (count (filter #(= % c) str))]
               :when (> l 1)]
           [c str]))

(println (for [str ["aaaeeeesdrtjykuh" "wasg" "sadf" "aa" "d"]
               c (distinct str)
               :let [l (count (filter #(= % c) str))]
               :when (> l 1)]
           [c str l]))

(defn sum "Calculates sum of two params " [a b] (+ a b))
(doc sum)

(defn sum "Calculates sum of two params " {:private true :author "me"} [a b] (+ a b))

(meta (var sum))

(defn sum "Calculates sum of two params " {:private true :author "Me"} [a b] {:pre [(number? a) (number? b)]} (+ a b))
(println (sum "3" "3"))                                     ; ошибка

(defn sum "Calculates sum of two params " {:private true :author "Me"} [a b] {:pre [(number? a) (number? b) (pos? a) (pos? b)]} (+ a b))

(defn sum "Calculates sum of two params" {:private true :author "Me"} [a b] {:pre [(number? a) (number? b)] :post [(number? %)]} (+ a b))

(defn sum "Calculates sum of two params" {:private true :author "Me"} [a b] {:post [(number? %)]} (str a b)) ;Абсурдный пример конкатинации строк, который должен вернуть число

(defn sum "Calculates sum of two params " {:private true :author "Me"}
  [^long a ^long b]
  {:pre  [(number? a) (number? b)]
   :post [(number? %)]} (+ a b))

(defn foo [str] (.indexOf str "abc"))                       ;Функции из JAVA
(defn foo [^String str] (.indexOf str "abcd"))

(defn sum
  ([] +)
  ([a] (+ a))
  ([a b] (+ a b))
  ([a b c] (+ a b c))
  )
(partial sum 1 2)

(defn sum
  ([] +)
  ([a] (+ a))
  ([a b] (+ a b))
  ([a b c] (+ a b c))
  ([a b c & rest] (reduce + (+ a b c) rest)))

(defn sum
  ([] +)
  ([a] (+ a))
  ([a b] (+ a b))
  ([a b c] (+ a b c))
  ([a b c & rest] (apply + a b c rest)))

(defn sum
  ([& rest] (apply + rest))
  )

(def v [1 2 3 4 5 6 7])
(let [[a & rest] v] (println "a=" a "rest=" rest))
(let [[a _ b] v] (println "a=" a "b=" b))

(def john {:name "John" :surname "Smith"})
(let [{:keys [name surname]} john] [name surname])

(def john {:name "John" :surname "Smith" :address {:city "Moscow" :street "Vadkovsky"}})
(let [{:keys [name surname] {:keys [city street]} :address} john] [name surname city street])

(let [{:keys [name surname age] :or {age 12}} john] [name surname age])
