;--------------------------------------------------
; Generate the deltas for the operation boundaries
;--------------------------------------------------
(defn gen-deltas [num-elems ops]
  (loop [iter 0 deltas (vec (repeat (inc num-elems) 0))]
    (if (< iter (count ops))
      (let [a ((nth ops iter) 0) b (inc ((nth ops iter) 1)) value ((nth ops iter) 2)]
        (if (<= b num-elems)
          (recur (inc iter) (update-in
                             (update-in deltas [b] - value)
                             [a] + value))
          (recur (inc iter) (update-in deltas [a] + value))))
      deltas)))

;--------------------------------------------------
; Loop over the deltas array and find the max value
;--------------------------------------------------
(defn calc-max [num-elems deltas]
  (loop [iter 1 cur-value 0 max -1]
    (if (<= iter num-elems)
      (do
        (recur (inc iter)
               (+ cur-value (nth deltas iter))
               (if (or (= max -1) (> cur-value max)) cur-value max)))
      (if (or (= max -1) (> cur-value max)) cur-value max))))

;--------------------------------------------------
; Read the sizes
;--------------------------------------------------
(def sizes (clojure.string/split (read-line) #" "))
(def num-elems (Integer/parseInt (clojure.string/trim (nth sizes 0))))
(def num-ops (Integer/parseInt (clojure.string/trim (nth sizes 1))))

;--------------------------------------------------
; Read the operations
;--------------------------------------------------
(def ops [])
(doseq [_ (range num-ops)]
  (def ops
    (conj ops
          (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))))

;--------------------------------------------------
; Do the work
;--------------------------------------------------
(def deltas (gen-deltas num-elems ops))
(println (calc-max num-elems deltas))
