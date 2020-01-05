
# Transactions-Statistics challenge

### Instructions

Building an launching tests:
```bash
./gradlew build
```

##### Running
```bash
./gradlew bootRun
```

Alternatively, your can run this project with docker, since the Dockerfile contains
a multi-stage build, no build in the host machine is needed before:
```bash
docker build .
docker run -p 8080:8080 <imageId>
```

#### In memory storage details
For achieving the O(1) goal, I designed a storage that creates buckets for every second. 
When a transaction is added, if the bucket already has Statistics information,
this statistics object is updated by merging the new one and then replacing the previous object 
in the bucket (see merge method in `Statistics` entity).

If no statistics are stored for that bucket, then they are created from the transaction amount and then stored.

This is done with the `ConcurrentHashMap.merge()` method, which ensures that the operation will be always atomic and thread safe.

At the end of each write operation, a cleanUp of stale transactions is performed, our business requirement says
that we only want statistics from the last minute.

These stale transactions statistics reside in our storage until a new write occur, but, this does not
affect to reads because the read operation always filter the stale statistics.

With this approach, I will only have at most 60 buckets with Statistics, and stale statistics are not a problem.


## Requirements
- The API have to be threadsafe with concurrent requests
- The API have to function properly, with proper result
- Memory solution without database (including in-memory database)
- Endpoints have to execute in constant time and memory (O(1))

