////def foo(bar: Baz with Blarg with FruitBat)
//
//abstract class LivingThings
//abstract class Plat extends LivingThings
//abstract class Fungus extends LivingThings
//abstract class Animal extends LivingThings
//
//trait HasLegs extends Animal {
//  def walk() { println("Walking") }
//}
//
//trait HasWings extends Animal {
//  def flap() { println("Flap Flap") }
//}
//
//trait Flies {
//  //only class extends HasWings
//  this: HasWings =>
//  def fly() { println("Im flying") }
//
//  val arr:Array[Int] = Array(1, 1)
//  val den = arr.length.toDouble
//  var positive = 0
//  var negative = 0
//  var zeros    = 0
//  arr.foreach(x =>
//      x match{
//        case x if x > 0 => positive = positive + 1
//        case x if x < 0 => negative = negative + 1
//        case x if _     => zeros = zeros + 1
//      })
//
//  val positiveResult = if (positive == 0) 0d else positive.toDouble / den
//  val negativeResult = if (negative == 0) 0d else negative.toDouble / den
//  val zerosResult    = if (zeros == 0) 0d else zeros.toDouble / den
//}
//
//abstract class Bird extends Animal with HasWings with HasLegs
//class Robin