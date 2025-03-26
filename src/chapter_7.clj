(ns chapter-7)

;; Clojure Alchemy: Reading, Evaluation, and Macros
(comment
  (defmacro backwards
    [form]
    (reverse form))

  (backwards (" backwards" " am" "I" str))
  )

;; An Overview of Clojure's Evaluation Model
(comment
  (def addition-list (list + 1 2))
  addition-list
  (eval addition-list)
  (concat addition-list [10])
  (eval (concat addition-list [10]))
  (list 'def 'luck-number (concat addition-list [10]))
  (eval (list 'def 'luck-number (concat addition-list [10])))
  luck-number
  )

;; The Reader
(comment
  (str "To understand what recursion is," " you must first understand recursion.")
  (read-string "(+ 1 2)")
  (list? (read-string "(+ 1 2)"))
  (conj (read-string "(+ 1 2)") :zagglewag)
  (eval (read-string "(+ 1 2)"))
  (#(+ 1 %) 3)
  (read-string "#(+ 1 %)")
  )

;; Reader Macros
