(ns advent-of-code-2018.day09-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day09 :refer [high-score]]))

(deftest high-score-test
  (is (= (high-score 9 25) 32))
  (is (= (high-score 10 1618) 8317))
  (is (= (high-score 13 7999) 146373))
  (is (= (high-score 17 1104) 2764))
  (is (= (high-score 21 6111) 54718))
  (is (= (high-score 30 5807) 37305)))
