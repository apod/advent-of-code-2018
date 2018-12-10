(ns advent-of-code-2018.day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [quil.core :as q]
            [quil.middleware :as m]))

(defn parse-point [s]
  (let [[[_ pos] [_ velocity]] (re-seq #"<([^>]+)>" s)
        [x y] (read-string (str \[ pos \]))
        [vx vy] (read-string (str \[ velocity \]))]
    (zipmap [:x :y :vx :vy] [x y vx vy])))

(defn parse-input [s]
  (map parse-point (str/split-lines s)))

(defn boundaries [points]
  (let [xs (map :x points)
        ys (map :y points)]
    (vector (mapv #(apply min %) [xs ys])
            (mapv #(apply max %) [xs ys]))))

(defn setup [points]
  (let [[[min-x min-y] [max-x max-y]] (boundaries points)]
    (fn []
      (q/frame-rate 60)
      {:mode :pause
       :current-time 0
       :points points
       :min-x min-x :min-y min-y
       :max-x max-x :max-y max-y})))

(defn draw-state [{:keys [current-time min-x min-y max-x max-y points] :as state}]
  (q/background 240)
  (q/fill 15)
  (q/stroke 15)
  (q/stroke-weight 1.5)
  (q/text (str "Time: " current-time) 5 15)
  (doseq [{:keys [x y]} points]
    (let [x (q/map-range x min-x max-x 40 200)
          y (q/map-range y min-y max-y 40 90)]
      (q/point x y))))

(defn key-released [state event]
  (let [time-step (condp = (:key event)
                    :a -1   :d 1
                    :A -50  :D 50
                    :w 100  :s -100
                    :q -500 :e 500
                    0)
        current-time (+ (:current-time state) time-step)
        update-point (fn [{:keys [x y vx vy] :as point}]
                       (-> point
                           (update :x + (* vx time-step))
                           (update :y + (* vy time-step))))
        points (map update-point (:points state))
        [[min-x min-y] [max-x max-y]] (boundaries points)]
    (assoc state
           :points points
           :current-time current-time
           :min-x min-x :min-y min-y
           :max-x max-x :max-y max-y)))

(defn visualize-message [points]
  (q/sketch
   :title "Day 10: The Stars Align"
   :size [400 200]
   :setup (setup points)
   :draw draw-state
   :key-pressed key-released
   :features [:keep-on-top]
   :middleware [m/fun-mode]))

(comment
  ;; First star
  (let [example (parse-input (slurp (io/resource "day10/example.txt")))
        input (parse-input (slurp (io/resource "day10/input.txt")))]
    ;; First & second star
    (visualize-message input))
  )


