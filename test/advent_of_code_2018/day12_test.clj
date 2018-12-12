(ns advent-of-code-2018.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day12 :refer [sum plants-map
                                               generation-sequence]]))

(def example-rules
  {"...##" \#
   "..#.." \#
   ".#..." \#
   ".#.#." \#
   ".#.##" \#
   ".##.." \#
   ".####" \#
   "#.#.#" \#
   "#.###" \#
   "##.#." \#
   "##.##" \#
   "###.." \#
   "###.#" \#
   "####." \#})

(def example-input "#..#.#..##......###...###")

(deftest sum-test
  (is (= (sum (plants-map ".#....##....#####...#######....#.#..##." -3))
         325)))

(defn truncate [sim-map]
  (apply str (map #(get sim-map % \.) (range -3 (inc 35)))))

(deftest simulate-test
  (let [gen (take 21 (generation-sequence example-input example-rules))]
    (is (= (truncate (nth gen 1)) "...#...#....#.....#..#..#..#..........."))
    (is (= (truncate (nth gen 2)) "...##..##...##....#..#..#..##.........."))
    (is (= (truncate (nth gen 3)) "..#.#...#..#.#....#..#..#...#.........."))
    (is (= (truncate (nth gen 4)) "...#.#..#...#.#...#..#..##..##........."))
    (is (= (truncate (nth gen 5)) "....#...##...#.#..#..#...#...#........."))
    (is (= (truncate (nth gen 6)) "....##.#.#....#...#..##..##..##........"))
    (is (= (truncate (nth gen 7)) "...#..###.#...##..#...#...#...#........"))
    (is (= (truncate (nth gen 8)) "...#....##.#.#.#..##..##..##..##......."))
    (is (= (truncate (nth gen 9)) "...##..#..#####....#...#...#...#......."))
    (is (= (truncate (nth gen 10)) "..#.#..#...#.##....##..##..##..##......"))
    (is (= (truncate (nth gen 11)) "...#...##...#.#...#.#...#...#...#......"))
    (is (= (truncate (nth gen 12)) "...##.#.#....#.#...#.#..##..##..##....."))
    (is (= (truncate (nth gen 13)) "..#..###.#....#.#...#....#...#...#....."))
    (is (= (truncate (nth gen 14)) "..#....##.#....#.#..##...##..##..##...."))
    (is (= (truncate (nth gen 15)) "..##..#..#.#....#....#..#.#...#...#...."))
    (is (= (truncate (nth gen 16)) ".#.#..#...#.#...##...#...#.#..##..##..."))
    (is (= (truncate (nth gen 17)) "..#...##...#.#.#.#...##...#....#...#..."))
    (is (= (truncate (nth gen 18)) "..##.#.#....#####.#.#.#...##...##..##.."))
    (is (= (truncate (nth gen 19)) ".#..###.#..#.#.#######.#.#.#..#.#...#.."))
    (is (= (truncate (nth gen 20)) ".#....##....#####...#######....#.#..##."))
    (is (= (sum (nth gen 1)) 91))
    (is (= (sum (nth gen 20)) 325))))
