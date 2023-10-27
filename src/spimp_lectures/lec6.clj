(ns spimp-lectures.lec6
  (:require [clojure.core.async :as a]))

(def queue (a/chan 10))
(a/go
  (doseq [x (range 0 10)]
    (let [value (str "x" x)]
      (println "put" value)
      (a/>! queue value)
      )
    )
  )

(a/go (while true
        (println "taken "
                 (a/<! queue))))

(def queue (a/chan 10))
(a/go (doseq [x (range 0 10)]
        (let [value (str "x" x)]
          (println (str "put " value))
          (a/>! queue value)))
      (a/close! queue))

(println "r= " (a/<!!
                 (a/go-loop []
                   (when-let [r (a/<! queue)]
                     (println "taken " r)
                     (recur))
                   )))

(def c1 (a/chan 10))
(def c2 (a/chan 10))

(a/go (doseq [x (range 0 10)]
        (let [value (str "x" x)]
          (println (str "put " value))
          (a/>! c1 value)))
      (a/close! c1))

(a/go (doseq [x (range 0 10)]
        (let [value (str "y" x)]
          (println (str "put " value))
          (a/>! c2 value)))
      (a/close! c2))

;(println "r= " (a/<!! (a/go-loop []
;                        (when-let [[r c] (a/alts! [c1 c2])]
;                          (println "taken " r c)
;                          (recur))
;                        )))

(println "r= " (a/<!! (a/go-loop []
                        (let [[r c]
                              (a/alts! [c1 c2])]
                          (println "taken " r)
                          (when r (recur)))
                        )))

(def c1 (a/chan 10))
(def c2 (a/chan 10))
(def c (a/merge [c1 c2]))

(a/go (doseq [x (range 0 10)]
        (let [value (str "x" x)]
          (println (str "put " value))
          (a/>! c1 value)))
      (a/close! c1))

(a/go (doseq [x (range 0 10)]
        (let [value (str "y" x)]
          (println (str "put " value))
          (a/>! c2 value)))
      (a/close! c2))

(println "r= " (a/<!! (a/go-loop []
                        (when-let [r (a/<! c)]
                          (println "taken " r)
                          (recur))
                        )))

(defn vec-to-chan [vec]
  (let [c (a/chan)]
    (a/go
      (doseq [x vec]
        (println (str "put " x))
        (a/>! c x))
      (a/close! c))
    c))

(def c1 (vec-to-chan (range 0 10)))

(a/go-loop []
  (when-let [r (a/<! c1)]
    (println (str "taken " r))
    (recur))
  )

(defn vec-to-chan [vec]
  (let [c (a/chan)]
    (a/go
      (doseq [x vec]
        (println (str "put " x))
        (a/>! c x))
      (a/close! c))
    c))

(defn chan-to-vec [c]
  (a/<!! (a/go-loop [v []]
           (if-let
             [r (a/<! c)]
             (do
               (println "v= " v)
               (recur (conj v r)))
             v)
           )))

(def c1 (vec-to-chan (range 0 10)))
(println "r=" (chan-to-vec c1))


(defn vec-to-chan [vec]
  (let [c (a/chan)] (a/go (doseq [x vec]
                            (a/>! c x))
                          (a/close! c))
                    c))

(defn chan-to-vec [c]
  (a/<!! (a/go-loop [v []]
           (if-let [r (a/<! c)]
             (do
               (recur (conj v r)))
             v)
           )))

(def c1 (vec-to-chan (range 0 10)))
(def c2 (vec-to-chan (range 10 20)))

(defn zip-chan [c1 c2]
  (let [c (a/chan)]
    (a/go-loop []
      (let [r1 (a/<! c1)
            r2 (a/<! c2)]
        (if (and (some? r1) (some? r2))
          (do
            (a/>! c [r1 r2])
            (println (str "r1=" r1 " r2=" r2))
            (recur))
          (a/close! c)
          )))
    c)
  )
(println (chan-to-vec (zip-chan c1 c2)))
