(ns spimp-lectures.lecc8-test
  (:require [clojure.test :refer :all]
            [spimp-lectures.lecc8 :refer :all]))

(deftest a-test
  (testing "number sum"
    (is (= 3 (sum 1 2)))
    (is (= 1 (sum 1 0)))
    (is (= 0 (sum 0 0)))
    (is (= 4 (sum 2 2)))
    (is (= -2 (sum -4 2)))
    )
  )

(deftest test-sum-with-are
  (testing "number sum by given data"
    (are [result arg1 arg2] (= result (sum arg1 arg2))
                            3 1 2
                            0 0 0
                            4 4 2
                            -10 -2 -8)
    )
  )


(deftest get-cpus-test
  (testing "testing parsing CPU from top output"
    (with-redefs [me.raynes.conch/execute (fn [& args]
                                            (is (= ["top" "-b" "-n" "1" {:seq true}] args))
                                            ["top - 19:54:15 up 56 min,  1 user,  load average: 0,73, 0,44, 0,48"
                                             "Tasks: 219 total,   1 running, 218 sleeping,   0 stopped,   0 zombie"
                                             "%Cpu(s):  1,8 us,  3,6 sy,  0,0 ni, 94,6 id,  0,0 wa,  0,0 hi,  0,0 si,  0,0 st"
                                             "MiB Mem :   3911,0 total,    133,0 free,   3245,8 used,    532,2 buff/cache"
                                             "MiB Swap:   2140,0 total,   1046,0 free,   1094,0 used.    368,7 avail Mem "]
                                            )]
      (is (= '("1,8" "3,6" "0,0" "94,6" "0,0" "0,0" "0,0" "0,0") (get-cpus))))))
