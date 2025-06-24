(ns chapter-12)

;; Working with the JVM

;; The JVM

;; Writing, Compiling, and Running a Java Program

;; Object-Oriented Programming in the World’s Tiniest Nutshell

;; Ahoy, World

;; Packages and Imports

;; JAR Files

;; clojure.jar

;; Clojure App JARs

;; Java Interop

;; Interop Syntax
(comment
  (.toUpperCase "By Bluebeard's bananas!")
  (.indexOf "Let's synergize our bleeding egdes" "y")
  (java.lang.Math/abs -3)
  java.lang.Math/PI

  (macroexpand-1 '(.toUpperCase "By Bluebeard's bananas!"))
  (macroexpand-1 '(.indexOf "Let's synergize our bleeding egdes" "y"))
  (macroexpand-1 '(Math/abs -3))
  ;; (. object-expr-or-classname-symbol method-or-member-symbol optional-args*)

  ;; Creating and Mutating Objects
  (new String)
  (String.)
  (String. "To Davey Jones's Locker with ye hardies")

  (java.util.Stack.)
  (let [stack (java.util.Stack.)]
    (.push stack "Latest episode of Game Of Thrones, no!")
    stack)
  (let [stack (java.util.Stack.)]
    (.push stack "Latest episode of Game Of Thrones, no!")
    (first stack))
  (doto (java.util.Stack.)
    (.push "Latest episode of Game Of Thrones, no!")
    (.push "Whoops, I meant 'land, no!"))
  (macroexpand-1
   '(doto (java.util.Stack.)
      (.push "Latest episode of Game Of Thrones, no!")
      (.push "Whoops, I meant 'land, no!")))

  ;; Importing
  (import java.util.Stack)
  (Stack.)
  ;; (import [package.name1 ClassName1 ClassName2]
  ;;         [package.name2 ClassName3 ClassName4])
  (import [java.util Date Stack]
          [java.net Proxy URI])
  (Date.))

;; Commonly Used Java Classes
(comment
  ;; The System Class
  (System/getenv)
  (System/getProperty "user.dir")
  (System/getProperty "java.version")

  ;; The Date Class
  (java.util.Date.))

;; Files and Input/Output
(comment
  (let [file (java.io.File. "/")]
    (println (.exists file))
    (println (.canWrite file))
    (println (.getPath file)))
  (spit "/tmp/hercules-todo-list"
        "- kill dat lion dav
         - chop up what nasty multi-headed snake thing")
  (slurp "/tmp/hercules-todo-list")
  (let [s (java.io.StringWriter.)]
    (spit s "- capture cerynian hind like for real")
    (.toString s))
  (let [s (java.io.StringReader. "- get erymathian pig what with the tusks")]
    (slurp s))
  (with-open [todo-list-rdr (clojure.java.io/reader "/tmp/hercules-todo-list")]
    (println (first (line-seq todo-list-rdr)))))
