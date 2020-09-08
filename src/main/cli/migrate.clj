(ns cli.migrate
  (:require
   [app.server-components.config :as svr]
   [app.model.database :refer [pool]]
   [migratus.core :as migratus]
   [mount.core :as mount]
   [clojure.pprint]))

(defn migratus-options []
  {:store                :database
   :migration-dir        "migrations/"
   :init-script          "init.sql"
   :init-in-transaction? true
   :migration-table-name "migrations"
   :db {:connection (.getConnection pool)}})

(defn help []
  (print
   "Usage:
  clj -m cli.migrate <command> [& args]

  Commands:
  init          - Initialize the db running init.sql
  migrate       - Perform any migrations that have not been run
  rollback      - Rollback the last migration applied
  up & <ids>    - Bring up migrations matching the ids
  down & <ids>  - Bring down migrations matching the ids
"))

(defn -main [cmd & args]
  (-> (mount/only
       #{#'app.server-components.config/config
         #'app.model.database/pool})
      (mount/start))
  (clojure.pprint/pprint pool)
  (let [fn (resolve (symbol (str "migratus.core/" cmd)))]
    (cond
      (= cmd "help") (help)
      (not fn)       (throw (ex-info "Migratus command not supported" {:cmd cmd}))
      :else          (apply fn (migratus-options) args))))
