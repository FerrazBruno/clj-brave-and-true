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
  {:name {:first "John" :middle "Jacob" :last "Jingleheimerschmidt"}}
  (hash-map :a 1 :b 2)
  (get {:a 0 :b 1} :a)
  (get {:a 0 :b {:c "ho hum"}} :b)
  (get {:a 0 :b 1} :c)
  (get {:a 0 :b 1} :c "unicorns?")
  (get-in {:a 0 :b {:c "ho hum"}} [:b :c])
  )

;; Keywords
(comment
  :a
  :rumplestiltsken
  :34
  :_?
  (:a {:a 1 :b 2 :c 3})
  (:d {:a 1 :b 2 :c 3} "No gnome knows homes likes Noah knows")
  )

;; Vectors
(comment
  [3 2 1]
  (get [3 2 1] 0)
  (get ["a" {:name "Pugsley Winterbotton"} "c"] 1)
  (vector "creepy" "full" "moon")
  (conj [1 2 3] 4)
  )

;; Lists
(comment
  '(1 2 3 4)
  (nth '(1 2 3 4) 0)
  (nth '(:a :b :c) 2)
  (list 1 "two" {3 4})
  (conj '(1 2 3) 4)
  )

;; Sets
(comment
  #{"kurt vonnegut" 20 :icicle}
  (hash-set 1 1 2 2)
  (conj #{:a :b} :b)
  (set [3 3 3 4 4])
  (contains? #{:a :b} :a)
  (contains? #{:a :b} 3)
  (contains? #{nil} nil)
  (:a #{:a :b})
  (get #{:a :b} :a)
  (get #{:a nil} nil)
  (get #{:a :b} "kurt vonnegut")
  )

;; Simplicity
;; It is better to have 100 functions operate on one data structure than 10 functions on 10 data structures.
;; Alan Perlis

;; Functions
;; Calling Functions
(comment
  (+ 1 2 3 4)
  (* 1 2 3 4)
  (first [1 2 3 4])
  (or + -)
  ((or + -) 1 2 3)
  ((and (= 1 1) +) 1 2 3)
  ((first [+ 0]) 1 2 3)
  (inc 1.1)
  (map inc [0 1 2 3])
  )

;; Functions Calls, Macro Calls, and Special Forms
;; Special forms: They don't always evaluate all of their operands
;; "if" is an example
;; Can't use them as arguments to functions
;; Macros also can't be passed as arguments to functions
;; Defining Functions

(comment
  (defn too-enthusiastic
    "Return a cheer chat might be a bit too enthusiastic"
    [name]
    (str "OH. MY. GOD! " name " YOU ARE THE MOST DEFINITELY LIKE THE BEST "
         "MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"))

  (too-enthusiastic "Zelda")
  )

;; Parameters and Arity
(comment
  (defn no-params
    []
    "I take no parameters")
  (defn one-param
    [x]
    (str "I take one parameter: " x))
  (defn two-params
    [x y]
    (str "Two parameters! That's nothing! Pah! I will smoosh them "
         "together to spit you! " x y))
  ;; multiple-arity function
  (defn multi-arity
    ([first-arg second-arg third-arg]
     #_(do-things first-arg second-arg third-arg))
    ([first-arg second-arg]
     #_(do-things first-arg second-arg))
    ([first-arg]
     #_(do-things first-arg)))

  (defn x-chop
    "Describe the kind of chop you're inflicting on someone"
    ([name chop-type]
     (str "I " chop-type " chop " name "! Take that!"))
    ([name]
     (x-chop name "karate")))

  (x-chop "Kanye West" "slap")
  (x-chop "Kanye West")

  (defn weird-arity
    ([]
     "Destiny dressed you this morning, my friend, and now Fear is
       trying to pull off your pants. If you give up, if you give in,
       you're gonna end up naked with Fear just standing there laughing
       at your dangling unmentionables! - the Tick")
    ([number]
      (inc number)))

  (defn codger-communication
    [whippersnapper]
    (str "Get off my lawn, " whippersnapper "!!!"))

  (defn codger
    [& whippersnapper]
    (map codger-communication whippersnapper))

  (codger "Billy" "Anne-Marie" "The Incredible Bulk")

  (defn favorite-things
    [name & things]
    (str "Hi, " name ", here are my favorite things: " (clojure.string/join ", " things)))

  (favorite-things "Doreen" "gum" "shoes" "kara-te")
  )

;; Destructuring
(comment
  (defn my-first
    [[first-thing]]
    first-thing)

  (my-first ["oven" "bike" "war-axe"])


  (defn chooser
    [[first-choice second-choice & unimportant-choices]]
    (println (str "Your first choice is: " first-choice))
    (println (str "Your second choice is: " second-choice))
    (println (str "We're ignoring the rest of the your choices. "
                  "Here they are in case you need to crie over them: "
                  (clojure.string/join ", " unimportant-choices))))


  (chooser ["Marmalade" "Handsome Jack" "Pigpen" "Aquaman"])


  (defn announce-treasure-location
    [{lat :lat lng :lng}]
    (println (str "Treasure lat: " lat))
    (println (str "Treasure lng: " lng)))

  #_(announce-treasure-location {:lat 28.22 :lng 81.33})

  (defn announce-treasure-location
    [{:keys [lat lng]}]
    (println (str "Treasure lat: " lat))
    (println (str "Treasure lng: " lng)))

  (defn announce-treasure-location
    [{:keys [lat lng]} :as treasure-location]
    (println (str "Treasure lat: " lat))
    (println (str "Treasure lng: " lng)))
  )

;; Function Body
(comment
  (defn illustrative-function
    []
    (+ 1 304)
    30
    "joe")

  (illustrative-function)

  (defn number-comment
    [x]
    (if (> x 6)
      "Oh my gosh! What a big number!"
      "That number's OK, I guess"))

  (number-comment 5)
  (number-comment 7)
  )

;; Anonymous Functions
(comment
  (map (fn [name] (str "Hi, " name)) ["Darth Vader" "Mr. Magoo"])

  ((fn [x] (* x 3)) 8)

  (def my-special-multiplier (fn [x] (* x 3)))
  (my-special-multiplier 12)

  (#(* % 3) 8)

  (map #(str "Hi, " %) ["Darth Vader" "Mr. Magoo"])

  (#(str %1 " and " %2) "cornbread" "butter beans")

  (#(identity %&) 1 "blarg" :yip)
  )

;; Returning Functions
(comment
  (defn inc-maker
    "Create a custom incrementor"
    [inc-by]
    #(+ % inc-by))

  (def inc3 (inc-maker 3))
  (inc3 7)
  )

;; Pulling It All Together
(comment
  (def asym-hobbit-body-parts [{:name "head" :size 3}
                               {:name "left-eye" :size 1}
                               {:name "left-ear" :size 1}
                               {:name "mouth" :size 1}
                               {:name "nose" :size 1}
                               {:name "neck" :size 2}
                               {:name "left-shoulder" :size 3}
                               {:name "left-upper-arm" :size 3}
                               {:name "chest" :size 10}
                               {:name "back" :size 10}
                               {:name "left-forearm" :size 3}
                               {:name "abdomen" :size 6}
                               {:name "left-kidney" :size 1}
                               {:name "left-hand" :size 2}
                               {:name "left-knee" :size 2}
                               {:name "left-thigh" :size 4}
                               {:name "left-lower-leg" :size 3}
                               {:name "left-achilles" :size 1}
                               {:name "left-foot" :size 2}])

  (defn matching-part
    [part]
    {:name (clojure.string/replace (:name part) #"^left-" "right-")
     :size (:size part)})

  (defn symmetrize-body-parts
    "Expects a seq of maps that have a :name and :size"
    [asym-body-parts]
    (loop [remaining-asym-parts asym-body-parts
           final-body-parts []]
           (if (empty? remaining-asym-parts)
             final-body-parts
             (let [[part & remaining] remaining-asym-parts]
               (recur remaining
                      (into final-body-parts
                            (set [part (matching-part part)])))))))

  (symmetrize-body-parts asym-hobbit-body-parts)
  )

;; let
