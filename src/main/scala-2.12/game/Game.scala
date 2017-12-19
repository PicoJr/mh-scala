package game

import game.commandEvents.CommandEventsHandler
import game.console.CommandParser
import game.gameStateEvents.GameStateEventsHandler
import game.gamestate.DefaultGameStateFactory
import game.questEvents.QuestEventsHandler
import game.uiEvents.UIEventsHandler

/** Game launcher.
  * Created by nol on 04/11/17.
  */
object Game extends App {
  val gameState = new DefaultGameStateFactory().createGameState
  new CommandEventsHandler(gameState)
  new QuestEventsHandler(gameState)
  new GameStateEventsHandler(gameState)
  new UIEventsHandler(gameState)
  var quit: Boolean = false
  println("Game started")
  while (!quit) {
    val args = scala.io.StdIn.readLine(">")
    CommandParser.runCommand(args.split(" "))
    quit = args == "quit"
  }
}
