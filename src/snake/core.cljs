(ns snake.core
  (:require [figwheel.client :as fw]
            [sablono.core :as html :refer-macros [html]]
            [quiescent :as q :include-macros true]
            [clojure.string :as str]))

(declare Root)

(defn rand-cell [size]
  (rand-int (* size size)))

(defn cx [& {:as classes}]
  (str/join " " (for [[k v] classes
                      :when v]
                  (name k))))

;; data
(def size 15)
(def init-state {:size size
                 :snake [(quot (* size size) 2)]
                 :dir :up
                 :food nil
                 :dead false})
(defonce world (atom init-state))

(defn restart []
  (reset! world init-state))

(let [render-pending? (atom false)]
  (defn request-render [data]
    (when (compare-and-set! render-pending? false true)
      (.requestAnimationFrame js/window
        (fn []
          (q/render (Root data) (.getElementById js/document "main-area"))
          (reset! render-pending? false))))))

(add-watch world ::render
  (fn [_ _ _ data]
    (request-render data)))

(fw/watch-and-reload
 :jsload-callback (fn []
                    (swap! world assoc :tmp-dev (not (:tmp-dev @world)))))

;; logic

(defn next-head [head dir size]
  (+ head (dir {:up (- size)
                :down size
                :left -1
                :right 1})))

(defn calc-borders [head size]
  {:up (< head size)
   :down (= (dec size) (quot head size))
   :left (= 0 (mod head size))
   :right (= (dec size) (mod head size))})

(defn spawn-food [snake size]
  (loop [cell (rand-cell size)]
    (if (some #{cell} snake)
      (recur (rand-cell size))
      cell)))

(defn next-tick
  [{:keys [snake size dir dead food] :as data}]
  (if dead
    data
    (let [head (first snake)
          new (next-head head dir size)
          borders (calc-borders head size)
          tail (if (= new food)
                 snake
                 (butlast snake))
          food (if (or (not food) (= new food))
                 (spawn-food snake size)
                 food)
          snake (into [new] tail)]
      (if (or (dir borders)
            (some #{new} tail))
        (assoc data :dead true)
        (assoc data
          :snake snake
          :food food)))))

(def KEYS
  {37 :left 38 :up 39 :right 40 :down})

(defn start
  []
  (request-render @world)
  (.addEventListener js/window "keydown"
    (fn [e]
      (if-let [key (KEYS (aget e "keyCode"))]
        (swap! world #(assoc % :dir key)))))
  (js/setInterval
    (fn []
      (swap! world next-tick))
    200))

;; html

(q/defcomponent Board
  [{:keys [size snake food] :as data}]
  (html
    [:table
     (for [i (range size)]
       [:tr {:key (* i size)}
        (for [j (range size)
              :let [id (+ j (* i size))]]
          [:td
           {:key id
            :className (cx :snake (some #{id} snake)
                           :fruit (= food id))}
           " "])])]))

(q/defcomponent Root
  [data]
  (html
    [:div
     [:h1 (if (:dead data) "x_x" "Snake")
      [:button {:on-click restart} "Restart"]]
     (Board data)
     [:div {:style {:font-size "2em"}} (pr-str data)]]))


(defonce app (start))
