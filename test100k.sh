#!/bin/bash

words=`cat 1k.txt`
words100k=''

for i in {1..100}
do
	words100k=$words100k$words
done

echo -----------------
echo save start time: $(date +"%T.%3N")
curl -H "Content-Type: application/x-www-form-urlencoded" \
	--request POST \
	--data "$words100k" \
         https://deepmatter.appspot.com/save?filename=test100ka
echo save end time: $(date +"%T.%3N")

echo -----------------
echo retrieve start time: $(date +"%T.%3N")
curl -H "Content-Type: text/plain" \
	--request GET \
         https://deepmatter.appspot.com/retrieve?filename=test100ka
echo retrieve end time: $(date +"%T.%3N")
echo -----------------
