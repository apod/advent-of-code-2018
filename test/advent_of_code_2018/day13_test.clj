(ns advent-of-code-2018.day13-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [advent-of-code-2018.day13 :refer [parse-input first-crash last-cart]]))

(def example (parse-input (slurp (io/resource "day13/example.txt"))))
(def example2 (parse-input (slurp (io/resource "day13/example2.txt"))))

(deftest first-crash-test
  (is (= (first-crash example) [7 3])))

(deftest last-cart-test
  (is (= (last-cart example2) [6 4])))
