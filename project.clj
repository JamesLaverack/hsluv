(defproject hsluv "0.1.0-SNAPSHOT"
  :description "Pure Clojure implementation of HSLuv"
  :url "https://github.com/JamesLaverack/hsluv"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.mikera/core.matrix "0.61.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]]
  :profiles {:test {:dependencies [[org.clojure/data.json "0.2.6"]]}})
