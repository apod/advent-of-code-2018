(ns advent-of-code-2018.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day01 :refer [frequency-reached-twice frequency]]))

(deftest frequency-given-examples
  (is (= (frequency [+1 +1 +1]) 3))
  (is (= (frequency [+1 +1 -2]) 0))
  (is (= (frequency [-1 -2 -3]) -6)))

(deftest frequency-reached-twice-given-examples
  (is (= (frequency-reached-twice [+1, -1]) 0))
  (is (= (frequency-reached-twice [+3, +3, +4, -2, -4]) 10))
  (is (= (frequency-reached-twice [-6, +3, +8, +5, -6]) 5))
  (is (= (frequency-reached-twice [+7, +7, -2, -7, -4]) 14)))
