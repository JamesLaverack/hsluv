(ns hsluv.core-test
  (:require [clojure.test :refer :all]
            [hsluv.core :refer :all]))

(deftest matrix-operations
  (testing "That we can use matrix operations"
    (is (= [2 2] (foo)))))
