(defproject cljbot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.9.1"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring-server "0.3.1"]
                 [ring/ring-json "0.3.1"]
                 [clojail "1.0.6"]]
  :main cljbot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :ring {:handler cljbot.core/handler})
