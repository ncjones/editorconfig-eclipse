#!/bin/sh
if [ -z "$1" ]; then
  echo "usage: $0 <new version>"
  exit 1
fi
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$1
