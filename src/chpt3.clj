(ns chpt3)

;; program 3.4
;; sieve of Eratosthenes, re-interpreted for Clojure
;; 
;; 1. Create a list of consecutive integers from 2 to n: (2, 3, 4, ..., n).
;; 2. Initially, let p equal 2, the first prime number.
;; 3. Starting from p, count up in increments of p and mark each of these numbers greater than p itself in the list. 
;;    These numbers will be 2p, 3p, 4p, etc.; note that some of them may have already been marked.
;; 4. Find the first number greater than p in the list that is not marked; let p now equal this number (which is the next prime).
;; 5. If there were no more unmarked numbers in the list, stop. Otherwise, repeat from step 3.

;; this is an ok implementation, but runs out of memory for very high numbers
;; would be better if it was lazy?
(defn sieve-of-eratosthenes
  [limit]
  (letfn [(find-primes [pos limit sieve]
    (cond
      (= pos limit) sieve                                ;; base case - we are at the limit, nothing more to check
      (nil? (sieve pos)) (recur (inc pos) limit sieve)   ;; skip over anything that has already been set to nil
      :else (recur (inc pos) limit                       ;; sieve it!
                   (loop [multiplier 2, new-sieve sieve]
                     (let [sieved-pos (* multiplier pos)]
                       (if (< sieved-pos limit)
                         (recur (inc multiplier) (assoc new-sieve sieved-pos nil))
                         new-sieve))))))]
         (let [sieved (find-primes 2 limit (vec (range limit)))]
           (filter (complement nil?) (drop 2 sieved)))))

;; program 3.8
;; Uses a circular list. How would one implement this in Clojure?
;; The algorithm is so imperative, with loops. Let's write a better
;; Clojure version.
(defn josephus 
  "Create a list of length n then take away every mth until there is only 1 number left."
  [n m]
  ;; since we are starting at one need to compensate for zero indexed sequence.
  (let [m (dec m)]
    (loop [circle (cycle (range 1 (inc n)))
           dropped #{}]
      (println (take (- n (count dropped)) circle) " " dropped)
      (if (= (count dropped) (dec n))
        (first circle)
        (let [dropped (conj dropped (nth circle m))]
          (recur (drop m (filter (complement dropped) circle)) dropped))))))

