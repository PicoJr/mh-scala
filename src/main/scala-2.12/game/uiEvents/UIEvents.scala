package game.uiEvents

import game.item.Item
import rescala._

/**
  * Created by nol on 19/12/17.
  */
object UIEvents {

  type Id = Long

  val itemObtained: rescala.Evt[Id] = Evt[Id]()

  val craftNotFound: rescala.Evt[(Id, Id)] = Evt[(Id, Id)]

  val itemIdNotFound: rescala.Evt[Id] = Evt[Id]()

  val questIdNotFound: rescala.Evt[Id] = Evt[Id]()

  val hunterRenamed: rescala.Evt[String] = Evt[String]()

  val itemEquipped: rescala.Evt[Item] = Evt[Item]()

  val itemNotEquipped: rescala.Evt[Item] = Evt[Item]()

  val listQuests: rescala.Evt[Unit] = Evt[Unit]()

  val listInventory: rescala.Evt[Unit] = Evt[Unit]()

  val showQuest: rescala.Evt[Id] = Evt[Id]()

  val showHunter: rescala.Evt[Unit] = Evt[Unit]()

  val showStat: rescala.Evt[Unit] = Evt[Unit]()

  val showCraft: rescala.Evt[Id] = Evt[Id]()

  val showItem: rescala.Evt[Id] = Evt[Id]()

  val questCompleted: rescala.Evt[Id] = Evt[Id]()
}
