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
  )
