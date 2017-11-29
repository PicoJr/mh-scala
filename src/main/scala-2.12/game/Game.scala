package game

import game.console.{CommandParser, DefaultCommand}
import game.description.DefaultDescription
import game.gamestate.DefaultGameState

/** Game launcher.
  * Created by nol on 04/11/17.
  */
object Game extends App {
  val defaultCommand = new DefaultCommand(new DefaultDescription)
  val commandParser = new CommandParser(defaultCommand)
  val gameState = DefaultGameState.createNewGameState
  var quit: Boolean = false
  println("Game started")
  while (!quit) {
    val args = scala.io.StdIn.readLine(">")
    commandParser.runCommand(args.split(" "), gameState)
    if (gameState.allQuestsCompleted) {
      defaultCommand.showScore(gameState)
    }
    quit = args == "quit"
  }
}
