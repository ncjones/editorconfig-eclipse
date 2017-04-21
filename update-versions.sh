#!/bin/bash

set -e

if [ -z "$1" ]; then
  echo "usage: $(basename "$0") <new version>"
  exit 1
fi

cd "$(dirname "$0")"

new_version=$1
version_pattern="[0-9]+\.[0-9]+\.[0-9]+"

mvn \
  -Dtycho.mode=maven \
  -DnewVersion="$new_version-SNAPSHOT" \
  org.eclipse.tycho:tycho-versions-plugin:set-version

find . \
  -name 'feature.xml' \
  -exec sed -i.bak -E "s/version=\"${version_pattern}\"/version=\"${new_version}\"/" '{}' ';'

find ./editorconfig-eclipse-functional-test \
  -name pom.xml \
  -exec sed -i.bak -E "s/${version_pattern}-SNAPSHOT/${new_version}-SNAPSHOT/" '{}' ';'

find ./editorconfig-eclipse-functional-test \
  -name MANIFEST.MF \
  -exec sed -i.bak -E "s/${version_pattern}.qualifier/${new_version}.qualifier/" '{}' ';'

git add .
git commit -m "Update version to $new_version"
