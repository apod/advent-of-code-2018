(ns advent-of-code-2018.day11-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day11 :refer [hundreds power
                                               power-grid integral-grid
                                               largest-power-sum]]))

(deftest hundreds-test
  (is (= (hundreds 12345) 3))
  (is (= (hundreds 949) 9))
  (is (= (hundreds 10) 0)))

(deftest power-test
  (is (= (power 8 [3 5]) 4))
  (is (= (power 57 [122 79]) -5))
  (is (= (power 39 [217 196]) 0))
  (is (= (power 71 [101 153]) 4)))

(deftest integral-grid-test
  (is (= (integral-grid {[1 1] 5 [2 1] 2 [3 1] 3
                         [1 2] 1 [2 2] 5 [3 2] 4} [3 2])
         {[1 1] 5 [2 1] 7 [3 1] 10 [1 2] 6 [2 2] 13 [3 2] 20})))

(deftest largest-power-sum-test
  (let [size [300 300]
        n 3]
    (is (= (largest-power-sum (integral-grid (power-grid 18 size) size) size n) [[33 45] 29]))
    (is (= (largest-power-sum (integral-grid (power-grid 42 size) size) size n) [[21 61] 30]))))
