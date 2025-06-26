(ns chapter-13)

;; Creating and Extending Abstractions With Multimethods, Protocols, and Records

;; Polymorphism
(comment
  (conj '(1 2) 0)
  (conj [1 2] 0)
  (class "a")
  (type "a")

  ;; Multimethods
  (defmulti full-moon-behavior
    (fn [were-creature] (:were-type were-creature)))

  (defmethod full-moon-behavior :wolf [were-creature]
    (str (:name were-creature)
         " will howl and murder!"))

  (defmethod full-moon-behavior :simmons [were-creature]
    (str (:name were-creature)
         " will encourage people and sweat to the oldies"))

  (full-moon-behavior {:were-type :wolf
                       :name "Rachel from next door"})

  (full-moon-behavior {:were-type :simmons
                       :name "Andy the baker"})

  (defmethod full-moon-behavior nil [were-creature]
    (str (:name were-creature) " will stay at home and eat ice cream."))

  (full-moon-behavior {:name "Martin the nurse"})

  (defmethod full-moon-behavior :default [were-creature]
    (str (:name were-creature) " will stay up all night fantasy footbaling"))

  (full-moon-behavior {:were-type :office-worker
                       :name "Jimmy from sales"})

  (defmethod full-moon-behavior :bill-murray [were-creature]
    (str (:name were-creature) " will be the most likeable celebrity"))

  (full-moon-behavior {:were-type :bill-murray
                       :name "Laura the intern"})

  (defmulti types (fn [x y] [(class x) (class y)]))

  (defmethod types [java.lang.String java.lang.String] [x y]
    "Two strings!")
  (types "String 1" "String 2")

  ;; Protocols
  (defprotocol Psychodynamics
    "Plumb the inner depths of your data types"
    (thoughts [x] "The data type's innermost thoughts")
    (feeling-about [x] [x y] "Feelings about self or other"))

  (extend-type java.lang.String
    Psychodynamics
    (thoughts [x]
      (str x " thinks, 'Truly, the character defines the data type'"))
    (feeling-about
      ([x]
       (str x " is longing for a simpler way of life"))
      ([x y]
       (str x " is envious of " y "'s simpler way of life"))))

  (thoughts "blorb")
  (feeling-about "schmorb")
  (feeling-about "schmorb" 2)

  (extend-type java.lang.Object
    Psychodynamics
    (thoughts [x]
      "Maybe the internet is just a vector for taxoplasmosis.")
    (feeling-about
      ([x] "meh")
      ([x y] (str "meh about " y))))

  (thoughts 3)
  (feeling-about 3)
  (feeling-about 3 "blorb")

  (extend-protocol Psychodynamics
    java.lang.String
    (thoughts [x]
      "Truly, the character defines the data type")
    (feeling-about
      ([x] "longing for a simpler way of life")
      ([x y] (str "envious of " y "'s simpler way of life")))

    java.lang.Object
    (thoughts [x]
      "Maybe the internet is just a vector for taxoplasmosis")
    (feeling-about
      ([x] "meh")
      ([x y] (str "meh about " y)))))

;; Records
(comment
  (defrecord WereWolf [name title])

  (WereWolf. "David" "Longon Tourist")
  (->WereWolf "Jacob" "Lead Shirt Discarder")
  (map->WereWolf {:name "Lucian" :title "CEO of Melodrama"})
  (def jacob (->WereWolf "Jacob" "Lead Shirt Discarder"))
  jacob
  (type jacob)
  (type {:a "1"})
  (.name jacob)
  (:name jacob)
  (get jacob :name)
  (= jacob (->WereWolf "Jacob" "Lead Shirt Discarder"))
  (= jacob (WereWolf. "David" "Lead Shirt Discarder"))
  (= jacob (WereWolf. "Jacob" "Lead Shirt Discarder"))
  (= jacob {:name "Jacob" :title "Lead Shirt Discard"})

  (assoc jacob :title "Lead Third Wheel")
  ;; Here is a WereWolf
  (type (assoc jacob :title "Lead Third Wheel"))
  ;; Here is a PersistentArrayMap
  (type (dissoc jacob :title))

  (defprotocol WereCreature
    (full-moon-behavior [x]))

  (defrecord WereWolf [name title]
    WereCreature
    (full-moon-behavior [x]
      (str name " will howl and murder")))

  (full-moon-behavior (map->WereWolf {:name "Lucian"
                                      :title "CEO of Melodrama"})))

;; Exercises
(comment
  ;; 1.
  (defmethod full-moon-behavior :curupira [were-creature]
    (str (:name were-creature) " vai andar pra frente, mas parece que esta de costa."))
  (full-moon-behavior {:were-type :curupira :name "Serjao berranteiro"})

  ;; 2.
  (defrecord WereSimmons [name title]
    WereCreature
    (full-moon-behavior [x]
      (str name " will stay at home and eat ice cream.")))
  (full-moon-behavior (map->WereSimmons {:name "Luva" :title "Maior de todos"}))

  ;; 3.
  (defprotocol MeuPet
    (gato [x])
    (doggo [x y]))

  (extend-type java.lang.Object
    MeuPet
    (gato [x] (str x " mia."))
    (doggo [x y] (str x " e " y " latem.")))

  (gato "Yumi")
  (doggo "Ketut" "Jack")

  (extend-protocol MeuPet
    java.lang.String
    (gato [x] " mia e arranha o sofa.")
    (doggo [x y] " latem e brincam de bolinha.")

    java.lang.Object
    (gato [x] (str x " mia."))
    (doggo [x y] (str x " e " y " latem.")))

  (gato "Yumi")
  (gato {})
  (doggo "Ketut" "Jack")
  (doggo {} 1)

  ;; 4.
  )
