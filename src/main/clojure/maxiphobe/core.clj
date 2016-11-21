(ns maxiphobe.core

  "Meldable priority queues in Clojure, based on OKasaki's maxiphobic heaps.

  See Okasaki, \"Alternatives to Two Classic Data Structures\"."

  {:author "Michał Marczyk"}

  (:import (java.util Comparator)
           (maxiphobe MaxiphobicHeap$EmptyMaxiphobicHeap MaxiphobicHeap)))


(set! *warn-on-reflection* true)


(defn pq
  "Returns a new priority queue containing the contents of coll."
  [coll]
  (into MaxiphobicHeap/EMPTY coll))


(defn pqueue
  "Returns a new priority queue with the supplied items."
  [& xs]
  (pq xs))


(defn pq-by
  "Returns a new priority queue containing the contents of coll, using the
  supplied comparator."
  [^Comparator comp coll]
  (into (MaxiphobicHeap$EmptyMaxiphobicHeap. comp) coll))


(defn pqueue-by
  "Returns a new priority queue with the supplied items, using the supplied
  comparator."
  [^Comparator comp & xs]
  (pq-by comp xs))


(defn meld
  "Returns a priority queue containing the contents of all of the supplied
  priority queues under the precondition that they all use the same comparator.
  (Throws if this is not the case.) nil is an acceptable input, treated as
  equivalent to an empty priority queue using the non-empty inputs' comparator.
  Returns nil in the nullary case, as well as if all the inputs are nil."
  ([]
   nil)
  ([pq]
   pq)
  ([pq1 pq2]
   (cond
     (nil? pq1) pq2
     (nil? pq2) pq1
     :else (MaxiphobicHeap/meld pq1 pq2)))
  ([pq1 pq2 & pqs]
   (meld (meld pq1 pq2) (apply meld pqs))))
