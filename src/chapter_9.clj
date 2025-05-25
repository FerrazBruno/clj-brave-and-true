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
    (println "This will be printing before the result")
    (println "The result is: " @result)
    (println "It will be at least 3 seconds before I print"))

  (deref (future (Thread/sleep 1000) 0) 10 5)
  (realized? (future (Thread/sleep 5000)))
  (let [f (future)]
    @f
    (realized? f)))

;; Delays
(comment
  (def jackson-5-delay
    (delay (let [message "Just call my name and I'll be there"]
             (println "First deref: " message)
             message)))
  (force jackson-5-delay)
  @jackson-5-delay

  (def gimli-headshots ["serious.jpg" "fun.jpg" "playful.jpg"])
  (defn email-user [email-address]
    (println "Sending headshot notification to" email-address))
  (defn upload-document 
    "Needs to be implemented"
    [headshot]
    true)
  (let [notify (delay (email-user "and-my-axe@gmail.com"))]
    (doseq [headshot gimli-headshots]
      (future (upload-document headshot)
              (force notify)))))

;; Promises
(comment
  (def my-promise (promise))
  (deliver my-promise (+ 1 2))
  @my-promise

  (def yak-butter-international
    {:store "Yak Butter International"
     :price 90
     :smoothness 90})
  (def butter-than-nothing
    {:store "Butter Then Nothing"
     :price 150
     :smoothness 83})
  ;; This is the butter that meets our requirements
  (def baby-got-yak
    {:store "Baby Got Yak"
     :price 94
     :smoothness 99})
  
  (defn mock-api-call [result]
    (Thread/sleep 1000)
    result)

  (defn satisfactory? 
    "If the butter meets our criteria, return the butter, else return false"
    [butter]
    (and (<= (:price butter) 100)
         (>= (:smoothness butter) 97)
         butter))

  (time (some (comp satisfactory? mock-api-call)
              [yak-butter-international butter-than-nothing baby-got-yak]))

  (time
    (let [butter-promise (promise)]
      (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
        (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
                  (deliver butter-promise satisfactory-butter))))
      (println "And the winner is:" @butter-promise)))

  (let [p (promise)]
    (deref p 100 "time out"))

  (let [ferengi-wisdom-promise (promise)]
    (future (println "Here's some Ferengi wisdom:" @ferengi-wisdom-promise))
    (Thread/sleep 100)
    (deliver ferengi-wisdom-promise "Whisper your way to success.")))

;; Rolling Your Own Queue
(comment
  (defmacro wait
    "Sleep `timeout` seconds before evaluating body"
    [timeout & body]
    `(do (Thread/sleep ~timeout) ~@body))

  (let [saying3 (promise)]
    (future (deliver saying3 (wait 100 "Cheerio!")))
    @(let [saying2 (promise)]
       (future (deliver saying2 (wait 400 "Pip pip!")))
       @(let [saying1 (promise)]
          (future (deliver saying1 (wait 200 "'Ello, gov'na!")))
          (println @saying1)
          saying1)
       (println @saying2)
       saying2)
    (println @saying3)
    saying3)

  (defmacro enqueue
    ([q concurrent-promise-name concurrent serialized]
     `(let [~concurrent-promise-name (promise)]
        (future (deliver ~concurrent-promise-name ~concurrent))
        (deref ~q)
        ~serialized
        ~concurrent-promise-name))
    ([concurrent-promise-name concurrent serialized]
     `(enqueue (future) ~concurrent-promise-name ~concurrent ~serialized)))

  (time @(-> (enqueue saying (wait 200 "'Ello, gov'na!") (println @saying))
             (enqueue saying (wait 400 "Pip pip!") (println @saying))
             (enqueue saying (wait 100 "Cheerio!") (println @saying)))))

;; Exercises
(comment
  ;; 1.
  (defn search-html [input]
    (let [google "https://www.google.com.br/search?q="
          bing "https://www.bing.com/search?q="
          search-fn (fn [eng inp] (slurp (str eng inp)))]
      (let [result (promise)
            task (fn [engine]
                   (future
                     (try
                       (let [html (search-fn engine input)]
                         (deliver result html))
                       (catch Exception e
                         nil))))]
        (task google)
        (task bing)
        @result)))

  (search-html "clojure")

  ;; 2.
  (defn search-html-2 [engine input]
    (let [search-fn (fn [eng inp] (slurp (str eng inp)))]
      (let [result (promise)
            task (fn [engine]
                   (future
                     (try
                       (let [html (search-fn engine input)]
                         (deliver result html))
                       (catch Exception e
                         nil))))]
        (task engine)
        @result)))

  (search-html-2 "https://www.google.com.br/search?q=" "clojure")
  (search-html-2 "https://www.bing.com/search?q=" "clojure")
  
  ;; 3.
  )
