#!/usr/bin/env bash

current_version="$1"
release_type="$2"
next_version=""

semver=( ${current_version//./ } )
if [[ $release_type = "patch" ]]; then
  next_version=${semver[0]}.${semver[1]}.$((${semver[2]}+1))
elif [[ $release_type = "minor" ]]; then
  next_version=${semver[0]}.$((${semver[1]}+1)).0
elif [[ $release_type = "major" ]]; then
 next_version=$((${semver[0]}+1)).0.0
else
  echo "Release type '$release_type' is not a valid option. Supported types: patch, minor, major." 1>&2
  exit 1 # Terminate and indicate error
fi

echo $next_version
