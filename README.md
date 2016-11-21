# maxiphobe

Meldable priority queues in Clojure, based on OKasaki's maxiphobic heaps.

See Okasaki, "Alternatives to Two Classic Data Structures".


## Usage

There is a single public namespace called `maxiphobe.core`:

```clojure
(require '[maxiphobe.core :as pq])
```

There are four factory functions:

```clojure
;; return a priority queue containing the contents of the input collection
(pq/pq [0 -1 3 4 2])
;= (-1 0 2 3 4)

;; as above, but with a custom comparator
(pq/pq-by > [0 -1 3 4 2])
;= (4 3 2 0 -1)

;; return a priority queue containing the given items
(pq/pqueue 0 -1 3 4 2)
;= (-1 0 2 3 4)

;; as above, but with a custom comparator
(pq/pqueue-by > 0 -1 3 4 2)
;= (4 3 2 0 -1)
```

Priority queues can be melded:

```clojure
(pq/meld (pq/pq [0 -1 3 4 2]) (pq/pq [8 -3 10 2 4 2]))
;= (-3 -1 0 2 2 2 3 4 4 8 10)
```

`peek` and `pop` can be used to retrieve and to remove the minimum element:

```clojure
(peek (pq/pq [0 -1 3 4 2]))
;= -1

(pop (pq/pq [0 -1 3 4 2]))
;= (0 2 3 4)
```

Items can be added using `conj`:

```clojure
(conj (pq/pq [0 -1 8 7]) 5)
;= (-1 0 5 7 8)
```

Additionally, priority queues are seqs and support `first` (equivalent to
`peek`) and `next` (equivalent to `pop`) directly.

Elements can be tied on priority (that is, they can compare equal), in which
case they will be returned in arbitrary order.


## Releases and dependency information

This is an experimental library.
[Alpha releases are available from Clojars](https://clojars.org/maxiphobe).
Follow the link above to discover the current release number.

[Leiningen](http://leiningen.org/) dependency information:

    [maxiphobe "${version}"]

[Maven](http://maven.apache.org/) dependency information:

    <dependency>
      <groupId>maxiphobe</groupId>
      <artifactId>maxiphobe</artifactId>
      <version>${version}</version>
    </dependency>

[Gradle](http://www.gradle.org/) dependency information:

    compile "maxiphobe:maxiphobe:${version}"


## Clojure code reuse

The implementation of the static class `MaxiphobicHeap.EmptyMaxiphobicHeap` is
adapted from the implementation of the analogous `PersistentList.EmptyList`
class in Clojure.

The Clojure source files containing the relevant code carry the following
copyright notice:

    Copyright (c) Rich Hickey. All rights reserved.
    The use and distribution terms for this software are covered by the
    Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
    which can be found in the file epl-v10.html at the root of this distribution.
    By using this software in any fashion, you are agreeing to be bound by
      the terms of this license.
    You must not remove this notice, or any other, from this software.


## Licence

Copyright © 2016 Michał Marczyk

Distributed under the Eclipse Public License version 1.0.
