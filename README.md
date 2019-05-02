# DeepMatter

The code is contained under the folder: DeepMatter/DeepMatter/src/main/

The application is available at:

https://deepmatter.appspot.com/

The two webservices are:

`/save?filename=<filename>`  
This uses the HTTP POST verb
This requires a header of application/x-www-form-urlencoded
The body should be a list of words separed by newlines
If successful, this will generate a HTTP 201 response code

`/retrieve?filename=<filename>&begin=<number>&end=<number>`
This uses the HTTP GET verb
The begin and end parameters are optional
  
## Notes on the implementation

Unfortunately the the link to Google drive was not able to save files.
The Google Drive returned a 403 scopes permission error when a file was attempted to be saved.
Instead, the files are save to the local temp folder within the web app file structure.
The issue with do it this way is that as the application scales automatically by GCP there it is not guarenteed that the the retrieve will hit the same instance that was used to save the file.  If this occurs, as HTTP 500 error is generated. Repeating the same request will eventually return the correct result.  Although most of the time the it will use the same instance to save and retrieve.

## Results

For generating the 1k result the bash script: test1k.sh was used.
For generating the 10k result the bash script: test10k.sh was used.
The method of using curl to send in a string payload of 100k did not work.
Instead I tried to use curl with a file for the payload.  Unfortunately, this did not correctly send in each word on a newline and as a result, the request handled the 100k file as a single word entry, so the test results are invalid.  I have converted the 100k test input file to windows file endings, but it still didn't change the outcome.  I have run the 100k and 1000k save and retrieve calls within postman and have added their result below.

The results for 1k and 10k are:

### Save
```
1,282ms
10,502ms
100,invalid result for bash script - runnig with postman this result was: 368ms
1000,not run in bash script - running with postman this result was: 2024ms
```

### Retrieve
```
1,316ms
10,250ms
100,invalid result for bash script - running with postman this result was: 156ms
1000,not run in bash script - running with postman this result was: 1402ms
```

It doesn't make much sense that the 10k retrieval is quicker than the 1k retrieval, but these are the timing script gave and I believe they are correct.
The raw output of these scripts are:
  * 1kresults.txt
  * 10Kresults.txt
  * 100Kresults.txt

Bash script files:

```
test1k.sh
test10k.sh
test100k.sh
```

Note: All the scripts and result files are stored at the repo root.  

## Report

The two page PDF report is located at the repo top level and is call DeepMatterReport.pdf
