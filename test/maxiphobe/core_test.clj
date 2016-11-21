(ns maxiphobe.core-test
  (:use clojure.test)
  (:require [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [collection-check :as cc]
            [maxiphobe.core :as pq]))


(defspec collection-check 1000
  (prop/for-all [xs (gen/vector gen/int)]
    (let [h (pq/pq xs)
          s (vec (sort xs))]
      (try
        (cc/assert-equivalent-collections h s)
        true
        (catch AssertionError _
          false)))))


(defspec collection-check-by 1000
  (prop/for-all [xs (gen/vector gen/int)]
    (let [h (pq/pq-by > xs)
          s (vec (sort > xs))]
      (try
        (cc/assert-equivalent-collections h s)
        true
        (catch AssertionError _
          false)))))


(defspec collection-check-merge 250
  (prop/for-all [xss (gen/vector (gen/vector gen/int))]
    (let [h (apply pq/meld (pq/pqueue) (mapv pq/pq xss))
          s (vec (sort (apply concat xss)))]
      (try
        (cc/assert-equivalent-collections h s)
        true
        (catch AssertionError _
          false)))))


(defspec collection-check-merge-by 250
  (prop/for-all [xss (gen/vector (gen/vector gen/int))]
    (let [h (apply pq/meld (pq/pqueue-by >) (mapv #(pq/pq-by > %) xss))
          s (vec (sort > (apply concat xss)))]
      (try
        (cc/assert-equivalent-collections h s)
        true
        (catch AssertionError _
          false)))))
