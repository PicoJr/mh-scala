package game.unit

import game.item.Inventory

/**
  * Created by nol on 22/11/17.
  */
trait Hunter extends GameUnit {


  /** Get hunter inventory
    *
    * @return hunter inventory
    */
  def getInventory: Inventory

}
