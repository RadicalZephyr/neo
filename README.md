# neo

A Boot task to include dependencies as source code.  Inspired and
heavily influenced by [mranderson].

[mranderson]: https://github.com/benedekfazekas/mranderson


## Usage

To use this in your project, add `[radicalzephyr.neo "0.1.0-SNAPSHOT"]` to your `:dependencies`
and then require the task:

``` clojure
(require '[radicalzephyr.neo :refer [source-deps]])
```

Mark dependencies that you want to include as source with `:scope
"source"`.

``` clojure
:dependencies [[org.clojure/tools.namespace "0.2.11" :scope "source"]]
```

Then, include the `source-deps` task in your jar creation task
pipeline. It should be included before the `jar` (or equivalent
packaging task).

If you provide a `prefix` argument, then all namespaces in all
dependencies marked as `:scope "source"` will be re-named (with
`clojure.tools.namespace.move/move-ns`) by adding the prefix as the
top-level elements in the namespace hierarchy. Like so:
`<prefix>.<original-ns>`. The default prefix is `neo-<neo-version>`.

Usages of the original ns will be replaced with the new prefixed
version throughout local source code and inside of all the moved
namespaces.


## License

Copyright © 2018 Geoff Shannon

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
