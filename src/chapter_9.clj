(ns chapter-9)

;; The Sacred Art of Concurrent and Parallel Programming

;; Concurrency and Parallelism Concepts

;; Managing Multiple Tasks vs. Executing Tasks Simultaneously

;; Blocking and Asynchronous Tasks

;; Concurrent Programming and Parallel Programming

;; Clojure Implementation: JVM Threads

;; What's a Thread?

;; The Three Goblins: Reference Cells, Mutual Exclusion, and Dwarven Berserkers

;; Futures, Delays and Promises

;; Futures
(comment
  (future (Thread/sleep 4000)
          (println "I'll print after 4 seconds."))
  (println "I'll print immediately")

  (let [result (future (println "this prints once")
                       (+ 1 1))]
    (println "deref: " (deref result))
    (println "@: " @result))

  (let [result (future (Thread/sleep 3000)
                       (+ 1 1))]
    (println "The result is: " @result)
    (println "It will be at least 3 seconds before I print"))

  (deref (future (Thread/sleep 1000) 0) 10 5)
  (realized? (future (Thread/sleep 5000)))
  (let [f (future)]
    @f
    (realized? f)))

;; Delays
