# Scala
## Some key concepts
+ Singleton Object
```scala
object HelloWorldScala {
  def main(args: Array[String]) {
    println("Hello, world!")
  }
}
```
You might have noticed that the main method is not declared as static here. This is because static members (methods or fields) do not exist in Scala. Rather than defining static members, the Scala programmer declares these members in singleton objects.

Keyword object declared a singleton object, which is a class with a single instance. The declaration above thus declares both a class called HelloWorldScala and an instance of that class, also called HelloWorldScala. This instance is lazily initialized and it will be created the first time it is used.
+ class
Apart from singletons, you can also create multiple objects by defining classes in Scala.
```scala
class Point(var x: Int = 0, var y: Int = 0) { 

  def move(dx: Int, dy: Int): Unit = {
    x = x + dx
    y = y + dy
  }

  override def toString: String =
    s"($x, $y)"
}

val point1 = new Point
point1.x  //0
println(point1)  //(0, 0)

val point2 = new Point(2, 3)
point2.x  //2
println(point2)  //(2, 3)

val point3 = new Point(y = 100)
point3.move(50, -30)
println(point3)  //(50, 70)
```
+ case class
In general, you can pattern match on objects by adding just a case keyword to each class.
```scala
scala> abstract class Item
defined class Item

scala> case class Book(isbn : String) extends Item
defined class Book

scala> case class Magazine(issn : String) extends Item
defined class Magazine

scala> val item  = Book("978-0984782864")
item: Book = Book(978-0984782864)

scala> def printSerialNumber(item : Item) = {
     | item match {
     | case Book(b) => println("ISBN: " + b)
     | case Magazine(m) => println("ISSN: " + m)
     | case _ =>
     | }
     | }

scala> printSerialNumber(item)
ISBN: 978-0984782864
```
+ object

+ companion object
The object with the same name as a class or a trait (trait is the interface in Scala) and is defined in the same source file as the associated file or the trait is defined as companion object. An analog to a companion object in Java is having a class with static variables or methods. In Scala you would move the static variables and methods to its companion object.
```scala
class Main {
    def nonStaticMethod() {
        println("nonStaticMethod");
    }
}

// companion object
object Main {
    // a "static" variable
    val STATIC_FINAL_CONSTANT = "const"
    // a "static" method
    def staticMethod() {
        println("staticMethod");
    }
}

var mainInstance : Main = new Main();
mainInstance.nonStaticMethod(); // not Main.nonStaticMethod()

Main.staticMethod(); // not mainInstance.staticMethod()
```
+ trait
trait is the interface in Scala