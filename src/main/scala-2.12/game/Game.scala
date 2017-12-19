package game

import game.console.{CommandParser, DefaultCommand}
import game.description.DefaultDescription
import game.gamestate.DefaultGameStateFactory

/** Game launcher.
  * Created by nol on 04/11/17.
  */
object Game extends App {
  val gameState = new DefaultGameStateFactory().createGameState
  val defaultCommand = new DefaultCommand(gameState, new DefaultDescription)
  val commandParser = new CommandParser(defaultCommand)
  var quit: Boolean = false
  println("Game started")
  while (!quit) {
    val args = scala.io.StdIn.readLine(">")
    commandParser.runCommand(args.split(" "))
    if (gameState.allQuestsCompleted) {
      defaultCommand.showScore()
    }
    quit = args == "quit"
  }
}
