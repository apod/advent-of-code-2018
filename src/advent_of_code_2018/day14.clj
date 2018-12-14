(ns advent-of-code-2018.day14
  (:require [clojure.string :as str]))

(defn digits [n]
  (map #(read-string (str %)) (str n)))

(defn scores-after [input]
  (loop [pos1 0
         pos2 1
         coll [3 7]]
    (if (>= (count coll) (+ input 10))
      (subs (str/join coll) input (+ input 10))
      (let [r1 (nth coll pos1)
            r2 (nth coll pos2)
            coll (into coll (digits (+ r1 r2)))]
        (recur
         (rem (+ pos1 1 r1) (count coll))
         (rem (+ pos2 1 r2) (count coll))
         coll)))))

(defn recipes-before [input]
  (let [input (digits input)
        n (count input)
        conj-fixed (fn [coll x]
                     (if (= (count coll) n)
                       (conj (vec (drop 1 coll)) x)
                       (conj coll x)))]
    (loop [pos1 0 pos2 1
           last-n [3 7] coll [3 7]]
      (let [r1 (nth coll pos1)
            r2 (nth coll pos2)
            new-recipes (digits (+ r1 r2))]
        (cond
          (= last-n input)
          (count (drop-last n coll))
          (= (conj-fixed last-n (first new-recipes)) input)
          (count (drop-last (dec n) coll))
          :else
          (let [coll (into coll new-recipes)
                cnt (count coll)]
            (recur
             (rem (+ pos1 1 r1) cnt)
             (rem (+ pos2 1 r2) cnt)
             (reduce conj-fixed last-n new-recipes)
             coll)))))))

(comment
  (let [input "074501"]
    ;; First star
    #_(scores-after (read-string (str "10r" input)))
    ;; Second star
    (recipes-before input)
    )


  )

