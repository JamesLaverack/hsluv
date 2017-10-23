(ns hsluv.core
  (:require [clojure.core.matrix :as matrix])
  (:require [clojure.math.numeric-tower :as math]))


(defn to-linear
  "Convert a relative R, G, or B sRGB channel to a linear one (in terms of energy)."
  [v]
  (if (<= v 0.04045)
    (/ v 12.92)
    (math/expt (/ (+ v 0.055) 1.055) 2.4)))

(def minv [[0.41239079926595 0.35758433938387 0.18048078840183]
           [0.21263900587151 0.71516867876775 0.072192315360733]
           [0.019330818715591 0.11919477979462 0.95053215224966]])

(defn rgb-to-xyz
  "Convert from sRGB to CIE XYZ"
  [rgb]
  (matrix/mmul minv (map to-linear rgb)))

(defn foo
  "Do some matrix operations"
  []
  (matrix/shape [[1 2] [3 4]]))
