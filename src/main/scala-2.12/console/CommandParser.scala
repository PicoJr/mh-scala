package console

import game.GameState
import org.rogach.scallop.exceptions._
import org.rogach.scallop.{ScallopConf, ScallopOption, Subcommand}

/**
  * Created by nol on 11/11/17.
  */
class CommandParser(command: Command) {

  def runCommand(args: Seq[String], gameState: GameState): Unit = {
    try {
      val conf = new Conf(args)
      conf.subcommands match {
        case List(conf.hunter) => command.showHunter(gameState)
        case List(conf.inventory) => command.listInventory(gameState)
        case List(conf.quests, conf.quests.list) => command.listQuests(gameState)
        case List(conf.quests, conf.quests.show) => command.showQuest(gameState, conf.quests.show.id.toOption.get)
        case List(conf.quests, conf.quests.start) => command.startQuest(gameState, conf.quests.start.id.toOption.get)
        case List(conf.quit) => println("quit")
        case _ =>
      }
    } catch {
      case ScallopException(message) => System.err.println(s"$message")
    }
  }

  class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val hunter = new Subcommand("hunter")
    addSubcommand(hunter)
    val inventory = new Subcommand("inventory")
    addSubcommand(inventory)
    val quests = new Subcommand("quests") {
      val list = new Subcommand("ls")
      addSubcommand(list)
      val show = new Subcommand("show") {
        val id: ScallopOption[Int] = trailArg[Int]("questID")
      }
      addSubcommand(show)
      val start = new Subcommand("start") {
        val id: ScallopOption[Int] = trailArg[Int]("questID")
      }
      addSubcommand(start)
    }
    addSubcommand(quests)
    val quit = new Subcommand("quit")
    addSubcommand(quit)
    verify()

    override def onError(e: Throwable): Unit = e match {
      case Help("") => builder.printHelp
      case Help(subName) => builder.findSubbuilder(subName).get.printHelp()
      case other => throw other
    }
  }

}
