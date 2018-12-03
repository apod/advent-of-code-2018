(ns advent-of-code-2018.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day03 :refer [parse-input parse-claim
                                               claim-to-points
                                               overlap-points
                                               non-overlap-claim]]))

(deftest parse-claim-test
  (is (= (parse-claim "#1 @ 1,3: 4x4")    {:id 1 :x 1 :y 3 :w 4 :h 4}))
  (is (= (parse-claim "#23 @ 35,1: 14x4") {:id 23 :x 35 :y 1 :w 14 :h 4}))
  (is (= (parse-claim "#30 @ 1,1: 1x22")  {:id 30 :x 1 :y 1 :w 1 :h 22})))

(deftest claim-to-points-test
  (is (= (claim-to-points (parse-claim "#1 @ 1,3: 2x2"))
         [{:id 1, :x 1, :y 3} {:id 1, :x 1, :y 4}
          {:id 1, :x 2, :y 3} {:id 1, :x 2, :y 4}])))

(deftest overlap-points-test
  (is (= (overlap-points (parse-input "#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2"))
         4)))

(deftest non-overlap-claim-test
  (is (= (non-overlap-claim (parse-input "#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2"))
         3)))
