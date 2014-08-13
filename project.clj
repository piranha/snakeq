(defproject snake "0.1.0-SNAPSHOT"
  :description "snake, oh, snake, oh, it's a snake"
  :url "https://github.com/piranha/qsnake"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2280"] ;; 2311
                 [figwheel "0.1.3-SNAPSHOT"]
                 [sablono "0.2.16"]
                 [quiescent "0.1.4"]]
  
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-figwheel "0.1.3-SNAPSHOT"]]

  :cljsbuild {
              :builds [{:source-paths ["src"]
                        :compiler {:output-to "resources/public/js/compiled/snake.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :optimizations :none
                                   :source-map true}}]}
  :figwheel {
             :http-server-root "public" ;; default and assumes "resources" 
             :server-port 3449 ;; default
             :css-dirs ["public/resources/css"] ;; watch and update CSS
             })
