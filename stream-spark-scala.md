```bash
docker run -i --rm --network=csp_cluster-network-spark --name=nc subfuzion/netcat -lk 9999
```

```bash
./run-example org.apache.spark.examples.sql.streaming.StructuredNetworkWordCount 172.27.0.5 9999
```

Ou depuis le shell

```scala
import spark.implicits._

val host = "127.X.X.X"
val port = 9999

// Create DataFrame representing the stream of input lines from connection to host:port
val lines = spark.readStream.format("socket").option("host", host).option("port", port).load()

// Split the lines into words
val words = lines.as[String].flatMap(_.split(" "))

// Generate running word count
val wordCounts = words.groupBy("value").count()

// Start running the query that prints the running counts to the console
val query = wordCounts.writeStream.outputMode("complete").format("console").start()
```

Retrouver l'adresse IP sur le réseau csp_cluster-network-spark du container netcat, ici `172.27.0.5`

Démarrer RabbitMQ

```bash
docker run -d --hostname rabbit --network=csp_cluster-network-spark  --name rabbit rabbitmq:3-management
```