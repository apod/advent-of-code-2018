(ns advent-of-code-2018.day14-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day14 :refer [scores-after
                                               recipes-before]]))

(deftest scores-after-test
  (is (= (scores-after 9   ) "5158916779"))
  (is (= (scores-after 5   ) "0124515891"))
  (is (= (scores-after 18  ) "9251071085"))
  (is (= (scores-after 2018) "5941429882")))

(deftest recipes-before-test
  (is (= (recipes-before "51589") 9))
  (is (= (recipes-before "01245") 5))
  (is (= (recipes-before "92510") 18))
  (is (= (recipes-before "59414") 2018)))
