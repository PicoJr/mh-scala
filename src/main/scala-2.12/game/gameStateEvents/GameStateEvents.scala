package game.gameStateEvents

import game.id.Identifiable
import game.item.Item
import rescala._

/**
  * Created by nol on 18/12/17.
  */
object GameStateEvents {
  type Id = Identifiable.Id

  val itemCrafted: rescala.Evt[Item] = Evt[Item]()

  val itemEquipped: rescala.Evt[Item] = Evt[Item]()

  val itemNotEquipped: rescala.Evt[Item] = Evt[Item]()

  val hunterRenamed: rescala.Evt[String] = Evt[String]()

  val questStarted: rescala.Evt[Id] = Evt[Id]()

  val questSucceeded: rescala.Evt[Id] = Evt[Id]()

  val questCompleted: rescala.Evt[Id] = Evt[Id]()

  val equipItem: rescala.Evt[Id] = Evt[Id]()

  val unEquipItem: rescala.Evt[Id] = Evt[Id]()

  val craftItem: rescala.Evt[(Id, Id)] = Evt[(Id, Id)]()
}

