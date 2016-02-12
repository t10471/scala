package org.sample

class Creature
class Animal extends Creature
class Cat extends Animal

class Nonvariant[T](val t: T){
  def foo(x: T) : T = x
}

// 共変 引数に使用できない
class Covariant[+T](val t: T) {
  // def ng(x: T) : Unit = ()
  def ok(x: String) : T = this.t
  // 下限境界を設定(Tの親クラスを許可)することで入力に使える
  def foo[T1 >: T](x: T1): T = this.t
}

// 反変 戻り値に使えない
// コンストラクタにも使えない
class Contravariant[-T] {
  // def ng(x: T) : T = x
  def ok(x: T) : Unit = ()
  // 上限境界を設定(Tのサブクラスを許可)することで出力に使える
  def foo[T1 <: T](x: T1): T1 = x
}

object Variant {
  def exec():Unit = {
    // 非変は自分のクラスのみ代入を許可する
    val nonvariant1:Nonvariant[Animal] = new Nonvariant[Animal](new Animal)
    // val nonvariant2:Nonvariant[Animal] = new Nonvariant[Creature](new Creature)
    // val nonvariant3:Nonvariant[Animal] = new Nonvariant[Cat](new Cat)
    // サブクラスはOK
    nonvariant1.foo(new Animal)
    // nonvariant1.foo(new Creature)
    nonvariant1.foo(new Cat)
    // 共変はサブクラスの代入を許可する
    val covariant1:Covariant[Animal] = new Covariant[Animal](new Animal)
    // val covariant2:Covariant[Animal] = new Covariant[Creature](new Creature)
    val covariant3:Covariant[Animal] = new Covariant[Cat](new Cat)
    // 全てOK
    covariant1.foo(new Animal)
    covariant1.foo(new Creature)
    covariant1.foo(new Cat)
    // 反変は継承クラスの代入を許可する
    val contravariant1:Contravariant[Animal] = new Contravariant[Animal]
    val contravariant2:Contravariant[Animal] = new Contravariant[Creature]
    // val contravariant3:Contravariant[Animal] = new Contravariant[Cat]
    // サブクラスはOK
    contravariant1.foo(new Animal)
    // contravariant1.foo(new Creature)
    contravariant1.foo(new Cat)
  }
}
