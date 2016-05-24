EditorConfig Eclipse
====================

[EditorConfig] plugin for the Eclipse IDE.

[![Build Status][build-status-img]][build-status-link]


Installation
------------

Install from [Eclipse Marketplace][marketplace] by dragging the following
install link to a running Eclipse workspace:

[![Install][install-img]][install-link]


Usage
-----

No additional configuration is required other than creating .editorconfig
files. The plugin applies the editorconfig settings for a file when opened in a
supported editor (currently Text, Java, XML and Ant editors).


How It Works
------------

Whenever an Eclipse editor is opened or gains focus, the .editorconfig settings
are obtained for that editor's file and applied globally to all supported
editor types. How the editorconfig is obtained for any file is described in the
[EditorConfig docs][EditorConfig]:

    When opening a file, EditorConfig plugins look for a file named
    .editorconfig in the directory of the opened file and in every parent
    directory. A search for .editorconfig files will stop if the root filepath
    is reached or an EditorConfig file with root=true is found.


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
mvn clean install
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


[build-status-img]: https://travis-ci.org/ncjones/editorconfig-eclipse.svg?branch=master
[build-status-link]: https://travis-ci.org/ncjones/editorconfig-eclipse
[install-img]: https://marketplace.eclipse.org/sites/all/themes/solstice/public/images/marketplace/btn-install.png
[install-link]: http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2506426
[marketplace]: https://marketplace.eclipse.org/node/2506426
[EditorConfig]: http://editorconfig.org/
