(ns fwpd
  (:require [clojure.string :as st]))

(def filename "suspects.csv")

(slurp filename)

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(st/split % #",")
       (st/split string #"\n")))

(defn mapify
  "Returns a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row))) rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(comment
  (glitter-filter 3 (mapify (parse (slurp filename))))
  ;; Exercise chapter_4
  ;; 1.
  (map :name
       (glitter-filter 3 (mapify (parse (slurp filename)))))
  ;; 2.
  (defn append
    [record list-of-suspects]
    (conj (vec list-of-suspects) record))
  (append "Blade" '("Edward Cullen" "Carlisle Cullen"))
  ;; 3.
  (def validations {:check-name (fn [record] (contains? record :name))
                    :check-glitter-index (fn [record] (contains? record :glitter-index))})
  (defn validate
    [kws record]
    (and ((:check-name kws) record)
         ((:check-glitter-index kws) record)
         record))
  ;; 4.
  (defn map->csv
    [map-list]
    (st/join
     (map (fn [item]
            (str
             (:name item)
             ","
             (:glitter-index item)
             "\n"))
          map-list)))
  (map->csv
   (glitter-filter 3 (mapify (parse (slurp filename)))))
  )
