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
        case List(conf.hunter, conf.hunter.show) => command.showHunter(gameState)
        case List(conf.hunter, conf.hunter.rename) => command.renameHunter(gameState, conf.hunter.rename.name.toOption.get)
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

  private class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val hunter = new Subcommand("hunter") {
      val show = new Subcommand("show")
      addSubcommand(show)
      val rename = new Subcommand("rename") {
        val name: ScallopOption[String] = trailArg[String]("newName")
      }
      addSubcommand(rename)
    }
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
