package game.questEvents

import game.id.Identifiable
import game.quest.Quest
import rescala._

/**
  * Created by nol on 19/12/17.
  */
object QuestEvents {

  type Id = Identifiable.Id

  val questStarted: rescala.Evt[Quest] = Evt[Quest]()

  val questFailed: rescala.Evt[Id] = Evt[Id]()

  val questSucceeded: rescala.Evt[Id] = Evt[Id]()

}
