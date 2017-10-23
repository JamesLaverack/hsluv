(ns hsluv.core-test
  (:require [clojure.test :refer :all]
            [hsluv.core :refer :all]))

(deftest matrix-operations
  (testing "That we can use matrix operations"
    (is (= [2 2] (foo)))))

(deftest rgb-to-xyz-test
  (testing "sRGB to CIE XYZ converstion"
    (is (= [0.308043578886299796 0.612655858810891907 0.102019012460713238] (rgb-to-xyz [0.0666666666666666657 0.933333333333333348 0])))))
