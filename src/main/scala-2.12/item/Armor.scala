package item

/**
  * Created by nol on 05/11/17.
  */
class Armor(i: Item, armor: Int) extends ItemDecorator(i: Item){
  def getArmor: Int = armor
}
