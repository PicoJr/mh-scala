package item

/**
  * Created by nol on 06/11/17.
  */
object ItemFactory {
  def createDefaultWeapon(name: String, damage: Int) : Item = {
    new Damage(new ItemDefault(name), damage)
  }

  def createDefaultArmor(name: String, armor: Int) : Item = {
    new Armor(new ItemDefault(name), armor)
  }

}
