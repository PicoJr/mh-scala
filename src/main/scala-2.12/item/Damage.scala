package item

/**
  * Created by nol on 04/11/17.
  */
class Damage(i: Item, damage: Int) extends ItemDecorator(i: Item) {
  def getRawDamage: Int = damage
}
