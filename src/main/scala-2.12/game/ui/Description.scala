package game.ui

import game.item.Item
import game.item.inventory.Inventory
import game.quest.Quest
import game.unit.{Hunter, Monster}

/** Provides descriptions of game instances to the player.
  * Created by nol on 23/11/17.
  */
trait Description {

  /** Returns description of craft recipes with item identified with itemId.
    *
    * @param itemId    ingredient of craft recipe
    * @return description of craft recipes with item identified with itemId.
    */
  def descriptionRecipesWith(itemId: Long): String

  def descriptionItem(item: Item): String

  def descriptionInventory(inventory: Inventory): String

  def descriptionHunter(hunter: Hunter): String

  def descriptionMonster(monster: Monster): String

  def descriptionQuest(quest: Quest): String

  def descriptionStatistics(): String
}
