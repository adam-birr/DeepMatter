#!/bin/bash

words=`cat 1k.txt`
words10k=''

for i in {1..10}
do
	words10k=$words10k$words
done
echo $words10k

echo -----------------
echo save start time: $(date +"%T.%3N")
curl -H "Content-Type: application/x-www-form-urlencoded" \
	--request POST \
	--data "$words10k" \
         https://deepmatter.appspot.com/save?filename=test10k4
echo save end time: $(date +"%T.%3N")

echo -----------------
echo retrieve start time: $(date +"%T.%3N")
curl -H "Content-Type: text/plain" \
	--request GET \
         https://deepmatter.appspot.com/retrieve?filename=test10k4
echo retrieve end time: $(date +"%T.%3N")
echo -----------------
