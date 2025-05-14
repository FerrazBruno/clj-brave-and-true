(ns chapter-7)

;; Clojure Alchemy: Reading, Evaluation, and Macros
(comment
  (defmacro backwards
    [form]
    (reverse form))

  (backwards (" backwards" " am" "I" str)))

;; An Overview of Clojure's Evaluation Model
(comment
  (def addition-list (list + 1 2))
  addition-list
  (eval addition-list)
  (concat addition-list [10])
  (eval (concat addition-list [10]))
  (list 'def 'luck-number (concat addition-list [10]))
  (eval (list 'def 'luck-number (concat addition-list [10])))
  luck-number)

;; The Reader
(comment
  (str "To understand what recursion is,"
       " you must first understand recursion.")
  (read-string "(+ 1 2)")
  (list? (read-string "(+ 1 2)"))
  (conj (read-string "(+ 1 2)") :zagglewag)
  (eval (read-string "(+ 1 2)"))
  (#(+ 1 %) 3)
  (read-string "#(+ 1 %)"))

;; Reader Macros
(comment
  (read-string "'(a b c)")
  (read-string "@var")
  (read-string "; ignore!\n(+ 1 2)")
  ;; Those Things Evaluate to Themselves
  true
  false
  {}
  :huzzah
  ())

;; Symbols
(comment
  (if true :a :b)
  (let [x 5]
    (+ x 3))
  (def x 15)
  (+ x 3)
  (let [x 5]
    (+ x 3))
  (let [x 5]
    (let [x 6]
      (+ x 3)))
  (defn exclaim
    [exclamation]
    (str exclamation "!"))
  (exclaim "Hadoken")

  (map inc [1 2 3])

  (read-string ("+"))
  (type (read-string "+"))
  (list (read-string "+") 1 2)
  (eval (list (read-string "+") 1 2)))

;; Lists
(comment
  (eval (read-string "()"))
  ;; Function Calls
  (+ 1 2)
  (+ (+ 2 3))
  ;; Special Forms
  (if true 1 2)
  '(1 2 3)
  (quote (1 2 3)))

;; Macros
(comment
  (read-string "(1 + 1)")
  (eval (read-string "(1 + 1)"))
  (eval (let [infix (read-string "(1 + 1)")]
          (list (second infix) (first infix) (last infix))))
  (butlast [1 2 3])
  (defmacro ignore-last-operand
    [function-call]
    (butlast function-call))
  (ignore-last-operand (+ 1 2 10))
  (ignore-last-operand (+ 1 2 (println "look at me!")))
  (macroexpand '(ignore-last-operand (+ 1 2 10)))
  (macroexpand '(ignore-last-operand (+ 1 2 (println "look at me!"))))
  (defmacro infix [infixed]
    (list (second infixed)
          (first infixed)
          (last infixed)))
  (infix (1 + 2)))

;; Syntatic Abstraction and the -> Macro
(comment
  (defn read-resource
    "Read a resource into a string"
    [path]
    (read-string (slurp (clojure.java.io/resource path))))
  (defn read-resource [path]
    (-> path
        clojure.java.io/resource
        slurp
        read-string)))

;; Exercises
(comment
  ;; 1.
  (eval (list println "Bruno" "Matrix"))
  (eval (quote (println "Bruno" "Matrix")))
  (eval (read-string "(println \"Bruno\" \"Matrix\")"))

  ;; 2.
  (defmacro infix [infixed]
    (let [multiply-sign (nth infixed 3)
          plus-sign (nth infixed 1)
          minus-sign (nth infixed 5)]
      (list minus-sign (list plus-sign
                             (nth infixed 0)
                             (list multiply-sign
                                   (nth infixed 2)
                                   (nth infixed 4)))
            (nth infixed 6))))
  (infix (1 + 3 * 4 - 5)))

