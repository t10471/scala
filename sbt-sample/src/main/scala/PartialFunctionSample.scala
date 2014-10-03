package org.sample

//ドットなし記法を書くときに警告を消すために必要
import scala.language.postfixOps
object PartialFunctionSample {
  val pf1:PartialFunction[String,Option[String]] = {
       case null => None
       case "" => None
       case s => Some(s)
   }
  val pf2:PartialFunction[String,String] = { case s if s != null && s != "" => s } 
  val list = Seq( "foo",null,"bar","","","baz")

  def doPf1() = {
    println("method list filter{ pf1.isDefinedAt } map{ pf1 }")
    list filter{ pf1.isDefinedAt } map{ pf1 }
  }
  def doPf2() = {
    println("method list filter{ pf1.isDefinedAt } map{ pf1 } flatten")        
    list filter{ pf1.isDefinedAt } map{ pf1 } flatten
  }
  //これでもできるようになった・・・
  def doPf3() = {
    println("method list.collect( pf2 )")
    list collect( pf2 )
  }
  def doPf4() = {
    println("""method list collect { case s if s != null || s != "" => s }""")
    list collect { case s if s != null && s != "" => s }
  }
  def d(x : String) = {
    println(x)
    x
  }
}
