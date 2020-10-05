Benchmark for 10 000 entities
```
Inserting entities (JPA single) took: 35.055961965s
Inserting entities (JPA list) took: 5.73625951s
Inserting entities (JPA list save all) took: 5.607583665s
Inserting entities (JPA list save all with batche 1000k) took: 5.101384927s
Inserting entities (MyBatis single) took: 8.764022431s
Inserting entities (MyBatis list) took: 3.66702061s
Inserting entities (jdbc list) took: 2.152497262s
Inserting entities (vertx single) took: 91.075988821s
Inserting entities (vertx list) took: 1.644300539s
Inserting entities (vertx list without transaction) took: 1.287974732s
Inserting entities (pg bulk insert) took: 0.389145117s
```