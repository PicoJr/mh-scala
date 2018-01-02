package game

import game.commandEvents.{CommandEvents, CommandEventsHandler}
import game.console.CommandParser
import game.gameStateEvents.{GameStateEvents, GameStateEventsHandler}
import game.gamestate.DefaultGameStateFactory
import game.id.DefaultIdSupplier
import game.item._
import game.item.craft.bonus.{DamageBonus, ProtectionBonus}
import game.item.craft.nature.{Armor, Charm, Weapon}
import game.item.craft.{DefaultCraftFactory, DefaultCrafts}
import game.item.element.{ELECTRIC, FIRE, NORMAL, WATER}
import game.item.status.{NEUTRAL, SLEEP, STUN}
import game.questEvents.{QuestEvents, QuestEventsHandler}
import game.ui.DefaultDescription
import game.uiEvents.{UIEvents, UIEventsHandler}

/** Game launcher.
  * Created by nol on 04/11/17.
  */
object Game extends App {
  /* Factories */
  private val decorator = new DefaultDecorator()
  private val itemTypeFactory = new DefaultItemTypeFactory(new DefaultIdSupplier)
  private val itemFactory = new DefaultItemFactory(new DefaultIdSupplier)
  /* Game state */
  private val natureTypes = Seq(Weapon[ItemType](decorator, itemTypeFactory), Charm[ItemType](decorator, itemTypeFactory), Armor[ItemType](ArmorPart.HEAD, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.BODY, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.ARMS, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.LEGS, decorator, itemTypeFactory))
  private val elementTypes = Seq(FIRE, WATER, ELECTRIC, NORMAL)
  private val statusTypes = Seq(STUN, SLEEP, NEUTRAL)
  private val bonusTypes = Seq(DamageBonus, ProtectionBonus)
  private val crafts = new DefaultCraftFactory[ItemType](bonusTypes, elementTypes, natureTypes, statusTypes, new DefaultDecorator(), itemTypeFactory).generateCraft(new DefaultCrafts[ItemType])
  private val gameState = new DefaultGameStateFactory(crafts, itemFactory).createGameState
  /* events */
  private val commandEvents = new CommandEvents()
  private val gameStateEvents = new GameStateEvents()
  private val uIEvents = new UIEvents()
  private val questEvents = new QuestEvents()
  new CommandEventsHandler(commandEvents, uIEvents, gameStateEvents)
  new QuestEventsHandler(gameState, questEvents, gameStateEvents, itemFactory)
  new GameStateEventsHandler(gameState, gameStateEvents, questEvents, uIEvents, itemFactory)
  new UIEventsHandler(gameState, uIEvents, new DefaultDescription(gameState))
  private val commandParser = new CommandParser(commandEvents)
  var quit: Boolean = false
  println("Game started")
  while (!quit) {
    val args = scala.io.StdIn.readLine(">")
    commandParser.runCommand(args.split(" "))
    quit = args == "quit"
  }
}
