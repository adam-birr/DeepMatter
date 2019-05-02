#!/bin/bash

words=`cat 1k.txt`


echo start time: $(date +"%T.%3N")
curl -H "Content-Type: application/x-www-form-urlencoded" \
	--request POST \
	--data "$words" \
         https://deepmatter.appspot.com/save?filename=test1k
echo end time: $(date +"%T.%3N")

echo start time: $(date +"%T.%3N")
curl -H "Content-Type: text/plain" \
	--request GET \
         https://deepmatter.appspot.com/retrieve?filename=test1k
echo end time: $(date +"%T.%3N")
