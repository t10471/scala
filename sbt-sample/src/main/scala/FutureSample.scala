package org.sample

import scala.concurrent.{Await, Future, future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

object FutureSample {
  def exec() {
    exec1()
    exec2()
  } 

  def exec1() {
    val f: Future[Int] = Future {
      Thread.sleep(1000)
      1
    }
    // 「1秒後に "1" になる計算の結果を2倍するという計算」をFutureで包んで返す
    val g: Future[Int] = f.map(i => i * 2)
    println("ここはすぐ表示される")
    //Await.resultでもって「中身」が決定するまで待って結果を取り出し、結果を表示する
    println(Await.result(g, 10 second))
  }

  def exec2() {
    val f0: Future[Int] = Future {
      Thread.sleep(1000)
      1
    }
    val f1: Future[Int] = Future {
      Thread.sleep(2000)
      2
    }
    val f2: Future[Int] = Future {
      Thread.sleep(3000)
      3
    }
    val f3: Future[Int] = Future {
      Thread.sleep(4000)
      4
    }
    val f4: Future[Int] = for {a <- f0; b <- f1} yield {
      a + b
    }
    val f5: Future[Int] = for {a <- f2; b <- f3} yield {
      a + b
    }
    val f6: Future[Int] = for {a <- f4; b <- f5} yield {
      a + b
    }
    println(Await.result(f6, 20 seconds))
  }


}
