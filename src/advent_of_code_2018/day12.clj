(ns advent-of-code-2018.day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn plants-map [input start]
  (into (sorted-map)
        (map-indexed #(vector (+ %1 start) %2) input)))

(defn sum [plants-map]
  (reduce-kv (fn [acc k v]
               (if (= v \#)
                 (+ acc k)
                 acc)) 0 plants-map))

;; Not happy with the wrapping maybe i should only
;; keep the plant in the initial data structure
(defn wrap-with-dots [plants-map]
  (let [sorted (sort-by key plants-map)
        min (ffirst sorted)
        max (first (last sorted))]
    (assoc plants-map
           (dec min) \.
           (- min 2) \.
           (inc max) \.
           (+ max 2) \.)))

(defn next-generation [rules]
  (fn [current]
    (reduce-kv (fn [acc k v]
                 (let [pattern-range (range (- k 2) (+ k 3))
                       pattern (reduce #(str %1 (get current %2 \.)) "" pattern-range)
                       new-v (get rules pattern \.)]
                   (assoc acc k new-v))) (sorted-map) (wrap-with-dots current))))

(defn generation-sequence [input rules]
  (iterate (next-generation rules) (plants-map input 0)))

(defn stable-growth [input rules]
  (let [check-n-sums 4]
    (reduce (fn [{:keys [previous] :as acc} gen]
              (let [diff (when (= check-n-sums (count previous))
                           (map (fn [[a b]] (- b a)) (partition 2 1 previous)))]
                (if (and diff (apply = diff))
                  (reduced (-> acc
                               (update :generation-n dec)
                               (assoc :growth (first diff))))
                  (let [s (sum gen)]
                    (-> acc
                        (update :generation-n inc)
                        (assoc  :generation-sum s)
                        (update :previous (fn [previous x]
                                            (if (= (count previous) check-n-sums)
                                              (conj (vec (drop 1 previous)) x)
                                              (conj previous x))) s))))))
            {:generation-n 0
             :previous []} (generation-sequence input rules))))

(comment
  (let [input "#..#####.#.#.##....####..##.#.#.##.##.#####..####.#.##.....#..#.#.#...###..#..###.##.#..##.#.#.....#"
        rules {".#.##" \# ".###." \# "#..#." \. "...##" \. "#.##." \# "....#" \. "..##." \# ".##.." \. "##..#" \.
               ".#..#" \# "#.#.#" \. "#...." \. ".####" \# ".##.#" \. "..#.." \# "####." \# "#.#.." \. ".#..." \.
               "###.#" \. "..###" \. "#..##" \# "...#." \# "....." \. "###.." \# "#...#" \. "..#.#" \# "##..." \#
               "##.##" \. "##.#." \. "#####" \. ".#.#." \# "#.###" \#}]
    ;; First star
    #_(sum (nth (generation-sequence input rules) 20))
    ;; Second star
    (let [goal-generation 50000000000
          {:keys [generation-n growth
                  generation-sum]} (stable-growth input rules)]
      (+ generation-sum (* (- goal-generation generation-n) growth)))
    )
;;=> 1050000000480


  )
