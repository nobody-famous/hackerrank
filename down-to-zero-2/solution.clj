(def MAX-NUMBER 1000000)

(defn set-i [out i]
  (cond
    (= 0 i) (assoc out i 0)
    (= 1 i) (assoc out i 1)
    (or (= 0 (out i))
        (> (out i) (inc (out (dec i)))))
    (assoc out i (inc (out (dec i))))
    :else out))

(defn set-j [out i j]
  (let [mult (* i j)]
    (if (or (= 0 (out mult)) (> (out mult) (inc (out i))))
      (assoc out mult (inc (out i)))
      out)))

;--------------------------------------------------
; Precompute the values for all possible inputs
;--------------------------------------------------
(defn precompute []
  (loop [i 0 out (vec (repeat MAX-NUMBER 0))]

    (if (< i MAX-NUMBER)
      (recur (inc i) (loop [j 1 new-out (set-i out i)]
                       (if (and (<= j i) (< (* i j) MAX-NUMBER))
                         (recur (inc j) (set-j new-out i j))
                         new-out)))
      out)))

;--------------------------------------------------
; Read the number of queries
;--------------------------------------------------
(def num-queries (Integer/parseInt (read-line)))

;--------------------------------------------------
; Precompute the solutions
;--------------------------------------------------
(def values (precompute))

;--------------------------------------------------
; Read and process each query
;--------------------------------------------------
(loop [iter 0]
  (if (< iter num-queries)
    (do
      (println (values (Integer/parseInt (read-line))))
      (recur (inc iter)))))
