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
  (into {} (seq {:a 1 :b 2 :c 3})))

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
  (reduce (fn [new-map [key val]]
            (assoc new-map key (inc val)))
          {}
          {:max 30 :min 10})
  (reduce (fn [new-map [key val]]
            (if (> val 4)
              (assoc new-map key val)
              new-map))
          {}
          {:human 4.1
           :critter 3.9})
  ;; take, drop, take-while and drop-while
  (take 3 [1 2 3 4 5 6 7 8 9 10])
  (drop 3 [1 2 3 4 5 6 7 8 9 10])
  (def food-journal
    [{:month 1 :day 1 :human 5.3 :critter 2.3}
     {:month 1 :day 2 :human 5.1 :critter 2.0}
     {:month 2 :day 1 :human 4.9 :critter 2.1}
     {:month 2 :day 2 :human 5.0 :critter 2.5}
     {:month 3 :day 1 :human 4.2 :critter 3.3}
     {:month 3 :day 5 :human 4.0 :critter 3.8}
     {:month 4 :day 1 :human 3.7 :critter 3.9}
     {:month 4 :day 4 :human 3.7 :critter 3.6}])
  (take-while #(< (:month %) 3) food-journal)
  (drop-while #(< (:month %) 3) food-journal)
  (take-while #(< (:month %) 4)
              (drop-while #(< (:month %) 2) food-journal))
  ;; filter and some
  (filter #(< (:human %) 5) food-journal)
  (filter #(< (:month %) 3) food-journal)
  (some #(> (:critter %) 5) food-journal)
  (some #(> (:critter %) 3) food-journal)
  (some #(and (> (:critter %) 3) %) food-journal)
  ;; sort and sort-by
  (sort [3 1 2])
  (sort-by count ["aaa" "c" "bb"])
  (sort-by :preco [{:preco 10} {:preco 1} {:preco 5}])
  ;; concat
  (concat [1 2] [3 4]))

;; Lazy Seqs
(comment
  ;; Demonstrating Lazy Seq Efficiency
  (def vampire-database
    {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
     1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
     2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
     3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})
  (defn vampire-related-details
    [social-security-number]
    (Thread/sleep 1000)
    (get vampire-database social-security-number))
  (defn vampire?
    [record]
    (and (:makes-blood-puns? record)
         (not (:has-pulse? record))
         record))
  (defn identify-vampire
    [social-security-numbers]
    (first (filter vampire?
                   (map vampire-related-details social-security-numbers))))
  (time (vampire-related-details 0))
  (time (def mapped-details (map vampire-related-details (range 0 1000000))))
  (time (first mapped-details))
  (time (identify-vampire (range 0 1000000)))
  ;; Infinite Sequences
  (concat (take 8 (repeat "na")) ["Batman!"])
  (take 3 (repeatedly (fn [] (rand-int 10))))
  (take 5 (repeatedly (fn [] (rand-int 10))))
  (defn even-numbers
    ([] (even-numbers 0))
    ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))
  (take 10 (even-numbers))
  (cons 0 '(2 4 6)))

;; The Collection Abstraction
(comment
  (empty? [])
  (empty? ["no!"])
  ;; into
  (map identity {:sunlight-reaction "Glitter!"})
  (into {} (map identity {:sunlight-reaction "Glitter!"}))
  (map identity [:garlic :sesame-oil :fried-eggs])
  (into [] (map identity [:garlic :sesame-oil :fried-eggs]))
  (map identity [:garlic-clove :garlic-clove])
  (into #{} (map identity [:garlic-clove :garlic-clove]))
  (into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]])
  (into ["cherry"] '("pine" "spruce"))
  (into {:favorite-animal "kitty"}
        {:least-favorite-smell "dog"
         :relationship-with-teenager "creepy"})
  ;; conj
  (conj [0] [1])
  (into [0] [1])
  (conj [0] 1)
  (conj [0] 1 2 3 4)
  (conj {:time "midnight"} [:place "ye olde cemetarium"])
  (defn my-conj
    [target & additions]
    (into target additions))
  (my-conj [0] 1 2 3)
  )

;; Function Functions
(comment
  ;; apply
  (max 0 1 2)
  ;; (max [0 1 2])
  (apply max [0 1 2])
  (defn my-into
    [target additions]
    (apply conj target additions))
  (my-into [0] [1])
  ;; partial
  (def add10 (partial + 10))
  (add10 3)
  (add10 5)
  (def add-missing-elements
    (partial conj ["water" "earth" "air"]))
  (add-missing-elements "unobtainium" "adamantium")
  (defn my-partial
    [partialized-fn & args]
    (fn [& more-args]
      (apply partialized-fn (into args more-args))))
  (def add20 (my-partial + 20))
  (add20 7)
  (defn lousy-logger
    [log-level message]
    (condp = log-level
      :warn (clojure.string/lower-case message)
      :emergency (clojure.string/upper-case message)))
  (def warn (partial lousy-logger :warn))
  (warn "Red light ahead")
  ;; complement
  (defn identify-humans
    [social-security-numbers]
    (filter #(not (vampire? %))
            (map vampire-related-details social-security-numbers)))
  (def not-vampire? (complement vampire?))
  (defn identify-humans
    [social-security-numbers]
    (filter not-vampire?
            (map vampire-related-details social-security-numbers)))
   (defn my-complement
     [f]
     (fn [& args]
       (not (apply f args))))
  (def my-pos? (complement neg?))
  (my-pos? 1)
  )
