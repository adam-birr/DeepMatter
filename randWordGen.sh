#!/bin/bash 

< /dev/urandom tr -dc _A-Za-z | head -c${1:-5};echo;
