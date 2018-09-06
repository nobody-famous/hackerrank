(def length (Integer/parseInt (read-line)))
(def array (map #(Integer/parseInt %) (clojure.string/split (read-line) #" ")))

(println (reduce + array))
