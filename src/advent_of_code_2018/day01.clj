(ns advent-of-code-2018.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-input [s]
  (read-string (str \[ s \])))

(def frequency (partial reduce +))

(defn frequency-reached-twice [xs]
  (loop [freq 0 previous #{}
         [h & t] xs]
    (cond
      (contains? previous freq) freq
      (nil? h) (recur freq previous xs)
      :else
      (recur (+ freq h) (conj previous freq) t))))

(comment
  (let [input (parse-input (slurp (io/resource "day01/input.txt")))]
    ;; First star
    #_(frequency input)
    ;; Second star
    (frequency-reached-twice input))
  )
