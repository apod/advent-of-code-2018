(ns advent-of-code-2018.day08-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day08 :refer [parse-node parse-tree
                                               sum-metadata node-value]]))

(deftest parse-node-test
  (let [[_ node1] (parse-node [0 3 10 11 12])
        [_ node2] (parse-node [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2])]
    (is (= (:metadata node1) [10 11 12]))
    (is (= (:children node1) []))
    (is (= (count (:children node2)) 2))
    (is (= (:metadata node2) [1 1 2]))))

(deftest sum-metadata-test
  (= (sum-metadata (parse-tree [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2]))
     138))

(deftest node-value-test
  (= (node-value (parse-tree [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2]))
     66))
