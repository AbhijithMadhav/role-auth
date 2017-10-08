

![pagerank](https://user-images.githubusercontent.com/1021302/31058094-7795fcf0-a70b-11e7-8d97-c3e7941415ed.png)

## Regarding Design
Refer to `pagerank.png`(above) for a visual representation of a high level design. Java docs capture lower level details.

The important entities are 
* `PageRankService` : Encapsulates operations needed to rank pages w.r.t a query.
* `PageScoreCalculator` : Represents a computation engine which encapsulates logic needed to calculate the page score of a page w.r.t. a given query
* `PageRankStore` : Repository of ranked lists

Custom implementations of `PageScoreCalculator` and `PageRankStore` can be used for different page score calculation algorithms and for different page rank ordering requirements

## Implementation for the requirements
* `DefaultPageRankService` : Composition of `PositionalWeightBasedPageScoreCalculator` and `InMemoryBoundedPageRankStore` for the purposes of meeting the requirements listed.
* `PositionalWeightBasedPageScoreCalculator` : Implementation of `PageScoreCalculator` which encapsulates the logic listed in the requirements
* `InMemoryBoundedPageRankStore` : Implementation of `PageRankStore`. **Bounded** to ensure that top N results for each queries are kept track off. Memory based implementation is for demo purposes. This will need to be augmented with a more scalable larger store for real-world use cases. For example, a multi-level distributed cache using Redis

## Models
* `Page` : Represents a web page with key words
* `Query` : Represents a query
* `PageScore` : The quantification of a page indicating its relevance to a query

## Building and running
Unzip or clone the source
```bash
$ cd pagerank-master
$ gradle build # or ./gradlew build
$ gradle run # Executes for the input given in the requirements
```

To execute for other inputs
```bash
$ cd build/distributions
$ unzip pagerank-1.0.zip
$ cd pagerank-1.0/bin/
$ ./pagerank <input-file-path>
```
