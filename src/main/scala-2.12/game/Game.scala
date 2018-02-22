package game

import game.commandEvents.{CommandEvents, CommandEventsHandler}
import game.console.CommandParser
import game.gameStateEvents.{GameStateEvents, GameStateEventsHandler}
import game.gamestate.DefaultGameStateFactory
import game.item.craft.DefaultCraftFactory
import game.item.craft.addOn.{BonusAddOn, ElementAddOn, StatusAddOn}
import game.questEvents.{QuestEvents, QuestEventsHandler}
import game.ui.DefaultDescription
import game.uiEvents.{UIEvents, UIEventsHandler}

/** Game launcher.
  * Created by nol on 04/11/17.
  */
object Game extends App {
  /* crafts */
  private val craftFactory = new DefaultCraftFactory(GameDefaults.natureTypes)
  /* level 1 */
  for (element <- GameDefaults.elementTypes) {
    craftFactory.withAddOn(1, ElementAddOn(element))
  }
  /* level 2 */
  for (status <- GameDefaults.statusTypes) {
    craftFactory.withAddOn(2, StatusAddOn(status))
  }
  /* level 3 */
  for (bonus <- GameDefaults.bonusTypes) {
    craftFactory.withAddOn(3, BonusAddOn(bonus))
  }
  /* level 4 */
  for (bonus <- GameDefaults.bonusTypes) {
    craftFactory.withAddOn(4, BonusAddOn(bonus))
  }
  val crafts = craftFactory.generateCraft()
  val gameState = new DefaultGameStateFactory(crafts).createGameState
  /* events */
  val commandEvents = new CommandEvents()
  val gameStateEvents = new GameStateEvents()
  val uIEvents = new UIEvents()
  val questEvents = new QuestEvents()
  new CommandEventsHandler(commandEvents, uIEvents, gameStateEvents)
  new QuestEventsHandler(gameState, questEvents, gameStateEvents, GameDefaults.eEResolver)
  new GameStateEventsHandler(gameState, gameStateEvents, questEvents, uIEvents)
  new UIEventsHandler(gameState, uIEvents, new DefaultDescription(gameState))
  val commandParser = new CommandParser(commandEvents)
  var quit: Boolean = false
  println("Game started")
  while (!quit) {
    val args = scala.io.StdIn.readLine(">")
    commandParser.runCommand(args.split(" "))
    quit = args == "quit"
  }
}
