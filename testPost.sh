#!/bin/bash

words=`cat 1k.txt`


echo save start time: $(date +"%T.%3N")
curl -H "Content-Type: application/x-www-form-urlencoded" \
	--request POST \
	--data "$words" \
         https://deepmatter.appspot.com/save?filename=bashtest4
echo save end time: $(date +"%T.%3N")

echo ********
echo retrieve start time: $(date +"%T.%3N")
curl -H "Content-Type: text/plain" \
	--request GET \
         https://deepmatter.appspot.com/retrieve?filename=bashtest4
echo retrieve end time: $(date +"%T.%3N")
echo ********
