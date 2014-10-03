package org.sample

trait Weapon {
  val strength: Int
}
trait KnightWeapon extends Weapon
trait MagicianWeapon extends Weapon

object NullWeapon extends Weapon {
  val strength = 0
}
object LongSword extends KnightWeapon {
  val strength = 10
}
object Rod extends MagicianWeapon {
  val strength = 2
}

trait HasWeapon {
  protected var weapon: Weapon = NullWeapon
}
//上限境界で指定したクラス自身とその派生クラスだけ受け付ける。
trait CanEquip[T <: Weapon] extends HasWeapon {
  def equip(w: T) = weapon = w
}

class Player(val name: String, private var hp: Int = 10) extends HasWeapon {
  val strength = 3

  def attack(otherPlayer: Player): Unit = {
    println(name + "は" + otherPlayer.name + "を攻撃した！")

    val damage = strength + weapon.strength
    otherPlayer.damaged(damage)
  }

  def damaged(damage: Int) = {
    println(name + "は" + damage.toString + "のダメージを受けた")

    hp = if (hp - damage < 0) { 0 } else { hp - damage }

    if (isDead) {
      println(name + "は死んでしまった")
    }
  }

  private def isDead = hp <= 0
}

class Knight(name: String, hp: Int)
extends Player(name: String, hp: Int) with CanEquip[KnightWeapon] {

  override val strength = 5
}

class Magician(name: String, hp: Int, private var mp: Int = 5)
extends Player(name: String, hp:Int) with CanEquip[MagicianWeapon] {

  def mera(otherPlayer: Player) = {
    println(name + "はメラを唱えた")

    if (mp < 3) {
      "しかしmpが足りない"
    } else {
      otherPlayer.damaged(10)
    }
  }
}
