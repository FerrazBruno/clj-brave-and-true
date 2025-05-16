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
  (defmacro report [to-try]
    `(if ~to-try
       (println (quote ~to-try) "was successful:" ~to-try)
       (println (quote ~to-try) "was not successful:" ~to-try)))
  (report (do (Thread/sleep 1000) (+ 1 1)))
  (defmacro report [to-try]
    `(let [result# ~to-try]
       (if ~to-try
         (println (quote ~to-try) "was successful:" result#)
         (println (quote ~to-try) "was not successful:" result#))))
  (report (do (Thread/sleep 1000) (+ 1 1))))

;; Macros All the Way Down
(comment
  (defmacro report [to-try]
    `(let [result# ~to-try]
       (if ~to-try
         (println (quote ~to-try) "was successful:" result#)
         (println (quote ~to-try) "was not successful:" result#))))
  (report (= 1 1))
  (report (= 1 2))
  (doseq [code ['(= 1 1) '(= 1 2)]]
    (report code))
  (defmacro doseq-macro [macroname & args]
    `(do
       ~@(map (fn [arg] (list macroname arg)) args)))
  (doseq-macro report (= 1 1) (= 1 2)))

;; Brews for the Brave and True
(comment
  ;; Validation Functions
  (def order-details
    {:name "Mitchard Blimons"
     :email "mitchard.blimonsgmail.com"})
  order-details
  (def order-details-validation
    {:name  ["Please enter a name" not-empty]
     :email ["Please enter an email address" not-empty

             "Your email address doesn't look like an email address"
             #(or (empty? %) (re-seq #"@" %))]})
  (defn error-message-for
    "Return a seq of error messages"
    [to-validate message-validator-pairs]
    (map first (filter #(not ((second %) to-validate))
                       (partition 2 message-validator-pairs))))
  (partition 2 (:email order-details-validation))
  (error-message-for "abc.com" (:email order-details-validation))
  (defn validate
    "Returns a map with a vector of errors for each key"
    [to-validade validations]
    (reduce (fn [errors validations]
              (let [[fieldname validation-check-groups] validations
                    value (fieldname to-validade)
                    error-messages
                    (error-message-for value validation-check-groups)]
                (if (empty? error-messages)
                  errors
                  (assoc errors fieldname error-messages))))
            {}
            validations))
  (validate order-details order-details-validation)
  ;; if-valid
  (let [errors (validate order-details order-details-validation)]
    (if (empty? errors)
      (println :success)
      (println :failure errors)))
  (defn if-valid [record validations success-code failure-code]
    (let [errors (validate record validations)]
      (if (empty? errors)
        success-code
        failure-code)))
  (defmacro if-valid [to-validate validations errors-name & then-else]
    `(let [~errors-name (validate ~to-validate ~validations)]
       (if (empty? ~errors-name)
         ~@then-else)))
  (macroexpand
   '(if-valid order-details order-details-validation my-errors-name
              (println :success)
              (println :failure))))

;; Exercises
(comment
  ;; 1.
  (defmacro when-valid [to-validate validations & body]
    `(when (~validations ~to-validate)
       ~@body))
  (defn valid? [x]
    (= (:status x) :ok))
  (def my-data {:status :ok})
  (when-valid my-data valid?
              (println :success)
              (println :failure))
  ;; 2.
  (defmacro my-or
    ([] nil)
    ([x] x)
    ([x & next]
     `(let [or# ~x]
        (if or# or# (or ~@next)))))
  ;; 3.
  (defmacro defattrs [& pairs]
    (when (odd? (count pairs))
      (throw (IllegalArgumentException. "defattrs requires an even number of arguments")))
    `(do
       ~@(for [[fname attr] (partition 2 pairs)]
           `(defn ~fname [character#]
              (~attr character#)))))
  (defattrs
    c-int :intelligence
    c-str :strength
    c-dex :dexterity)
  (def character {:intelligence 12
                  :strength 15
                  :dexterity 10})
  (c-int character)
  (c-str character)
  (c-dex character))
