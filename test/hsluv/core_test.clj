(ns hsluv.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.algo.generic.math-functions :as gf]
            [hsluv.core :refer :all]))

(defn =vec
  [a b cmp]
  (reduce (fn [a b] (and a b)) (map cmp a b)))

(def match-tolerance 1e-9)

(defn colour-match
  [expected actual]
  (=vec expected actual (fn [expect actual] (gf/approx= expect actual match-tolerance))))

(deftest rgb-to-xyz-test
  (testing "sRGB to CIE XYZ conversion"
    (let [data (json/read (io/reader (io/resource "snapshot-rev4.json")))]
      (loop [colour (first (vals data))
             remaining-colours (rest (vals data))]
        (if (not (nil? colour))
          (let [rgb (map double (get colour "rgb"))
                xyz (map double (get colour "xyz"))]
            (is (colour-match xyz (rgb-to-xyz rgb)))
            (recur (first remaining-colours) (rest remaining-colours))))))))

(deftest xyz-to-rgb-test
  (testing "CIE XYZ to sRGB conversion"
    (let [data (json/read (io/reader (io/resource "snapshot-rev4.json")))]
      (loop [colour (first (vals data))
             remaining-colours (rest (vals data))]
        (if (not (nil? colour))
          (let [rgb (map double (get colour "rgb"))
                xyz (map double (get colour "xyz"))]
            (is (colour-match rgb (xyz-to-rgb xyz)))
            (recur (first remaining-colours) (rest remaining-colours))))))))
