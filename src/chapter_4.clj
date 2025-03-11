(ns chapter-4)

;; Treating lists, vectors, sets, and maps as sequences
(comment
  (defn titleize
    [topic]
    (str topic " for the Brave and True"))

  (map titleize ["Hamsters" "Ragnarok"])
  (map titleize '("Empathy" "Decorating"))
  (map titleize #{"Elbows" "Soap Carving"})
  (map #(titleize (second %)) {:uncomfortable-thing "Winking"}))

;; Abstraction by indirection
(comment
  (seq '(1 2 3))
  (seq [1 2 3])
  (seq #{1 2 3})
  (seq {:name "Bill Compton" :occupation "Dead mopey guy"})
  (into {} (seq {:a 1 :b 2 :c 3}))
  )

;; Seq functions examples
(comment
  ;; map
  (map inc [1 2 3])
  (map str ["a" "b" "c"] ["A" "B" "C"])
  (list (str "a" "A") (str "b" "B") (str "c" "C"))
  (def human-consumption [8.1 7.3 6.6 5.0])
  (def critter-consumption [0.0 0.2 0.3 1.1])
  (defn unify-diet-data
    [human critter]
    {:human human
     :critter critter})
  (map unify-diet-data human-consumption critter-consumption)
  (list (unify-diet-data 8.1 0.0) (unify-diet-data 7.3 0.2) (unify-diet-data 6.6 0.3) (unify-diet-data 5.0 1.1))
  (def sum #(reduce + %))
  (def avg #(/ (sum %) (count %)))
  (defn stats
    [numbers]
    (map #(% numbers) [sum count avg]))
  (stats [1 2 3])
  (stats [3 4 10])
  (stats [80 1 44 13 6])
  (def identities
    [{:alias "Batman" :real "Bruce Wayne"}
     {:alias "Spider-Man" :real "Peter Parker"}
     {:alias "Santa" :real "Your mom"}
     {:alias "Easter Bunny" :real "Your dad"}])
  (map :real identities)
  ;; reduce
  )
