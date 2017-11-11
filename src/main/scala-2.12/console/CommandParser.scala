package console

import game.GameState
import org.rogach.scallop.{ScallopConf, ScallopOption, Subcommand}

/**
  * Created by nol on 11/11/17.
  */
class CommandParser(command: Command) {

  def runCommand(args: Seq[String], gameState: GameState): Unit = {
    val conf = new Conf(args)
    conf.subcommands match {
      case List(conf.hunter) => command.showHunter(gameState)
      case List(conf.inventory) => command.listInventory(gameState)
      case List(conf.quests, conf.quests.list) => command.listQuests(gameState)
      case List(conf.quests, conf.quests.show) => command.showQuest(gameState, conf.quests.show.id.toOption.get)
      case _ => println(conf.subcommands)
    }
  }

  class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val hunter = new Subcommand("hunter")
    val inventory = new Subcommand("inventory")
    val quests = new Subcommand("quests") {
      val list = new Subcommand("ls")
      val show = new Subcommand("show") {
        val id: ScallopOption[Int] = trailArg[Int]("questID")
      }
      addSubcommand(list)
      addSubcommand(show)
    }
    val quit = new Subcommand("quit")
    addSubcommand(hunter)
    addSubcommand(inventory)
    addSubcommand(quests)
    addSubcommand(quit)
    verify()
  }

}
