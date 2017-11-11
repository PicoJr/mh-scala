package game

import console.{CommandParser, MockCommand}

/**
  * Created by nol on 04/11/17.
  */
object Game extends App {
  val commandParser = new CommandParser(new MockCommand())
  val gameState = GameState.createNewGameState
  var quit: Boolean = false
  println("Game started")
  while (!quit) {
    val args = scala.io.StdIn.readLine(">")
    commandParser.runCommand(args.split(" "), gameState)
    quit = args == "quit"
  }
}
