#!/bin/sh

word='abc'
two='cde'
cc=$word$two
echo $cc

cc2=''
for i in {1..5}
do
	cc2=$cc2$word

done
echo $cc2
