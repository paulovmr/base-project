#!/bin/bash

die () {
    echo >&2 "$@"
    exit 1
}

[ "$#" -eq 1 ] || die "1 argument required, $# provided"

ROOT_DIRECTORY=$(pwd)

# Application files
cd $ROOT_DIRECTORY/implementation/
shopt -s globstar
rename -v "s/baseproject/$1/" **
find . -type f -exec sed -i "s,baseproject,$1,g" {} \;
