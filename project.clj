(defproject qsnake "0.1.0-SNAPSHOT"
  :description "snake, oh, snake, oh, it's a snake"
  :url "https://github.com/piranha/qsnake"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [com.facebook/react "0.11.2"]
                 [sablono "0.2.22"]
                 [quiescent "0.1.4"]]

  :profiles {:dev {:dependencies [[figwheel "0.1.4-SNAPSHOT"
                                   :exclusions [org.clojure/core.async]]
                                  [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
                   :plugins [[lein-cljsbuild "1.0.3"]
                             [lein-figwheel "0.1.4-SNAPSHOT"]]}}

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src" "dev-src"]
              :compiler {:output-to "resources/public/js/qsnake.js"
                         :output-dir "resources/public/js/out"
                         :optimizations :none
                         :source-map true}}
             {:id "www"
              :source-paths ["src"]
              :compiler {:output-to "www/qsnake.min.js"
                         :optimizations :advanced
                         :pretty-print false
                         :preamble ["react/react.min.js"]
                         :externs ["react/externs/react.js"]}}]}
  :figwheel {
             :http-server-root "public" ;; default and assumes "resources"
             :server-port 3449 ;; default
             :css-dirs ["public/resources/css"] ;; watch and update CSS
             })
