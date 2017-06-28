# AskNow
AskNow is a Question Answering (QA) system for RDF data sets. The system first normalizes Natural Language (English) query into an intermediary canonical syntactic form, called Normalized Query Structure (NQS), and then translated into SPARQL queries. NQS helps in identifying the desire (or expected output information) and the user-provided input information, and establishing their mutual semantic relationship. At the same time, it is sufficiently adaptive to query paraphrasing.

-----------------------------------------------------------------------------------------------------------------------------
### To run the code 


AskNow relies on word2vec for relation annotation and ranking. To start the word2vec server in the resource folder execute the basic_server.py file.The file also needs pre-trained google news corpus vector model. The file are available [here](https://drive.google.com/file/d/0B7XkCwpI5KDYNlNUTTlSS21pQmM/edit) . 

Replace the file location at line 11 in the basic_server.py file with your local file location. 

To execute basic_server.py, install all the python dependency. ***Json, requests, gensim, bottle, pprint*** are few of the dependencies.

and then simply, 
```
python basic_server.py
```


Note : basic_server.py file is avaialbe at src/main/. If things break here :fire: , ping "gaurav.rygbee@gmail.com"

After the word2vec server is :running:

Build the project using maven and execute the src/main/java/benchmark/executeQuestion.java file. This runs the qald6 file. There is only a certan subset which works. Also AskNow needs an active internet connection as it relies on external API.