(ns advent-of-code-2018.day05-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day05 :refer [react shortest-polymer]]))

(deftest react-test
  (is (= (react "aA") ""))
  (is (= (react "abBA") ""))
  (is (= (react "abAB") "abAB"))
  (is (= (react "aabAAB") "aabAAB"))
  (is (= (react "dabAcCaCBAcCcaDA") "dabCBAcaDA")))

(deftest shortest-polymer-test
  (is (= (shortest-polymer "dabAcCaCBAcCcaDA") "daDA")))
