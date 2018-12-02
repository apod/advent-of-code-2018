(ns advent-of-code-2018.day02-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day02 :refer [checksum differ-by-one?
                                               correct-boxes common-letters]]))

(deftest checksum-test
  (is (= (checksum ["abcdef" "bababc" "abbcde"
                    "abcccd" "aabcdd" "abcdee"
                    "ababab"]) 12)))

(deftest differ-by-one?-test
  (is (differ-by-one? "abcdef" "abddef"))
  (is (not (differ-by-one? "abcdef" "abdddf"))))

(deftest correct-boxes-test
  (is (= (correct-boxes ["abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"])
         ["fghij" "fguij"])))

(deftest common-letters-test
  (is (= (common-letters "fghij" "fguij") "fgij")))
