(ns chapter-8)

;; Writing Macros

;; Macros Are Essential

;; Anatomy of a Macro
(comment
  (defmacro infix
    "Use this macro when you pine for the notation of your childhood"
    [infixed]
    (list (second infixed) (first infixed) (last infixed)))
  (infix (1 + 1))
  (macroexpand '(infix (1 + 1)))

  (defmacro infix-2 [[operand1 op operand2]]
    (list op operand1 operand2))
  (infix-2 (1 + 1)))

;; Building Lists for Evaluations
(comment
  ;; Distinguishing Symbols and Values
  (defmacro my-print-whoopsie [expression]
    (list let [result expression]
          (list println result)
          result))
  (defmacro my-print-whoopsie [expression]
    (list 'let ['result expression]
          (list 'println 'result)
          'result))

  ;; Simple Quoting
  (+ 1 2)
  (quote (+ 1 2))
  +
  (quote +)
  sweating-to-the-oldies
  (quote sweating-to-the-oldies)
  '(+ 1 2)
  'dr-jekyll-and-richard-simmons
  (macroexpand '(when (the-cows-comw :home)
                  (call me :pappy)
                  (slap me :silly)))
  (defmacro unless
    "Inverted if"
    [test & branches]
    (conj (reverse branches) test 'if))
  (macroexpand '(unless (done-been slapped? me)
                        (slap me :silly)
                        (say "I reckon that'll learn me")))
  (unless true 1 2)

  ;; Syntax Quoting
  '+
  'clojure.core/+
  `+
  '(+ 1 2)
  `(+ 1 2)
  `(+ 1 ~(inc 1))
  `(+ 1 (inc 1))
  (list '+ 1 (inc 1))
  `(+ 1 ~(inc 1)))

;; Using Syntax Quoting in a Macro
(comment
  (defmacro code-critic
    "Phrases are courtesy Hermes Conrad from futurama"
    [bad good]
    (list 'do
          (list 'println
                "Great squid of Madrid, this is bad code:"
                (list 'quote bad))
          (list 'println
                "Sweet gorilla of Manila, this is good code:"
                (list 'quote good))))
  (code-critic (1 + 1) (+ 1 1))

  (defmacro code-critic [bad good]
    `(do
       (println "Great squid of Madrid, this is bad code:" (quote ~bad))
       (println "Sweet gorilla of Manila, this is good code:" (quote ~good))))
  (code-critic (1 + 1) (+ 1 1)))

;; Refactoring a Macro and Unquote Splicing
(comment
  (defn criticize-code [criticism code]
    `(println ~criticism (quote code)))
  (defmacro code-critic [bad good]
    `(do ~(criticize-code
           "Cursed bacteria of Liberia, this is bad code:"
           bad)
         ~(criticize-code
           "Sweet sacred boa of Western and Eastern Samos,this is good code:"
           good)))
  (code-critic (1 + 1) (+ 1 1))
  (defmacro code-critic [bad good]
    `(do ~(map #(apply criticize-code %)
               [["Great squid of Madrid, this is bad code:" bad]
                ["Sweet gorilla of Manila, this is good code:" good]])))
  (code-critic (1 + 1) (+ 1 1))
  `(+ ~(list 1 2 3))
  `(+ ~@(list 1 2 3))
  (defmacro code-critic [bad good]
    `(do ~@(map #(apply criticize-code %)
                [["Great squid of Madrid, this is bad code:" bad]
                 ["Sweet gorilla of Manila, this is good code:" good]])))
  (code-critic (1 + 1) (+ 1 1)))

;; Things to Watch Out For
(comment
  ;; Variable Capture
  (def message "Good job!")
  (defmacro with-mischief [& stuff-to-do]
    (concat (list 'let ['message "Oh, big deal!"]) stuff-to-do))
  (with-mischief
    (println "Here's how I feel about that thing you did:" message))
  (defmacro with-mischief [& stuff-to-do]
    `(let [message "Oh, big deal!"]
       ~@stuff-to-do))
  (with-mischief
    (println "Here's how I feel about that thing you did:" message))
  (gensym)
  (gensym)
  (gensym 'message)
  (defmacro with-mischief [& stuff-to-do]
    (let [macro-message (gensym 'message)]
      `(let [~macro-message "Oh, big deals!"]
         ~@stuff-to-do
         (println "I still need to say: " ~macro-message))))
  (with-mischief
    (println "Here's how I feel about that thing you did:" message))
  `(blarg# blarg#)
  `(let [name# "Larry Potter"] name#)

  ;; Double Evaluation
  )
