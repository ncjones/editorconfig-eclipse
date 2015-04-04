#!/bin/sh
if [ -z "$1" ]; then
  echo "usage: $0 <new version>"
  exit 1
fi
new_version=$1
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$new_version-SNAPSHOT
find -name 'feature.xml' | xargs sed -i -r "s/version=\"[0-9]+\.[0-9]+\.[0-9]+\"/version=\"$new_version\"/"
