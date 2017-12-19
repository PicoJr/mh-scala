package game.questEvents

import game.quest.Quest
import rescala._

/**
  * Created by nol on 19/12/17.
  */
object QuestEvents {

  type Id = Long

  val questStarted: rescala.Evt[Quest] = Evt[Quest]() // value: questId

  val questFailed: rescala.Evt[Id] = Evt[Id]() // values: questId

  val questSucceeded: rescala.Evt[Id] = Evt[Id]() // values: quest

}
