package game.questEvents

import game.id.Identifiable
import rescala._

/**
  * Created by nol on 19/12/17.
  */
class QuestEvents {

  type Id = Identifiable.Id

  val questStarted: rescala.Evt[Id] = Evt[Id]()

  val questFailed: rescala.Evt[Id] = Evt[Id]()

  val questSucceeded: rescala.Evt[Id] = Evt[Id]()

}
