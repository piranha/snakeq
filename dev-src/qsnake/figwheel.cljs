(ns qsnake.figwheel
  (:require [figwheel.client :as fw :include-macros true]
            [qsnake.core :as qsnake]))

(fw/watch-and-reload :jsload-callback
  (fn [] (swap! qsnake/world update-in [:dev] not)))
