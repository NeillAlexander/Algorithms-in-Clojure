(ns chpt1)

;; Below are functions that solve the connectivity problem i.e. whether or not 2 objects
;; are connected.

;; This is a solution that is very quick to lookup once it has been created, but which
;; is expensive to create (quick find, slow union).
;; Find is quick because once the data structure exists, we just need to do a get
;; at i and j to verify if they are connected.
(defn quick-find 
  "n defines the size of the set we wish to check for connectedness, pairs
represent objects that are not yet connected."
  [n pairs]
  (loop [v (vec (range 0 n))
         pairs (partition 2 pairs)]    
    (println v " : " pairs)
    (if (seq pairs) 
      (let [pq (first pairs)
            name-p (v (first pq))
            name-q (v (second pq))]
        ;; this is the meat of the algorithm, equivalent to the for loop from the book.
        (recur (vec (map #(if (= name-p %) name-q %) v)) (rest pairs)))
      v)))

;; example from book
(comment (quick-find 10 [3 4, 4 9, 8 0, 2 3, 5 6, 2 9, 5 9, 7 3, 4 8, 5 6, 0 2, 6 1]))

;; exercise 1.4
(comment (quick-find 10 [0 2, 1 4, 2 5, 3 6, 0 4, 6 0, 1 3]))

;; Variation on the algorithm where the way we connect the pairs is different.
(defn find-index 
  "If v[i] == i, return i, else go to v[v[i]] and if that is equal to i return it."
  [v i]
  (let [val-at-i (v i)]
    (println "val-at" i "=" val-at-i)
    (if (= i val-at-i)
      i
      (recur v val-at-i))))

(defn quick-union
  [n pairs]
  (loop [id (vec (range 0 n))
         pairs (partition 2 pairs)]    
    (println id " : " pairs)
    (if (seq pairs) 
      (let [pq (first pairs)
            i (find-index id (first pq))
            j (find-index id (second pq))]
        (if-not (= (id i) (id j))
          (recur (assoc id i (id j)) (rest pairs))
          (recur id (rest pairs))))
      id)))

(defn weighted-quick-union
  "Modification of quick-union that keeps track of the size of the sub-trees, and links to 
the smaller of the 2 trees."
  [n pairs]
  (loop [id (vec (range 0 n))         
         pairs (partition 2 pairs)
         sz (vec (take n (repeat 1)))]    
    (println id " : " sz " : " pairs)
    (if (seq pairs) 
      (let [pq (first pairs)
            i (find-index id (first pq))
            j (find-index id (second pq))]
        (println "i =" i " j =" j " sz[i] =" (sz i) " sz[j] =" (sz j))
        (if-not (= (id i) (id j))          
          ;; NB: it looks like there is a bug in the original program 1.3 code in the book.
          ;; The assignment is reversed and that leads to an unbalanced tree.
          ;; This is a correct version.
          (if (< (sz i) (sz j)) 
            (recur (assoc id j i) (rest pairs) (update-in sz [j] + (sz i)))
            (recur (assoc id i j) (rest pairs) (update-in sz [i] + (sz j))))
          (recur id (rest pairs) sz)))
      id)))

;; Check if the pair are connected given the connected pairs.
(defn connected?
  [pair pairs n data-fn value-fn]
  (let [data (data-fn n pairs)]
      (= (value-fn data (first pair)) (value-fn data (second pair)))))

;; Once the data structure has been created it is a simple 'get' required
;; to verify whether the pair is connected.
(defn connected-quick-find?  
  [pair pairs n]
  (connected? pair pairs n quick-find get))

;; Note that this one needs to do more work to determine whether the pair
;; is connected, via the find-index.
(defn connected-quick-union?
  [pair pairs n]
  (connected? pair pairs n quick-union find-index))

(defn connected-weighted-quick-union?
  [pair pairs n]
  (connected? pair pairs n weighted-quick-union find-index))