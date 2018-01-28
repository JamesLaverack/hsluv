(ns hsluv.core
  (:require [clojure.core.matrix :as matrix])
  (:require [clojure.math.numeric-tower :as math]))


(defn to-linear
  "Convert a relative R, G, or B sRGB channel to a linear one (in terms of energy)."
  [v]
  (if (<= v 0.04045)
    (/ v 12.92)
    (math/expt (/ (+ v 0.055) 1.055) 2.4)))

(defn from-linear
  [v]
  (if (<= v 0.0031308)
    (* v 12.92)
    (- (* 1.055 (math/expt v (/ 1 2.4))) 0.055)))

(def minv [[0.41239079926595 0.35758433938387 0.18048078840183]
           [0.21263900587151 0.71516867876775 0.072192315360733]
           [0.019330818715591 0.11919477979462 0.95053215224966]])

(def m [[3.240969941904521 -1.537383177570093 -0.498610760293]
        [-0.96924363628087 1.87596750150772 0.041555057407175]
        [0.055630079696993 -0.20397695888897 1.056971514242878]])

(defn rgb-to-xyz
  "Convert from sRGB to CIE XYZ"
  [rgb]
  (matrix/mmul minv (map to-linear rgb)))

(defn xyz-to-rgb
  "Convert from CIE XYZ to sRGB"
  [xyz]
  (map from-linear (matrix/mmul m xyz)))

(def ref-y 1.0)
(def ref-u 0.19783000664283)
(def ref-v 0.46831999493879)

(def kappa 903.2962962)
(def epsilon 0.0088564516)

(defn var-u
  [[x y z]]
  (/ (* 4 x) (+ x (* 15 y) (* 3 z))))

(defn var-v
  [[x y z]]
  (/ (* 9 y) (+ x (* 15 y) (* 3 z))))

(defn y-to-l
  [y]
  (if (<= y epsilon)
    (* kappa (/ y ref-y))
    (- (* 116 (math/expt (/ y ref-y) (/ 1 3))) 16)))

(defn l-to-y
  [l]
  (if (<= l 8)
    (* ref-y (/ l kappa))
    (* ref-y (math/expt (/ (+ l 16) 116) 3))))

(defn xyz-to-luv
  "Convert form CIE XYZ to LUV"
  [xyz]
  (let [l (let [[_ y _] xyz] (y-to-l y))]
    (if (= l 0.0)
      (list 0 0 0)
      (list l
            (* 13 l (- (var-u xyz) ref-u))
            (* 13 l (- (var-v xyz) ref-v))))))

(defn foo
  "Do some matrix operations"
  []
  (matrix/shape [[1 2] [3 4]]))
