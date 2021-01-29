#!/bin/sh
# fetch branch main updates
git checkout main
git remote update
VAR_STATUS=$(git status | grep "Your branch is behind")
if [[ $VAR_STATUS != "" ]]; then
    say $VAR_STATUS  # say it loud!!!!
    osascript -e 'display alert "UPDATE!!!"'
fi