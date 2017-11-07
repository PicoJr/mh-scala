import item._

/**
  * Created by nol on 04/11/17.
  */
object Game extends App {
  println("Game started")
  val i: Item = new Item("test")
  val e: Item = Equipment(i)
  val w: Item = Damage(e, 42)
  val w2: Item = Equipment(Damage(i, 43))
  assert(w.getClassifications.size == 2, "weapon should at least be classified as equipment and damage")
  assert(e.isClassifiedAs(Classification.EQUIPMENT))
  assert(w.isClassifiedAs(Classification.EQUIPMENT))
  assert(w.isClassifiedAs(Classification.DAMAGE))
  assert(ItemFactory.isWeapon(w))
  assert(ItemFactory.isWeapon(w2))
  assert(ItemFactory.getRawDamage(w) == 42)
}
