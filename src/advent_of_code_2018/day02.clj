(ns advent-of-code-2018.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-input [s]
  (str/split-lines s))

(defn checksum [ids]
  (->> ids
       (reduce (fn [[twos threes] id]
                 (let [freqs (set (vals (frequencies id)))]
                   [(cond-> twos   (contains? freqs 2) inc)
                    (cond-> threes (contains? freqs 3) inc)]))
               [0 0])
       (reduce *)))

(defn differ-by-one? [a b]
  (= 1 (reduce + (map #(if (= %1 %2) 0 1) a b))))

(defn correct-boxes [ids]
  (loop [[id & other] ids]
    (if-let [res (some #(when (differ-by-one? id %)
                          [id %]) other)]
      res
      (recur other))))

(defn common-letters [a b]
  (reduce (fn [acc [c1 c2]]
            (cond-> acc
              (= c1 c2) (str c1))) "" (map vector a b)))

(comment
  (let [input (parse-input (slurp (io/resource "day02/input.txt")))]
    ;; First star
    #_(checksum input)
    ;; Second star
    (apply common-letters (correct-boxes input)))
  )
