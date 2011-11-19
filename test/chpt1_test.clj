(ns chpt1-test
  (:use [clojure.test]
        [chpt1]))

(deftest test-find-index
  (is (= 3 (find-index [0 1 2 3] 3)))
  (is (= 2 (find-index [1 2 2 3] 0)))
  (is (= 5 (find-index [9 8 7 6 5 5 3 4 1 0] 2))))
