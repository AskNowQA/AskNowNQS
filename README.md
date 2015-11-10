# AskNow

AskNow is a Question Answering (QA) system for RDF datasets. 
The sytem first normalizes Natural Language (English) query into an intermediary canonical syntactic form, called Normalized Query Structure (NQS), and then translated into SPARQL queries. 
NQS helps in identifying the desire (or expected output information) and the user-provided input information, and establishing their mutual semantic relationship. 
At the same time, it is sufficiently adaptive to query paraphrasing.

## Setup Instructions
CubeQA uses [Project Lombok](http://projectlombok.org/), which removes much boilerplate from Java. If you use Eclipse you need to download and execute lombok.jar (doubleclick it, or run java -jar lombok.jar). Follow instructions.
