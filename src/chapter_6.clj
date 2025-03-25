(ns chapter-6)

;; Your Project as a Library
(comment
  (ns-name *ns*)

  inc
  'inc
  (map inc [1 2])
  '(map inc [1 2]))

;; Storing Objects With def
(comment
  (def great-books ["East of Eden" "The Glass Bead Game"])
  great-books

  (ns-interns *ns*)
  (get (ns-interns *ns*) 'great-books)

  (ns-map *ns*)

  (deref #'chapter-6/great-books)
  great-books

  (def great-books ["The Power of Bees" "Journey of Upstairs"])
  great-books

  (ns-interns *ns*))

;; Creating And Switching to Namespaces
(comment
  (create-ns 'cheese.taxonomy)
  (ns-name (create-ns 'cheese.taxonomy))
  (in-ns 'cheese.analysis)
  (def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
  (in-ns 'cheese.taxonomy)
  cheddars
  cheese.analysis/cheddars
  ;; refer
  (in-ns 'cheese.taxonomy)
  (def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
  (def bries ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"])
  (in-ns 'cheese.analysis)
  (clojure.core/refer 'cheese.taxonomy)
  bries
  (clojure.core/get (clojure.core/ns-map clojure.core/*ns*) 'bries)
  (clojure.core/get (clojure.core/ns-map clojure.core/*ns*) 'cheddars)
  (clojure.core/refer 'cheese.taxonomy :only ['bries])
  bries
  cheddars
  (clojure.core/refer 'cheese.taxonomy :exclude ['bries])
  bries
  cheddars
  (clojure.core/refer 'cheese.taxonomy :rename {'bries 'yummy-bries})
  bries
  ; yummy-bries
  (in-ns 'cheese.analysis)
  (defn- private-function
    "Just an example function that does nothing"
    [])
  (in-ns 'cheese.taxonomy)
  (clojure.core/refer-clojure)
  (cheese.analysis/private-function)
  (refer 'cheese.analysis :only ['private-function])
  ;; alias
  (clojure.core/alias 'taxonomy 'cheese.taxonomy)
  taxonomy/bries
  )

;; Real Project Organization

;; The Relationship Between File Paths and Namespaces Names
;; lein new app the-divine-cheese-code

;; Requiring and Using Namespaces

;; The ns Macro
