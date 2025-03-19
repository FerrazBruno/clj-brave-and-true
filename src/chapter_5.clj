(ns chapter-5)

;; Pure Functions Are Referentially Transparent
(comment
  (+ 1 3)
  (defn wisdom
    [words]
    (str words ", Daniel-san"))
  (wisdom "Always bath on fridays")
  (defn year-end-evaluation
    []
    (if (> (rand) 0.5)
      "You get a raise!"
      "Better luck next year!"))
  (year-end-evaluation)
  (defn analyze-file
    [filename]
    (analysis (slurp filename)))
  (defn analysis
    [text]
    (str "Character count: " (count text)))
  (def great-baby-name "Rosanthony")
  (let [great-baby-name "Bloodthunder"]
    great-baby-name)
  great-baby-name
  (defn sum
    ([vals] (sum vals 0))
    ([vals accumulating-total]
     (if (empty? vals)
       accumulating-total
       (sum (rest vals) (+ (first vals) accumulating-total)))))
  (sum [1 2 3])
  (defn sum
    ([vals] (sum vals 0))
    ([vals accumulating-total]
     (if (empty? vals)
       accumulating-total
       (recur (rest vals) (+ (first vals) accumulating-total)))))
  )

;; Function Composition Instead of Attribute Mutation
(comment
  (defn clean [text]
    (clojure.string/replace
     (clojure.string/trim text)
     #"lol" "LOL"))
  (clean "My boa constrictor is so sassy lol!    ")
  )

;; Cool Things To Do With Pure Functions
(comment
  ;; comp
  ((comp inc *) 2 3)
  (def character
    {:name "Smooches McCutes"
     :attributes {:intelligence 10
                  :strength 4
                  :dexterity 5}})
  (def c-int (comp :intelligence :attributes))
  (def c-str (comp :strength :attributes))
  (def c-dex (comp :dexterity :attributes))
  (c-int character)
  (c-str character)
  (c-dex character)
  (defn spell-slots [char]
    (int (inc (/ (c-int char) 2))))
  (spell-slots character)
  (def spell-slots-comp (comp int inc #(/ % 2) c-int))
  (spell-slots-comp character)
  (defn two-comp [f g]
    (fn [& args]
      (f (apply g args))))
  (def two-comp-test (two-comp dec *))
  (two-comp-test 2 3)
  ;; memoize
  (defn sleepy-identity
    "Returns the given value after 1 second"
    [x]
    (Thread/sleep 1000)
    x)
  (sleepy-identity "Mr. Fantastico")
  (sleepy-identity "Mr. Fantastico")
  (def memo-sleep-identity (memoize sleepy-identity))
  (memo-sleep-identity "Mr. Fantastico")
  (memo-sleep-identity "Mr. Fantastico")
  )

;; Exercises
(comment
  ;; 1.
  (defn attr [keyword-attr]
    (fn [arg]
      (-> arg :attributes keyword-attr)))
  (def c-attr (attr :intelligence))
  (c-attr character)
  ;; 2.
  (defn my-comp
    ([] identity)
    ([f1] f1)
    ([f1 f2]
     (fn
       ([] (f1 (f2)))
       ([x] (f1 (f2 x)))
       ([x y] (f1 (f2 x y)))
       ([x y z] (f1 (f2 x y z)))
       ([x y z & args]
        (f1 (apply f2 x y z args)))))
    ([f1 f2 & fns]
     (reduce my-comp (list* f1 f2 fns))))
  (def t (my-comp inc #(/ % 2) #(* % 4)))
  (t 12)
  ;; 3.
  (defn my-assoc-in [m [k & ks] v]
    (if ks
      (assoc m k (my-assoc-in (get m k) ks v))
      (assoc m k v)))
  (my-assoc-in {:a {:b {:c 1}}} [:a :b :c] 3)
  ;; 4.
  (def character
    {:name "Smooches McCutes"
     :attributes {:intelligence 10
                  :strength 4
                  :dexterity 5}})
  (update-in character [:attributes :strength] inc)
  ;; 5.
  (defn my-update-in
    [m ks f & args]
    (if (empty? ks)
      (apply f m args)
      (let [[k & rest-ks] ks]
        (assoc m k (apply my-update-in (get m k) rest-ks f args)))))
  (my-update-in character [:attributes :strength] dec)
  )
