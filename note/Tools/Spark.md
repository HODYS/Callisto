# Spark
## Introduction

## Resilient Distributed Dataset(RDD)

## Command
There are different ways of running a Spark application:
```
$ spark-shell
```
or pyspark for Python users
```
$ pyspark
```
From within the cluster you can use spark-submit to submit your standalone Spark job.
Java or Scala applications:
```
$ spark-submit --class <class name> <path_to_jar> <optional arguments>
```
Python applications:
```
$ spark-submit <path_to_python_file>
```

## Writing programs
The main difference between writing your own Spark programs and using the Spark shell directly is that you have to explicitly initialize your own SparkSession in the former case. You need to [create a SparkSession](http://spark.apache.org/docs/latest/sql-programming-guide.html#starting-point-sparksession) when you are writing your own program.

On spark-shell, pypark and the notebooks the SparkSession is initialized as spark (there is no need to manually initialize it).

+ Scala  

You should probably be using a package manager such as maven pom.xml.
```
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-core_2.11</artifactId>
    <version>2.4.5</version>
</dependency>
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-sql_2.11</artifactId>
    <version>2.4.5</version>
</dependency>
```
#### Initialize the SparkSession
+ Scala
```
import org.apache.spark.sql.SparkSession

val spark = SparkSession
   .builder()
   .appName("SparkSessionExample")
   .getOrCreate()
```

In Standalone local mode you need to set the Spark master URL to connect to local:
```
import org.apache.spark.sql.SparkSession

val spark = SparkSession
   .builder()
   .master("local[*]")
   .appName("SparkSessionExample")
   .getOrCreate()
[*] specifies that it should use all cores in the machine
```
+ Java
import org.apache.spark.sql.SparkSession;
```
SparkSession spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("SparkSessionExample")
            .getOrCreate();
```
+ Python
```
from pyspark.sql import SparkSession

spark = SparkSession.builder \
                    .master("local[*]") \
                    .appName("Word Count") \
                    .config("spark.some.config.option", "SparkSessionExample") \
                    .getOrCreate()
```

#### RDD Operations
+ parallelize
we use the function parallelize which distributes a local collection into an RDD.
```scala
val data = Array(1, 2, 3, 4, 5)
val distData = sc.parallelize(data)
val squared = distData.map(x => x * x)
```
+ map and flatmap
map:
```scala
val data = Array(1, 2, 3, 4, 5)
val distData = sc.parallelize(data)
val squared = distData.map(x => x * x)
```
flatmap is a combination of map and flatten operations.For example:
```scala
// Scala program of flatMap 

// Creating object 
object GfG 
{ 

	// Main method 
	def main(args:Array[String]) 
	{ 

		// Creating a list of numbers 
		val list = List(2, 3, 4) 

		// Defining a function 
		def f(x:Int) = List(x-1, x, x+1) 

		// Applying flatMap 
		val result = list.flatMap(y => f(y)) 

		// Displays output 
		println(result) 

	} 
} 
```
How the results are gained:
```scala
List(List(2-1, 2, 2+1), List(3-1, 3, 3+1), List(4-1, 4, 4+1))

// After evaluation we get,
List(List(1, 2, 3), List(2, 3, 4), List(3, 4, 5))

List(1, 2, 3, 2, 3, 4, 3, 4, 5)
```
Another example from WordCount. line.split will generate a list. If one line has 'Lisa 19', after mapping, we get a List("Lisa", "19") and the list is flatten and result becomes 'Lisa, 19'
```scala
val file = sc.textFile("input/1112.txt") // change the path if working locally
val words = file.flatMap(line => line.split(" "))
val pairs = words.map(word => (word, 1))
val counts = pairs.reduceByKey(_+_)
counts.saveAsTextFile("output")
```
+ collect
collect() to aggregate all the RDD data on the master node as an Array.

#####  key-value pair transformations
reduceByKey, groupByKey, aggregateByKey and combineByKey

 In situations where you are grouping to aggregate data (e.g. summing over keys), using reduceByKey, aggregateByKey or combineByKey will be more performant than groupByKey, but can only be used if the operation is both commutative and associative.

+ groupByKey  

groupByKey works very much like MapReduce. It aggregates results into an Iterable of the value. The downside is that data is sent over the network, without reducing it first on each worker, which might cause out of memory issues. If the task is not commutative and associative, results cannot be aggregated before shuffling, and they need to be sent to the reducers for aggregation (similar to the Combiner in MapReduce). Therefore, groupByKey should be used.

The function definition is as follow:
```
groupByKey(): RDD[(K, Iterable[V])]
```
```scala
$ val count = data.groupByKey().map(x => (x._1, x._2.sum))
$ count.collect().foreach(println)
(is,1)
(15619,1)
(hello,2)
...
```
+ reduceByKey

If the task is commutative and associative, then reduceByKey can be used. It reduces the results on each worker before sending them to the reducers avoiding potential out of memory issues.

The function definition is as follow:
```
reduceByKey(func: (V, V) => V, [numTasks]): RDD[(K, V)]
```
Here, func has type (V, V) => V. func aggregates the values for each key separately. And numTasks represents the number of reduce tasks which is configurable.

The function transforms an RDD[(K, V)] => RDD[(K, V)].

```scala
$ def add(left: Int, right: Int): Int = left + right
$ val count = data.reduceByKey(add)
$ count.collect().foreach(println)
(is,1)
(15619,1)
(hello,2)
...

// Note that you could have used Scala's Placeholder syntax:
$ val count = data.reduceByKey(_ + _)
```
+ aggregateByKey

aggregateByKey works similarly to reduceByKey but one can specify an arbitrary initial value (it can be a different type from the original).

The function definition is as follow:
```
aggregateByKey(zeroValue: U)(seqOp: (U, V) => U, combOp: (U, U) => U, [numTasks]): RDD[(K, U)])
```
Using the analogy of MapReduce: 1. seqOp is called in the mappers to add a new value 2. combOp is used to combine different combined values
```scala
$ val count = data.aggregateByKey(0)((x, y) => x + 1, (a, b) => a + b)
$ count.collect().foreach(println)
(is,1)
(15619,1)
(hello,2)
...
```
+ combineByKey
  
combineByKey works similarly to aggregateByKey, but instead of having a fixed value for all keys one can specify a function that returns the initial value.
```
combineByKey(createCombiner: (V) ? U, mergeValue: (U, V) ? U, mergeCombiners: (U, U) ? U): RDD[(K, U)]
```
```scala
$ val count = data.combineByKey(z: Int => z, (x: Int, y: Int) => x + 1, (a: Int, b: Int) => a + b)
$ count.collect().foreach(println)
(is,1)
(15619,1)
(hello,2)
...
```

#### Spark SQL/ DataFrames
+ Datasets and DataFrames  

A Dataset is an abstraction on top of RDDs. Unlike the latter, Dataset are structured. Thus, it is organized into named columns (think of it as a table in a relational database). Therefore, Dataset[T] is a collection of strongly-typed JVM objects, dictated by a case class T you define in Scala or a class in Java.

DataFrame, on the other hand, is an alias for a collection of generic objects Dataset[Row], where a Row is a generic untyped JVM object.

Therefore, Dataset takes on two distinct APIs: a strongly-typed API (Dataset[T]) and an untyped API (DataFrame).

By providing a case class, a DataFrame can be converted to a Dataset. Note that the DataFrame column names and case class field names need to be the same.
```scala
import spark.implicits._

case class Person(name: String, age: Long)
val ds = df.as[Person]
// ds: org.apache.spark.sql.Dataset[Person] = [age: bigint, name: string]
```

+ explode
Spark function explode(e: Column) is used to explode or create array or map columns to rows. When an array is passed to this function, it creates a new default column “col1” and it contains all array elements. When a map is passed, it creates two new columns one for key and one for value and each element in map split into the row.
see [this](https://sparkbyexamples.com/spark/explode-spark-array-and-map-dataframe-column/)