(ns advent-of-code-2018.day08
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-input [s]
  (read-string (str \[ (str/trim s) \])))

(defn parse-node [[child-n metadata-n & other]]
  (if (zero? child-n)
    (let [[metadata other] (split-at metadata-n other)]
      [other {:children [] :metadata metadata}])
    (let [{:keys [other children]} (reduce (fn [{:keys [other] :as acc} _]
                                             (let [[other node] (parse-node other)]
                                               (-> acc
                                                   (update :children conj node)
                                                   (assoc :other other)))) {:children []
                                                                            :other other} (range child-n))
          [metadata other] (split-at metadata-n other)]
      [other {:children children :metadata metadata}])))

(defn parse-tree [tree]
  (last (parse-node tree)))

(defn sum-metadata [{:keys [children metadata]}]
  (let [sum (reduce + metadata)]
    (if (seq children)
      (apply + sum (map sum-metadata children))
      sum)))

(defn node-value [{:keys [children metadata] :as node}]
  (if (seq children)
    (let [meta-children (reduce (fn [acc m]
                                  (if-let [child (get children (dec m))]
                                    (conj acc child)
                                    acc)) [] metadata)]
      (reduce + (map node-value meta-children)))
    (sum-metadata node)))

(comment
  (let [input (parse-input (slurp (io/resource "day08/input.txt")))
        tree (parse-tree input)]
    ;; First star
    #_(sum-metadata tree)
    ;; Second star
    (node-value tree))
  )
