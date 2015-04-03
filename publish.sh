#!/bin/sh
base_dir=$(cd $(dirname $0) && pwd)
site_dir=$base_dir/site
p2_repo_dir=$base_dir/editorconfig-eclipse-p2/target/repository
rm -rf $site_dir
mkdir $site_dir
if [ ! -d $p2_repo_dir ]; then
  echo ERROR: $p2_repo_dir does not exist. Run '`mvn install`'.
  exit 1
fi
cp -r $p2_repo_dir $site_dir
cd $site_dir
git init
git add .
git commit -m 'add p2 repo'
git remote add origin git@github.com:editorconfig-eclipse/editorconfig-eclipse.github.io.git
git push -f origin master
