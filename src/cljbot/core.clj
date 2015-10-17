(ns cljbot.core
  (:require [ring.util.response :refer :all]
            [ring.adapter.jetty :as jetty]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.json :refer [wrap-json-response]]
            [clojail.core :refer [sandbox]]
            [clojail.testers :refer [secure-tester]])
  (:gen-class))

(def sb (sandbox secure-tester))

(defn evaluate-clj [text]
  (try
    (str (sb (read-string text)))
    (catch Exception e (str "DUH: " (.getMessage e)))))

(defroutes app
           (POST "/" {body :body} (response {:text (evaluate-clj (subs (slurp body) 4))})))

(def handler
  (-> app
      wrap-params
      wrap-json-response))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (jetty/run-jetty handler {:port 9090 :join? false}))
