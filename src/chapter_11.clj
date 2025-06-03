(ns chapter-11
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread alts! alts!! timeout]]))

;; Mastering concurrent Processes with core.async

;; Getting started with Processes
(comment
  (def echo-chan (chan))
  (go (println (<! echo-chan)))
  (>!! echo-chan "ketchup")

  ;; Buffering
  (def echo-buffer (chan 2))
  (>!! echo-buffer "ketchup")
  (>!! echo-buffer "ketchup")
  (>!! echo-buffer "ketchup") ;; This blocks because the channel buffer is full

  ;; Blocking and Parking
  (def hi-chan (chan))
  (doseq [n (range 1000)]
    (go (>! hi-chan (str "hi " n))))

  ;; thread
  (thread (println (<!! echo-chan)))
  (>!! echo-chan "mustard")
  (let [t (thread "chili")]
    (<!! t)))

;; The Hot Dog Machine Proccess You've Been Longing For
(comment
  (defn hot-dog-machine []
    (let [in (chan)
          out (chan)]
      (go (<! in)
          (>! out "hot dog"))
      [in out]))

  (let [[in out] (hot-dog-machine)]
    (>!! in "pocket lint")
    (<!! out)))
