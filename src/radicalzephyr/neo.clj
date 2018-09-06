(ns radicalzephyr.neo
  "Example tasks showing various approaches."
  {:boot/export-tasks true}
  (:require [boot.core :as boot :refer [deftask]]
            [boot.util :as util]
            [boot.pod :as pod]
            [clojure.tools.namespace.dir :as dir]
            [clojure.tools.namespace.move :as move]
            [clojure.tools.namespace.track :as track]))

(defn prefixed-ns [prefix ns]
  (symbol (str prefix "." ns)))

(deftask source-deps
  "I'm a pre-wrap task."
  [p prefix PRE sym "The string to prefix vendored nses with."]
  (let [version "0.1.0"
        prefix (or prefix (str "neo-" version))
        tgt (boot/tmp-dir!)
        vendor? #(= "source" (:scope (util/dep-as-map %)))
        jars (-> (boot/get-env)
                 (update-in [:dependencies] (partial filter vendor?))
                 (pod/resolve-dependency-jars true))
        jars (remove #(.endsWith (.getName %) ".pom") jars)]
    (doseq [jar jars]
      (pod/unpack-jar jar tgt))
    (let [tracker (-> (track/tracker)
                      (dir/scan tgt))
          nses (:clojure.tools.namespace.track/unload tracker)]
      (doseq [ns nses]
        (let [new-ns (prefixed-ns prefix ns)]
          (move/move-ns ns new-ns tgt [tgt]))))
    (boot/with-pre-wrap [fs]
      (-> fs
          (boot/add-resource tgt)
          boot/commit!))))
