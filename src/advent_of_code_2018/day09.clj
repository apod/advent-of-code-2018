(ns advent-of-code-2018.day09)

(defn deque []
  (java.util.ArrayDeque.))

(defn rotate [n ^java.util.ArrayDeque circle]
  (cond
    (> n 0)
    (let [v (or (.pollLast circle) 0)]
      (.addFirst circle v)
      (rotate (dec n) circle))
    (< n 0)
    (let [v (or (.pollFirst circle) 0)]
      (.addLast circle v)
      (rotate (inc n) circle))))

(defn play [players marbles]
  (let [circle (deque)
        scores (atom {})]
    (.addLast circle 0)
    (doseq [val (range 1 (inc marbles))]
      (if (zero? (mod val 23))
        (do
          (rotate -7 circle)
          (let [score (+ (or (.pollLast circle) 0) val)
                player (mod (dec val) players)]
            (swap! scores update player (fnil + 0) score)))
        (do
          (rotate 2 circle)
          (.addLast circle val))))
    @scores))

(defn high-score [players last-marble]
  (val (apply max-key val (play players last-marble))))

(comment
  ;; 428 players; last marble is worth 70825 points
  (let [players 428 last-marble 70825]
    ;; First star
    #_(high-score players last-marble)
    ;; Second star
    (high-score players (* 100 last-marble))
    )
  )
