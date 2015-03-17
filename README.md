EditorConfig Eclipse
====================

[EditorConfig](https://editorconfig.org/) plugin for the Eclipse IDE.


Building
--------

Building requires Java 1.6 and Maven 3.x.

To build, run:

```sh
mvn install
```

Note that the build depends on the editorconfig core source via a Git
submodule. To initialize the submodule, run:

```sh
git submodule init && \
git submodule update
```


Installing
----------

To install the built plugin into Eclipse, copy it to your Eclipse dropins dir
and restart Eclipse:

```sh
cp target/*.jar $ECLIPSE_HOME/dropins/
```


License
-------

Licensed under the Apache License, Version 2.0.

