(ns advent-of-code-2018.day06
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-input [s]
  (map #(read-string (str \[ % \])) (str/split-lines s)))

(defn distance [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2))))

(defn boundaries [points]
  (let [xs (map first points)
        ys (map last points)]
    (vector (mapv #(apply min %) [xs ys])
            (mapv #(apply max %) [xs ys]))))

(defn boundary-points [[[min-x min-y] [max-x max-y] :as boundaries]]
  (set (mapcat (fn [x y]
                 [[x min-y] [x max-y]
                  [min-x y] [max-x y]])
               (range min-x (inc max-x))
               (range min-y (inc max-y)))))

(defn area-or-nil [current area-points]
  (let [distances (pmap (fn [point]
                          [point (distance current point)]) area-points)
        [[p1 d1] [p2 d2]] (sort-by last distances)]
    (when-not (= d1 d2)
      p1)))

(defn finite-area-points [area-points boundaries]
  (let [infinite (reduce (fn [acc current]
                           (if-let [area (area-or-nil current area-points)]
                             (conj acc area)
                             acc)) #{} (boundary-points boundaries))]
   (set/difference (set area-points) infinite)))

(defn largest-area [area-points]
  (let [[[min-x min-y] [max-x max-y] :as bounds] (boundaries area-points)
        finite (finite-area-points area-points bounds)
        all-points (for [x (range min-x (inc max-x))
                         y (range min-y (inc max-y))]
                     [x y])
        counts (reduce (fn [acc point]
                         (if-let [area (area-or-nil point area-points)]
                           (update acc area (fnil inc 0))
                           acc)) {} all-points)]
    (apply max (map #(get counts %) finite))))

(defn region-size [points max-distance]
  (let [[[min-x min-y] [max-x max-y] :as boundaries] (boundaries points)
        region-points (for [y (range min-y (inc max-y))
                            x (range min-x (inc max-x))
                            :let [d (reduce #(+ %1 (distance [x y] %2)) 0 points)]
                            :when (< d max-distance)]
                        [x y])]
    (count region-points)))

(comment
  (let [input (parse-input (slurp (io/resource "day06/input.txt")))]
    ;; First star
    #_(largest-area input)
    ;; Second star
    (region-size input 10000)
    )
  )
