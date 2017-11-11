import printer.Printer
import quest.Quest
import unit.Hunter

/**
  * Created by nol on 04/11/17.
  */
object Game extends App {
  println("Game started")
  val hunter = new Hunter("hunter")
  val quest = Quest.createQuest(1)
  val questResult = GameLogic.simulateQuest(hunter, quest)
  Printer.printQuestResult(questResult)
}
