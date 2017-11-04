(ns hsluv.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [hsluv.core :refer :all]))

(deftest rgb-to-xyz-test
  (testing "sRGB to CIE XYZ converstion"
    (let [data (json/read (io/reader (io/resource "snapshot-rev4.json")))]
      (loop [colour (first (vals data))
             remaining-colours (rest (vals data))]
        (if (not (nil? colour))
          (let [rgb (get colour "rgb")
                xyz (get colour "xyz")]
            (is (= xyz (rgb-to-xyz rgb)))
            (recur (first remaining-colours) (rest remaining-colours))))))))
      
