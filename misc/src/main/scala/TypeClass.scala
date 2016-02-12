package org.sample

// Haskellのextentions-exampleM.hsと同じもの
case class Rectangle(bottom:Double, hight:Double)
case class Circle(radius:Double)

trait Figure[A] {
  def area(a: A) : Double
}

class Holder[A](implicit f:Figure[A]) {
  private var holder = Seq[A]()
  // 型クラス経由でパラメータにアクセスする
  def add(x:A):Holder[A] = {holder = x +: holder;  this}
  def total():Double     = holder.foldLeft(0.0)((x,y) => f.area(y) + x)
}

object Holder {
  implicit object RectangleInstance  extends Figure[Rectangle] {
    def area(x:Rectangle) = x.bottom * x.hight
  }

  implicit object CircleInstance extends Figure[Circle] {
    import math._
    def area(x:Circle) = x.radius * x.radius * Pi
  }
}

object Executer {
  def run():Unit = {
    import Holder._
    println((new Holder[Rectangle]).add(Rectangle(1, 3)).add(Rectangle(2,4)).total())
    println((new Holder[Circle]).add(Circle(3)).add(Circle(4)).total())
  }
}

