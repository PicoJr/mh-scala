package game.gameStateEvents

import game.id.Identifiable
import rescala._

/**
  * Created by nol on 18/12/17.
  */
class GameStateEvents {
  type Id = Identifiable.Id

  val itemCrafted: rescala.Evt[Id] = Evt[Id]()

  val itemEquipped: rescala.Evt[Id] = Evt[Id]()

  val itemNotEquipped: rescala.Evt[Id] = Evt[Id]()

  val hunterRenamed: rescala.Evt[String] = Evt[String]()

  val questStarted: rescala.Evt[Id] = Evt[Id]()

  val questSucceeded: rescala.Evt[Id] = Evt[Id]()

  val questFailed: rescala.Evt[Id] = Evt[Id]()

  val questCompleted: rescala.Evt[Id] = Evt[Id]()

  val equipItem: rescala.Evt[Id] = Evt[Id]()

  val unEquipItem: rescala.Evt[Id] = Evt[Id]()

  val craftItem: rescala.Evt[(Id, Id)] = Evt[(Id, Id)]()
}

