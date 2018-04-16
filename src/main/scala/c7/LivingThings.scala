def foo(bar: Baz with Blarg with FruitBat)

abstract class LivingThings
abstract class Plat extends LivingThings
abstract class Fungus extends LivingThings
abstract class Animal extends LivingThings

trait HasLegs extends Animal {
  def walk() { println("Walking") }
}

trait HasWings extends Animal {
  def flap() { println("Flap Flap") }
}

trait Flies {
  //only class extends HasWings
  this: HasWings =>
  def fly() { println("Im flying") }
}

abstract class Bird extends Animal with HasWings with HasLegs
class Robin