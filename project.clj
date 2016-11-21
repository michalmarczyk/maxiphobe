(defproject maxiphobe "0.0.2-SNAPSHOT"
  :description "Meldable priority queues in Clojure"
  :url "https://github.com/michalmarczyk/maxiphobe"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :source-paths ["src/main/clojure"]
  :java-source-paths ["src/main/java"]
  :aliases {"test-all" ["with-profile" "dev:dev,1.9" "do" "clean," "test"]}
  :profiles {:dev {:dependencies [[org.clojure/test.check "0.9.0"]
                                  [collection-check "0.1.6"]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0-alpha14"]]}})
