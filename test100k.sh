#!/bin/bash


echo -----------------
echo save start time: $(date +"%T.%3N")
curl -H "Content-Type: application/x-www-form-urlencoded" \
	--request POST \
	--data @100k.txt \
         https://deepmatter.appspot.com/save?filename=test100kb1
echo save end time: $(date +"%T.%3N")

echo -----------------
echo retrieve start time: $(date +"%T.%3N")
curl -H "Content-Type: text/plain" \
	--request GET \
         https://deepmatter.appspot.com/retrieve?filename=test100kb1
echo retrieve end time: $(date +"%T.%3N")
echo -----------------
