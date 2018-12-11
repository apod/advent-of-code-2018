(ns advent-of-code-2018.day11
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn hundreds [^long n]
  (mod (quot n 100) 10))

(defn power [^long grid-serial [^long x ^long y]]
  (let [rack-id (+ x 10)]
    (-> rack-id
        (* y)
        (+ grid-serial)
        (* rack-id)
        hundreds
        (- 5))))

(defn power-grid [grid-serial [width height :as size]]
  (into {} (for [x (range 1 (inc width))
                 y (range 1 (inc height))]
             [[x y] (power grid-serial [x y])])))

(defn integral-grid [grid [width height :as size]]
  (reduce (fn [acc [x y :as point]]
            (let [a (get acc point)
                  b (get acc [(dec x) y] 0)
                  c (get acc [x (dec y)] 0)
                  d (get acc [(dec x) (dec y)] 0)
                  res (- (+ a b c) d)]
              (assoc acc point (- (+ a b c) d))))
          grid
          (for [y (range 1 (inc height))
                x (range 1 (inc width))]
            [x y])))

(defn n-grid-sum [grid [x y :as point] n]
  (let [a (get grid [(+ x (dec n)) (+ y (dec n))] 0)
        b (get grid [(+ x (dec n)) (dec y)] 0)
        c (get grid [(dec x) (+ y (dec n))] 0)
        d (get grid [(dec x) (dec y)] 0)]
    (+ (- a b c) d)))

(defn largest-power-sum [integral [width height :as size] n]
  (let [width (- width n)
        height (- height n)]
    (if (zero? width)
      [[1 1] (n-grid-sum integral [1 1] n)]
      (apply max-key last
             (for [x (range 1 (inc width))
                   y (range 1 (inc height))]
               [[x y] (n-grid-sum integral [x y] n)])))))

(comment
  (let [input 1133
        grid-size [300 300]
        grid (power-grid input grid-size)
        integral (integral-grid grid grid-size)]
    ;; First star
    #_(largest-power-sum integral grid-size 3)
    ;; Second star
    (apply max-key last
           (pmap #(let [[point power] (largest-power-sum integral grid-size %)]
                    [(conj point %) power]) (range 1 301)))
    )
  )
