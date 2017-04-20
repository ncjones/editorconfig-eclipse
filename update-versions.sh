#!/bin/bash

set -e

if [ -z "$1" ]; then
  echo "usage: $0 <new version>"
  exit 1
fi

new_version=$1
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion="$new_version-SNAPSHOT"
find -name 'feature.xml' -exec sed -i -r "s/version=\"[0-9]+\.[0-9]+\.[0-9]+\"/version=\"$new_version\"/" '{}' ';'
git add .
git commit -m "Update version to $new_version"
