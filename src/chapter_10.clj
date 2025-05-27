(ns chapter-10)

;; Clojure Metaphysics: Atoms, Refs, Vars, and Cuddle Zombies

;; Atoms
(comment
  (def fred (atom {:cuddle-hunger-level 0
                   :percent-deteriorated 0}))
  @fred

  (let [zombie-state @fred]
    (if (>= (:percent-deteriorated zombie-state) 50)
      (future (println (:cuddle-hunger-level zombie-state)))))

  (swap! fred
         (fn [current-state]
           (merge-with + current-state {:cuddle-hunger-level 1})))
  @fred

  (swap! fred
         (fn [current-state]
           (merge-with + current-state {:cuddle-hunger-level 1
                                        :percent-deteriorated 1})))

  (defn increase-cuddle-hunger-level [zombie-state increase-by]
    (merge-with + zombie-state {:cuddle-hunger-level increase-by}))

  (increase-cuddle-hunger-level @fred 10)
  @fred

  (swap! fred increase-cuddle-hunger-level 10)
  @fred

  (update-in {:a {:b 3}} [:a :b] inc)
  (update-in {:a {:b 3}} [:a :b] + 10)

  (swap! fred update-in [:cuddle-hunger-level] + 10)

  (let [num (atom 1)
        s1 @num]
    (swap! num inc)
    (println "State 1:" s1)
    (println "Current State:" @num))

  (reset! fred {:cuddle-hunger-level 0
                :percent-deteriorated 0}))

;; Watches and Validators
(comment
  ;; Watches
  (defn shuffle-speed [zombie]
    (* (:cuddle-hunger-level zombie)
       (- 100 (:percent-deteriorated zombie))))

  (defn shuffle-alert [key watched old-state new-state]
    (let [sph (shuffle-speed new-state)]
      (if (> sph 500)
        (do (println "Run, you fool!")
            (println "The zombie's SPH is now " sph)
            (println "This message brought to your courtesy of " key))
        (do (println "All's well with " key)
            (println "Cuddle hunger: " (:cuddle-hunger-level new-state))
            (println "Percent deteriorated: " (:percent-deteriorated new-state))
            (println "SPH: " sph)))))

  (reset! fred {:cuddle-hunger-level 22 :percent-deteriorated 2})
  (add-watch fred :fred-shuffle-alert shuffle-alert)
  (swap! fred update-in [:percent-deteriorated] + 1)
  (swap! fred update-in [:cuddle-hunger-level] + 30)

  ;; Validators
  (defn percent-deteriorated-validator [{:keys [percent-deteriorated]}]
    (and (>= percent-deteriorated 0)
         (<= percent-deteriorated 100)))
  (def bobby (atom {:cuddle-hunger-level 0 :percent-deteriorated 0}
                   :validator percent-deteriorated-validator))
  (swap! bobby update-in [:percent-deteriorated] + 200)
  (swap! bobby update-in [:percent-deteriorated] + 1)

  (defn percent-deteriorated-validator [{:keys [percent-deteriorated]}]
    (or (and (>= percent-deteriorated 0)
             (<= percent-deteriorated 100))
        (throw (IllegalStateException. "That's not mathy!"))))
  (def bobby (atom {:cuddle-hunger-level 0 :percent-deteriorated 0}
                   :validator percent-deteriorated-validator))
  (swap! bobby update-in [:percent-deteriorated] + 200)

  ;; Modeling Sock Transfers
  )
