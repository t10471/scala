package org.sample

object HelloWorldScala {
  def main(args: Array[String]) :Unit = {
    println("Hello World by Scala")

    val shinpei = new Magician("しんぺい", 10, 5)
    val takashi = new Player("たかし",15) with CanEquip[Weapon]
    shinpei.equip(Rod)
    shinpei.attack(takashi)
    takashi.equip(LongSword)
    takashi.attack(shinpei)
    import ImplicitSample._
    println(ImplicitSample.flipFlap(1)) // => -1
    println(ImplicitSample.flipFlap("string")) // => "gnirts"
    import MyRichInt._
    println(123.f1) // => -123
    import Helpers._
    println(123.f2) // => -123
    import ViewBound._
    import ContextBound._
    println(chooseBigger2(1,2))
    println(chooseBigger2("a","b"))
    println(chooseBigger3(1,2))
    println(chooseBigger3("a","b"))
    println(chooseBigger4(1,2))
    println(chooseBigger4("a","b"))
    println(chooseBigger5(1,2))
    println(chooseBigger5("a","b"))
    import PartialFunctionSample._
    println(doPf1())
    println(doPf2())
    println(doPf3())
    println(doPf4())
    import CollectionSample._
    CollectionSample.exec()
    import FutureSample._
    FutureSample.exec()

  }
}
