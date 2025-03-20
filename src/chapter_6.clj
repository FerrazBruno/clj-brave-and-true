(ns chapter-6)

;; Your Project as a Library
(comment
  (ns-name *ns*)

  inc
  'inc
  (map inc [1 2])
  '(map inc [1 2])
)

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

  (ns-interns *ns*)
  )

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

  )
