import item._

/**
  * Created by nol on 04/11/17.
  */
object Game extends App {
  println("Game started")
  val w = ItemFactory.createDefaultWeapon("weapon", 42)
  assert(ItemFactory.isWeapon(w), "weapon sanity check")
  assert(ItemFactory.isEquipment(w), "weapon is equipment")
  assert(ItemFactory.isDamage(w), "weapon is damage")
}
