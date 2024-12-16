(ns chapter-1)


;; Building, Running, and The  REPL

;; Goals
;; Create a new Clojure project with Leiningen
;; Build the project to create an executable JAR file
;; Execute the JAR file
;; Execute code in a Clojure REPL


;; First Things First: What is Clojure?

;; Clojure was created by Rich Hickey
;; It's a Lisp dialect
;; It runs on the JVM by default
;; Clojure is a general-purpose programming language with an emphasis on functional programming
;; It's immutable
;; Clojure language and Clojure compiler are different things
;; The compiler is an executable JAR file, clojure.jar, which takes code written in the Clojure language and compiles it to JVM bytecode
;; JVM processes execute Java bytecode
;; Usually, the Java Compiler produces Java bytecode from Java source
;; JAR files are collections of Java bytecode
;; Java programs are usually distributed as JAR files
;; The Java program clojure.jar reads Clojure source code and produces Java bytecode
;; That Java bytecode is then executed by the same JVM process already running clojure.jar


;; Leiningen

;; Four tasks:

;; Creating a new Clojure project
;; Running the Clojure project
;; Building the Clojure project
;; Using the REPL


;; Creating a New Clojure Project

;; lein new app project_name
;; project.clj -> Configuration file for Leiningen
;; src/project_name/core.clj -> Where Clojure code is written
;; test/ -> Contains tests
;; resorces/ -> Where stores assets, like images


;; Running the Clojure Project

;; lein run -> Execute a program


;; Building the Clojure Project

;; lein uberjar -> Create a stand-alone file that everyone with Java installed can execute
;; This command creates the file target/uberjar/project-name-0.1.0-SNAPSHOT-standalone.jar
;; java -jar target/uberjar/project-name-0.1.0-SNAPSHOT-standalone.jar -> Makes Java execute it


;; Using the REPL

;; lein repl -> Start a REPL


;; Clojure Editors

;; Emacs -> The most popular editor among Clojurists
;; Sublime
;; Vscode
;; Nightcode
;; Intellij
