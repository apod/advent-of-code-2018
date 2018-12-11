(ns advent-of-code-2018.day07-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day07 :refer [steps-order
                                               dependency-graph-from-steps
                                               simulate]]))

(def example-dg (dependency-graph-from-steps [[\C \A]
                                              [\C \F]
                                              [\A \B]
                                              [\A \D]
                                              [\B \E]
                                              [\D \E]
                                              [\F \E]]))
(deftest steps-order-test
  (is (= (steps-order example-dg) "CABDFE")))

(deftest simulate-test
  (is (= (simulate example-dg 2 0) 15)))
