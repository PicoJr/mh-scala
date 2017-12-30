package game.ui

import game.item.inventory.Inventory
import game.item.{Item, ItemType}
import game.quest.Quest
import game.unit.{Hunter, Monster}

/** Provides descriptions of game instances to the player.
  * Created by nol on 23/11/17.
  */
trait Description[TItem <: Item, TItemType <: ItemType] {

  def descriptionRecipesWith(item: TItem): String

  def descriptionItem(item: TItem): String

  def descriptionInventory(inventory: Inventory[TItem]): String

  def descriptionHunter(hunter: Hunter[TItem]): String

  def descriptionMonster(monster: Monster): String

  def descriptionQuest(quest: Quest[TItemType]): String

  def descriptionStatistics(): String
}
