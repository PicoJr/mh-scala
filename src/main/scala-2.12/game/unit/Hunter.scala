package game.unit

import game.item.Item
import game.item.inventory.Inventory

/** Unit controlled by the player.
  * Created by nol on 22/11/17.
  */
trait Hunter[TItem <: Item] extends GameUnit {

  /** hunter inventory */
  val inventory: Inventory[TItem]

}
