(ns advent-of-code-2018.day04
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [java.time LocalDateTime]
           [java.time.format DateTimeFormatter]))

(def date-format (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm"))

(defn parse-entry [s]
  (let [[_ date entry] (re-matches #"\[(.+)\] (.+$)" s)
        date (LocalDateTime/parse date date-format)
        entry (condp #(str/starts-with? %2 %1) entry
                "Guard" [:guard (->> entry (re-find #"#(\d+)")
                                     last read-string)]
                "falls" [:asleep]
                "wakes" [:awake])]
    (into [date] entry)))

(defn group-minutes-by-guard [entries]
  (loop [[entry & entries] entries
         current-guard nil
         by-guard {}]
    (condp #(= %1 (second %2)) entry
      :guard (recur entries (last entry) by-guard)
      :asleep (let [[awake-entry & entries] entries
                    asleep-min (.getMinute (first entry))
                    awake-min (.getMinute (first awake-entry))]
                (recur entries current-guard
                       (update by-guard current-guard
                               (fnil into []) (range asleep-min awake-min))))
      by-guard)))

(defn parse-input [s]
  (group-minutes-by-guard
   (sort-by first (map parse-entry (str/split-lines s)))))

(defn sleepiest-guard [guard-entries]
  (key (apply max-key (comp count val) guard-entries)))

(defn sleepiest-minute [minutes]
  (apply max-key val (frequencies minutes)))

(defn strategy-1 [input]
  (let [guard-id (sleepiest-guard input)
        minute (key (sleepiest-minute (get input guard-id)))]
    (* guard-id minute)))

(defn strategy-2 [input]
  (let [[guard-id [minute freq]]
        (->> input
             (reduce-kv (fn [acc k v]
                          (assoc acc k (sleepiest-minute v))) {})
             (apply max-key (comp last val)))]
    (* guard-id minute)))

(comment
  (let [input (parse-input (slurp (io/resource "day04/input.txt")))]
    ;; First star
    #_(strategy-1 input)
    ;; Second star
    (strategy-2 input))
  )
