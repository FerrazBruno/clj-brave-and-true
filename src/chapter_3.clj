(ns chapter-3)

;; Do things, A Clojure Crash Course


;; Syntax

;; Forms

;; Clojure recognizes two kinds of structures:
;; Literal representation of data structures(like numbers, strings, maps and vectors)
;; Operations

;; We use the term "form" to valid code

;; These literal representation are all valid forms:
(comment
  1
  "a string"
  ["a" "vector" "of" "strings"]
  )

;; Operations
;; (operator operand1 operand2... operandn)

(comment
  (+ 1 2 3)
  (str "It was the panda " "int the library " "with a dust buster")
  )

;; Control Flow

;; basic control flow operations: if, do and when

;; if
(comment
  (if true
    "By Zeus's hammer!"
    "By Aquaman's trident!")

  (if false
    "By Zeus's hammer!"
    "By Aquaman's trident!")

  (if false
    "By Odin's elbow!")
  )

;; do
(comment
  (if true
    (do
      (println "Success!")
      "By Zeus's hammer!")
    (do
      (println "Failure!")
      "By Aquaman's trident!"))
  )

;; when
(comment
  (when true
    (println "Success!")
    "abra cadabra"))

;; nil, true, false, Truthiness, Equality, and Boolean Expressions
(comment
  (nil? 1)
  (nil? nil)
  )

;; both "nil" and "false" are used to represent logical falsiness
;; all other values are logically truthy
(comment
  (if "bears eat beats"
    "bears beets battlestart galactica")

  (if nil
    "This won't be the result because nil is falsey"
    "nil is falsey")
  )

(comment
  (= 1 1)
  (= nil nil)
  (= 1 2)
  )

;; "or" and "and"
(comment
  (or false nil :large_I_mean_venti :why_cant_I_just_say_large)
  (or (= 0 1) (= "yes" "no"))
  (or nil)

  (and :free_wifi :hot_coffee)
  (and :feeling_super_cool nil false)
  )

;; Naming Values with "def"
(comment
  (def failed-protagonist-names
    ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"]))

;; wrong
(comment
  (def severity :mild)
  (def error-message "OH GOD! IT'S A DISASTER! WE'RE ")
  (if (= severity :mild)
    (def error-message (str error-message "MILDLY INCONVENIENCED!"))
    (def error-message (str error-message "DOOOOOOOOOOOMED!")))
  error-message
  )

;; right
(comment
  (defn error-message
    [severity]
    (str "OH GOD! IT'S A DISASTER! WE'RE "
         (if (= severity :mild)
           "MILDLY INCONVENIENCED!"
           "DOOOOOOOOOOOMED!")))
  (error-message :mild)
  (error-message :milk)
  )


;; Data Structures

;; Numbers
(comment
  ;; interger
  93
  ;; float
  1.2
  ;; ratio
  1/5
  )

;; Strings
(comment
  "Lord Voldemort"
  "\"He whot must not be named\""
  "\"Great cow of Moscou!\" - Hermes Conrad"

  (def name "Chewbacca")
  (str "\"Ugglgglgllglglll\" - " name)
  )

;; Maps
(comment
  ;; empty map
  {}
  {:first-name "Charlie"
   :last-namee "McFishwich"}
  {"string-key" +}
  )
