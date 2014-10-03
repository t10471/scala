package org.sample

object CollectionSample {
  def exec() {
    exec1();
    exec2();
    exec3();
    exec4();
  }
  def exec1() {
    println("exec1");
    //基本
    println((0 until 5) foreach { i => println(i) })
    println(List(0,1,2,3,4,"S") foreach {
      case i: Int => println(i)
      case _ => println("NaN")
    })
    println(List(1,2,3,4) filter { _>=2 }) 
    println(Map("aaa" -> "AAA", "bbb" -> "BBB") map { case (k,v) => v })

    //yieldをつかって新たなコレクションを返す
    println(for(i <- 0 to 3 ) yield i * i)
    def square(i: Int) = i * i
    println(for(i <- 0 to 3 ) yield square(i))
    println(for(i <- 1 to 10) yield { 
      if ( i < 4 ) 0 else i 
    }) 
  }
  def exec2() {
    println("exec2");
    //再帰
    def printAll1(chars: List[String]): Unit = { 
      chars match {
        case head :: Nil => println(head) 
        case head :: tail => {
          print(head)
          printAll1(tail)
        }
        case _ =>
      }
    }
    printAll1(List("H","e","l","l","o"))
    //エラーになる
    // def printAll2: List[String] = {
    //   case head :: Nil => println(head) 
    //   case head :: tail => {
    //     print(head)
    //     printAll2(tail)
    //   }
    //   case _ =>
    // }
    // printAll2(List("H","e","l","l","o"))
  }

  def exec3() {
    println("exec3");
    //forの書き換え
    //forreach
    val list = List(1, 2, 3)
    list.foreach(el => {
      println(el * 2)
    })
    for {el <- list} {
      println(el * 2)
    }
    //Optionも使える
    var option:Option[Integer] = Some(1)
    for {el <- option} {
      println(el * 2)
    }
    option = None
    for {el <- option} {
      println(el * 2)
    }
    //filter
    def isOdd(n: Integer) = n % 2 == 1
    list.filter(el => isOdd(el)).foreach(el => println(el))
    for { el <- list if isOdd(el)} {
      println(el)
    }
    //map
    list.map(el => el * 2)
    for { el <- list } yield {
      el * 2
    }
  }

  def result(someNyan:Option[String], someWan:Option[String]){
      println(for { nyan <- someNyan; wan  <- someWan } yield {
        nyan + wan
      })
  }

  def exec4() {
    println("exec4");
    //ネストしたfor2つはおなじ
    println(for{i <- List("a", "b");j <- List("c", "d")} yield i+j)
    println(List("a","b").flatMap(i => List("c","d").map(j => i+j)))

    var someNyan:Option[String] = Some("nyan")
    var someWan:Option[String] = Some("wan")
    result(someNyan, someWan)
    someNyan = None
    someWan = Some("wan")
    result(someNyan, someWan)

    someNyan = Some("nyan")
    someWan = None
    result(someNyan, someWan)

    someNyan = Some("nyan")
    someWan = None
    result(someNyan, someWan)

    someNyan = None
    someWan  = None
    result(someNyan, someWan)
  }
}
