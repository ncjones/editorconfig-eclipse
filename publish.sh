#!/bin/bash

set -e

function fatal() {
  echo "$1"
  exit 1
}

version_pattern="[0-9]+\.[0-9]+\.[0-9]+"

function get_version() {
  grep 'version="[^"]*qualifier"' editorconfig-eclipse-feature/feature.xml \
    | head -1 \
    | sed -r "s/.*(${version_pattern}).*/\1/" \
    | grep -P "^${version_pattern}$"
}

version="$(get_version)" || fatal "bad version"

cd "$(dirname "$0")"
site_src_dir=site/src
site_build_dir=site/target
p2_repo_dir=editorconfig-eclipse-p2/target/repository
rm -rf "$site_build_dir"
mkdir "$site_build_dir"
if [[ ! -d "$p2_repo_dir" ]]; then
  echo "ERROR: $p2_repo_dir does not exist. Run 'mvn install'."
  exit 1
fi
cp -r "$p2_repo_dir" "$site_build_dir"
cp -r "$site_src_dir"/* "$site_build_dir"
cd "$site_build_dir"
git init
git add .
git commit -m 'add p2 repo'
git tag "$version"
git remote add origin git@github.com:editorconfig-eclipse/editorconfig-eclipse.github.io.git
git push origin "$version"
git push -f origin master
