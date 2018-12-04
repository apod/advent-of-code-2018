(ns advent-of-code-2018.day04-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [advent-of-code-2018.day04 :refer [parse-entry parse-input
                                               sleepiest-guard sleepiest-minute]]))

(deftest parse-entry-test
  (is (= (drop 1 (parse-entry "[1518-11-01 00:00] Guard #10 begins shift"))
         [:guard 10]))
  (is (= (drop 1 (parse-entry "[1518-11-01 00:05] falls asleep"))
         [:asleep]))
  (is (= (drop 1 (parse-entry "[1518-11-01 00:25] wakes up"))
         [:awake])))

(def example-input (slurp (io/resource "day04/example-input.txt")))

(deftest sleepiest-guard-test
  (let [example (parse-input example-input)]
    (is (= (sleepiest-guard example) 10))))

(deftest sleepiest-minute-test
  (let [example (parse-input example-input)]
    (is (= (sleepiest-minute (get example 10)) [24 2]))))
