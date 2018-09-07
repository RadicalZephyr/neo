(def project 'radicalzephyr/neo)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [boot/core "RELEASE" :scope "test"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
                            [org.clojure/tools.namespace "0.2.11" :scope "source"]])

(require '[adzerk.boot-test :refer [test]])

(task-options!
 pom {:project     project
      :version     version
      :description "A Boot task to include dependencies as source code."
      :url         "https://github.com/RadicalZephyr/neo"
      :scm         {:url "https://github.com/RadicalZephyr/neo"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))
