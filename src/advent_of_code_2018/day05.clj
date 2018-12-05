(ns advent-of-code-2018.day05
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-input [s]
  (str/trim-newline s))

(defn same-letter? [a b]
  (and a b (= (Math/abs (- (int a) (int b))) 32)))

(defn react [s]
  (reduce (fn [acc c]
            (let [last-idx (dec (count acc))]
              (if (same-letter? c (get acc last-idx))
                (subs acc 0 last-idx)
                (str acc c))))
          "" s))

(defn shortest-polymer [s]
(let [units (filter #(Character/isLowerCase %) (set s))]
  (->> units
       (pmap (fn [c]
               (let [regex (re-pattern (str \[ c (Character/toUpperCase c) \]))
                     s (str/replace s regex "")]
                 (react s))))
       (apply min-key count))))

(comment
  (let [input (parse-input (slurp (io/resource "day05/input.txt")))]
    ;; First star
    #_(count (react input))
    ;; Second star
    (count (shortest-polymer input)))
  )
