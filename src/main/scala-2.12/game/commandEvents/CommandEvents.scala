package game.commandEvents

import game.id.Identifiable
import rescala._

/** Commands (may not succeed: does not mean the action will be done).
  * Created by nol on 19/12/17.
  */
class CommandEvents {

  type Id = Identifiable.Id

  /**
    * List quests.
    */
  val listQuests: rescala.Evt[Unit] = Evt[Unit]()

  /**
    * List items from inventory.
    */
  val listInventory: rescala.Evt[Unit] = Evt[Unit]()

  /**
    * Show craft recipes with item with id Id.
    */
  val showCraft: rescala.Evt[Id] = Evt[Id]()

  /**
    * Craft using items with ids Id, Id.
    */
  val craftItem: rescala.Evt[(Id, Id)] = Evt[(Id, Id)]()

  /**
    * Show quest with id Id.
    */
  val showQuest: rescala.Evt[Id] = Evt[Id]()

  /**
    * Show hunter stats.
    */
  val showHunter: rescala.Evt[Unit] = Evt[Unit]()

  /**
    * Show global stats.
    */
  val showStat: rescala.Evt[Unit] = Evt[Unit]()

  /**
    * Equip item with id Id.
    */
  val equipItem: rescala.Evt[Id] = Evt[Id]()

  /**
    * Un-equip item with id Id.
    */
  val unEquipItem: rescala.Evt[Id] = Evt[Id]()

  /**
    * Show item with id Id.
    */
  val showItem: rescala.Evt[Id] = Evt[Id]()

  /**
    * Rename hunter with name String.
    */
  val renameHunter: rescala.Evt[String] = Evt[String]()

  /**
    * Start quest with id Id.
    */
  val startQuest: rescala.Evt[Id] = Evt[Id]()

}
