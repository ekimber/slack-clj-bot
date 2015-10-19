(ns cljbot.core
  (:require [ring.util.response :refer :all]
            [ring.adapter.jetty :as jetty]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.json :refer [wrap-json-response]]
            [clojail.core :refer [sandbox]]
            [clojail.testers :refer [secure-tester]])
  (:gen-class)
  (:import (java.util Properties)))

(def sb (sandbox secure-tester))

(defn evaluate-clj [text]
  (try
    (str (sb (read-string text)))
    (catch Exception e (str "DUH: " (.getMessage e)))))


(defn handle-clj
  [req]
  (println req)
  (response {:text (evaluate-clj (-> req :params (get "text") (subs 4)))}))

(defroutes app
           (POST "/" [] handle-clj))

(def handler
  (-> app
      wrap-params
      wrap-json-response))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (jetty/run-jetty handler {:port 9090 :join? false}))
