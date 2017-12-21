package game.item.craft.nature

import game.item.ItemType

/** ItemType Nature.
  * Created by nol on 25/11/17.
  */
trait NatureType {
  /** user-friendly real-play description */
  val name: String

  def createItemType(level: Int): ItemType
}
