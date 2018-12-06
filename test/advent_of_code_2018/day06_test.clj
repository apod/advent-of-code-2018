(ns advent-of-code-2018.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day06 :refer [distance boundaries boundary-points
                                               area-or-nil finite-area-points
                                               largest-area region-size]]))

(deftest distance-test
  (is (= (distance [1 1] [0 5]) 5))
  (is (= (distance [8 3] [3 4]) 6)))

(def example [[1 1] [1 6]
              [8 3] [3 4]
              [5 5] [8 9]])

(deftest boundaries-test
  (is (= (boundaries [[0 0] [3 3] [5 5]]) [[0 0] [5 5]]))
  (is (= (boundaries example) [[1 1] [8 9]])))

(deftest boundary-points-test
  (is (= (boundary-points (boundaries [[0 0] [3 3] [5 5]]))
         #{[0 0] [1 0] [2 0] [3 0] [4 0] [5 0]
           [0 1]                         [5 1]
           [0 2]                         [5 2]
           [0 3]                         [5 3]
           [0 4]                         [5 4]
           [0 5] [1 5] [2 5] [3 5] [4 5] [5 5]}))
  (is (= (boundary-points (boundaries example))
         #{[1 1] [2 1] [3 1] [4 1] [5 1] [6 1] [7 1] [8 1]
           [1 2]                                     [8 2]
           [1 3]                                     [8 3]
           [1 4]                                     [8 4]
           [1 5]                                     [8 5]
           [1 6]                                     [8 6]
           [1 7]                                     [8 7]
           [1 8]                                     [8 8]
           [1 9] [2 9] [3 9] [4 9] [5 9] [6 9] [7 9] [8 9]})))

(deftest area-or-nil-test
  (is (= (area-or-nil [0 0] example) [1 1]))
  (is (= (area-or-nil [0 4] example) nil))
  (is (= (area-or-nil [9 9] example) [8 9]))
  (is (= (area-or-nil [9 6] example) nil)))

(deftest finite-area-points-test
  (is (= (finite-area-points example (boundaries example)) #{[3 4] [5 5]})))

(deftest largest-area-test
  (is (= (largest-area example) 17)))

(deftest region-size-test
  (is (= (region-size example 32) 16)))
