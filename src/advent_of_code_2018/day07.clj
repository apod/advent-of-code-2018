(ns advent-of-code-2018.day07
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn dependency-graph []
  {:nodes #{}
   :dependencies {}})

(defn nodes [dg]
  (:nodes dg))

(defn add-node [dg node]
  (update dg :nodes conj node))

(defn remove-node [dg node]
  (let [deps (dissoc (:dependencies dg) node)
        updated-deps (reduce-kv (fn [m k v]
                                  (let [new-value (disj v node)]
                                    (if (seq new-value)
                                      (assoc m k (disj v node))
                                      (dissoc m k)))) {} deps)]
    (-> dg
        (update :nodes disj node)
        (assoc :dependencies updated-deps))))

(defn add-dependency [dg node dependency]
  (-> dg
      (add-node node)
      (add-node dependency)
      (update :dependencies update node (fnil conj #{}) dependency)))

(defn independent [dg]
  (set/difference (nodes dg) (keys (:dependencies dg))))

(defn dependency-graph-from-steps [steps]
  (reduce (fn [dg [a b]]
            (add-dependency dg b a))
          (dependency-graph) steps))

(defn parse-step [s]
  [(nth s 5) (nth s 36)])

(defn parse-input [s]
  (dependency-graph-from-steps (map parse-step (str/split-lines s))))

(defn steps-order [dg]
  (loop [order []
         dg dg]
    (if (seq (nodes dg))
      (let [inodes (first (sort (independent dg)))]
        (recur (conj order inodes)
               (remove-node dg inodes)))
      (apply str order))))

(defn step-idx [c]
  (inc (- (int c) (int \A))))

(defn simulate [dg n-workers step-extra-seconds]
  (loop [dg dg
         workers (zipmap (range n-workers) (repeat {:node nil :secs 0}))
         total -1
         res ""]
    (if (seq (nodes dg))
      (let [free (filter (fn [[i w]] (= (:secs w) 0)) workers)
            nodes-done (remove nil? (map (fn [[i w]] (:node w)) free))
            dg (reduce remove-node dg nodes-done)
            inodes (independent dg)
            worked-now (set (remove nil? (map :node (vals workers))))
            nodes-to-work (sort (set/difference inodes worked-now))
            workers (reduce-kv (fn [acc k w]
                                 (if (= (:secs w) 0)
                                   (assoc acc k {:node nil :secs 0})
                                   (assoc acc k
                                          (update w :secs dec))))
                               {}
                               workers)
            new-jobs (map (fn [[i w] node]
                            [i {:node node
                                :secs (+ step-extra-seconds (step-idx node) -1)}]) free nodes-to-work)
            workers (reduce (fn [acc [i w]]
                              (assoc acc i w)) workers new-jobs)]
        (recur dg workers (inc total)
               (apply str res nodes-done)))
      total)))

(comment
  (let [input (parse-input (slurp (io/resource "day07/input.txt")))]
    ;; First star
    #_(steps-order input)
    ;; Second star
    (simulate input 5 60))
  )
