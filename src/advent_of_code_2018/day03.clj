(ns advent-of-code-2018.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-claim [s]
  (->> (re-matches #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" s)
       (drop 1)
       (map read-string)
       (zipmap [:id :x :y :w :h])))

(defn parse-input [s]
  (map parse-claim (str/split-lines s)))

(defn claim-to-points [{:keys [id x y w h] :as claim}]
  (for [px (range w)
        py (range h)]
    {:id id :x (+ x px) :y (+ y py)}))

(defn overlap-points [claims]
  (let [points (mapcat claim-to-points claims)]
    (->> points
         (map (juxt :x :y))
         frequencies
         (remove #(= (val %) 1))
         count)))

(defn non-overlap-claim [claims]
  (let [points (mapcat claim-to-points claims)
        overlapping-ids (->> points
                             (group-by (juxt :x :y))
                             (filter #(> (count (val %)) 1))
                             vals
                             (mapcat #(map :id %))
                             set)]
    (some #(when-not (contains? overlapping-ids (:id %))
             (:id %)) claims)))

(comment
  (let [input (parse-input (slurp (io/resource "day03/input.txt")))]
    ;; First star
    #_(overlap-points input)
    ;; Second star
    (non-overlap-claim input))
  )
