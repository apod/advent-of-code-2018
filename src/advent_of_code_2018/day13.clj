(ns advent-of-code-2018.day13
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn cart [x y dir]
  {:x x :y y
   :last+ :right
   :dir (case dir
          \^ :up \v :down
          \> :right \< :left)})

(defn parse-stage [s]
  (reduce
   (fn [acc [y row]]
     (let [carts (keep-indexed #(when (contains? #{\^ \< \> \v} %2)
                                  (cart %1 y %2)) row)
           row (mapv #(condp contains? %
                        #{\^ \v} \|
                        #{\< \>} \-
                        %) row)]
       (-> acc
           (update :stage conj row)
           (update :carts into carts))))
   {:carts []
    :stage []} (map-indexed vector s)))

(defn parse-input [s]
  (parse-stage (str/split-lines s)))

(defn curve-turn [dir nxt]
  (case [dir nxt]
    [:right \\] :down
    [:right \/] :up
    [:left  \\] :up
    [:left  \/] :down
    [:down  \/] :left
    [:down  \\] :right
    [:up    \/] :right
    [:up    \\] :left
    dir))

(defn intersection-turn [dir last+]
  (let [last+ (case last+
                :right :left
                :left :straight
                :straight :right)]
    (case [dir last+]
      [:up    :right] [:right last+]
      [:up    :left ] [:left last+]
      [:down  :right] [:left last+]
      [:down  :left ] [:right last+]
      [:right :right] [:down last+]
      [:right :left ] [:up last+]
      [:left  :right] [:up last+]
      [:left  :left ] [:down last+]
      [dir last+])))

(defn advance [{:keys [x y dir last+] :as cart} stage]
  (let [[x y] (case dir
                :left  [(dec x) y]
                :right [(inc x) y]
                :up    [x (dec y)]
                :down  [x (inc y)])
        nxt (get-in stage [y x])
        dir (curve-turn dir nxt)
        [dir last+] (if (= nxt \+)
                      (intersection-turn dir last+)
                      [dir last+])]
    (-> cart
        (assoc :x x :y y
               :dir dir
               :last+ last+))))

(defn crash? [carts]
  (let [freqs (frequencies (map (juxt :x :y) carts))]
    (get (set/map-invert freqs) 2 false)))

(defn all-crashes [carts]
  (let [freqs (frequencies (map (juxt :x :y) carts))]
    (set (keep #(when (>= (val %) 2)
                  (key %)) freqs))))

(defn first-crash [{:keys [carts stage]}]
  (some crash? (iterate (fn [carts] (mapv #(advance % stage) carts)) carts)))

(defn remove-crashed [carts]
  (let [crashed (all-crashes carts)]
    (remove (fn [{:keys [x y]}]
              (contains? crashed [x y])) carts)))

(defn last-cart [{:keys [carts stage]}]
  (some #(when (<= (count %) 1)
           ((juxt :x :y) (first %)))
        (iterate (fn [carts]
                   (loop [[cart & carts] (sort-by (juxt :y :x) carts)
                          res []]
                     (if (nil? cart)
                       (remove-crashed res)
                       (let [cart (advance cart stage)
                             crashed (some (fn [{:keys [x y]}]
                                             (when (= [x y] [(:x cart) (:y cart)])
                                               true)) carts)]
                         (if crashed
                           (recur (remove (fn [{:keys [x y]}]
                                            (= [x y] [(:x cart) (:y cart)])) carts)
                                  res)
                           (recur carts
                                  (conj res cart))))))) carts)))
(comment
  (let [input (parse-input (slurp (io/resource "day13/input.txt")))]
    ;; First star
    #_(first-crash input)
    ;; Second star
    (last-cart input)
    )
  )
