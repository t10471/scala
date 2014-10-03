package org.sample

//アドホック多相の実現
trait FlipFlapper[T] {
  def doFlipFlap(x:T):T
}

object ImplicitSample {
  implicit object IntFlipFlapper extends FlipFlapper[Int] { 
    def doFlipFlap(x:Int) = - x
  }
  implicit object StringFlipFlapper extends FlipFlapper[String] {
    def doFlipFlap(x:String) = x.reverse
  }
  def flipFlap[T](x:T)(implicit flipFlapper: FlipFlapper[T]) = flipFlapper.doFlipFlap(x)
}

class MyRichInt(x: Int) {
  def f1 = - x
}
//モンキーパッチは危険だとwarningがでるので消すため
import scala.language.implicitConversions
object MyRichInt {
  implicit def intToMyRichInt(x: Int) = new MyRichInt(x)
}
//こっちのほうがモダン
object Helpers {
  implicit class IntWithF(x: Int) {
    def f2 = - x  
  }
}   

trait Comparable[T]{
  def bigger(b:T):T
}

object ViewBound {
  implicit def intComparable( a:Int ) = new Comparable[Int]{
    def bigger(b:Int):Int = Math.max(a,b)
  }
  implicit def stringComparable( a:String ) = new Comparable[String]{
    def bigger(b:String):String = if( a.compareTo(b) > 0 ) a else b
  }
  def chooseBigger2[T](a:T,b:T)(implicit comp: T => Comparable[T] ) : T = a.bigger(b)
  //これがviewbound
  def chooseBigger3[T <% Comparable[T]](a:T,b:T) : T = a.bigger(b)
}

trait Comparator[T] {
  def bigger(a:T,b:T):T
}

object ContextBound {
  implicit object IntComparator extends Comparator[Int]{
    def bigger(a:Int, b:Int):Int = Math.max(a,b)
  }
  implicit object StringComparator extends Comparator[String]{
    def bigger(a:String, b:String):String = if( a.compareTo(b) > 0 ) a else b
  }

  def chooseBigger4[T](a:T,b:T)( implicit comp:Comparator[T] ):T = comp.bigger(a,b)
  //これがcontextbound
  def chooseBigger5[T:Comparator](a:T,b:T):T = implicitly[Comparator[T]].bigger(a,b)
}

