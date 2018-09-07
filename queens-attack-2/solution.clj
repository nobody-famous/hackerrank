;--------------------------------------------------
; Calculate the distance between a and b
;
; It's the distance on the chess board, so 4 and 3
; are next to each other and therefore have a distance
; of zero.
;--------------------------------------------------
(defn delta [a b]
  (dec (Math/abs (- a b))))

;--------------------------------------------------
; Given the position of the queen and the position
; of the obstacles, find the closest obstacle for
; each of the 8 directions
;--------------------------------------------------
(defn get-nearest [queen obstacles]
  (let [queen-row (nth queen 0) queen-col (nth queen 1) near {:N -1 :S -1 :E -1 :W -1 :NE -1 :NW -1 :SE -1 :SW -1}]
    (reduce (fn [near ob]
              (let [ob-col (nth ob 1)
                    ob-row (nth ob 0)
                    delta-col (delta queen-col ob-col)
                    delta-row (delta queen-row ob-row)
                    is-diag (= delta-col delta-row)]
                (if is-diag
                  (cond
                    (and (< ob-row queen-row) (< ob-col queen-col)
                         (or (= -1 (near :SW)) (< delta-col (near :SW)))) (assoc near :SW delta-col)
                    (and (< ob-row queen-row) (> ob-col queen-col)
                         (or (= -1 (near :SE)) (< delta-col (near :SE)))) (assoc near :SE delta-col)
                    (and (> ob-row queen-row) (< ob-col queen-col)
                         (or (= -1 (near :NW)) (< delta-col (near :NW)))) (assoc near :NW delta-col)
                    (and (> ob-row queen-row) (> ob-col queen-col)
                         (or (= -1 (near :NE)) (< delta-col (near :NE)))) (assoc near :NE delta-col)
                    :else near)
                  (cond
                    (and (= ob-col queen-col) (< ob-row queen-row)
                         (or (= -1 (near :S)) (< delta-row (near :S))))  (assoc near :S delta-row)
                    (and (= ob-col queen-col) (> ob-row queen-row)
                         (or (= -1 (near :N))  (< delta-row (near :N))))  (assoc near :N delta-row)
                    (and (= ob-row queen-row) (< ob-col queen-col)
                         (or (= -1 (near :W)) (< delta-col (near :W)))) (assoc near :W delta-col)
                    (and (= ob-row queen-row) (> ob-col queen-col)
                         (or (= -1 (near :E)) (< delta-col (near :E)))) (assoc near :E delta-col)
                    :else near))))
            near
            obstacles)))

;--------------------------------------------------
; Given the closest obstacle in each direction,
; calculate the total squares the queen can attack
;--------------------------------------------------
(defn calc-squares [sideLength queen near]
  (let [queen-row (nth queen 0) queen-col (nth queen 1) total 0]
    (reduce (fn [total near]
              (if (= (near 1) -1)
                (case (near 0)
                  :N (+ total (delta (inc sideLength) queen-row))
                  :S (+ total (inc (delta 1 queen-row)))
                  :E (+ total (delta (inc sideLength) queen-col))
                  :W (+ total (inc (delta 1 queen-col)))
                  :NE (+ total (Math/min (delta (inc sideLength) queen-col) (delta (inc sideLength) queen-row)))
                  :NW (+ total (Math/min (inc (delta 1 queen-col)) (delta (inc sideLength) queen-row)))
                  :SE (+ total (Math/min (delta (inc sideLength) queen-col) (inc (delta 1 queen-row))))
                  :SW (+ total (Math/min (inc (delta 1 queen-col)) (inc (delta 1 queen-row)))))
                (+ total (near 1))))
            total
            near)))

;--------------------------------------------------
; Real work starts here
;--------------------------------------------------
(def sizes (map #(Integer/parseInt %) (clojure.string/split (read-line) #" ")))
(def sideLength (nth sizes 0))
(def numObstacles (nth sizes 1))
(def queen (map #(Integer/parseInt %) (clojure.string/split (read-line) #" ")))
(def obstacles (for [_ (range numObstacles)]
                 (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))

(println (calc-squares sideLength queen (get-nearest queen obstacles)))
