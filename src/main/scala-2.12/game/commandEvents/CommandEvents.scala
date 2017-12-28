package game.commandEvents

import game.id.Identifiable
import rescala._

/**
  * Created by nol on 19/12/17.
  */
object CommandEvents {

  type Id = Identifiable.Id

  val listQuests: rescala.Evt[Unit] = Evt[Unit]()

  val listInventory: rescala.Evt[Unit] = Evt[Unit]()

  val showCraft: rescala.Evt[Id] = Evt[Id]()

  val craftItem: rescala.Evt[(Id, Id)] = Evt[(Id, Id)]()

  val showQuest: rescala.Evt[Id] = Evt[Id]()

  val showHunter: rescala.Evt[Unit] = Evt[Unit]()

  val showStat: rescala.Evt[Unit] = Evt[Unit]()

  val equipItem: rescala.Evt[Id] = Evt[Id]()

  val unEquipItem: rescala.Evt[Id] = Evt[Id]()

  val showItem: rescala.Evt[Id] = Evt[Id]()

  val renameHunter: rescala.Evt[String] = Evt[String]()

  val startQuest: rescala.Evt[Id] = Evt[Id]()

}
