EditorConfig Eclipse
====================

[EditorConfig](https://editorconfig.org/) plugin for the Eclipse IDE.


Installation
------------

Install from [Ecplipse Marketplace][marketplace] or using Eclipse update site:

    https://editorconfig-eclipse.github.io/repository


Feature Support
---------------

| Feature                    | Support | Notes                                       |
|----------------------------|---------|---------------------------------------------|
| `indent_style`             | Yes     | tested with Java, XML, Ant and text editors |
| `indent_size`              | Yes     | tested with Java, XML, Ant and text editors |
| `tab_width`                | No      |                                             |
| `end_of_line`              | No      | applies to files created after similar file opened |
| `charset`                  | Yes     | untested                                    |
| `trim_trailing_whitespace` | No      |                                             |
| `insert_final_newline`     | No      |                                             |


Compatibility
-------------

Eclipse Mars and Luna are supported.


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


Testing
-------

Functional tests, implemented with JUnit and SwtBot, are located in the
`editorconfig-eclipse-functional-test` directory. To run the tests:

```sh
mvn -f editorconfig-eclipse-functional-test/pom.xml integration-test
```


Installing Locally
------------------

Install via the local update site: `editorconfig-eclipse-p2/target/repository`.


License
-------

Licensed under the Apache License, Version 2.0.


[marketplace]: https://marketplace.eclipse.org/node/2506426
