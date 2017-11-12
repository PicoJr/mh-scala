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
        case List(conf.items, conf.items.list) => command.listInventory(gameState)
        case List(conf.items, conf.items.show) => command.showItem(gameState, conf.items.show.id.toOption.get)
        case List(conf.items, conf.items.equip) => command.equipItem(gameState, conf.items.equip.id.toOption.get)
        case List(conf.items, conf.items.unequip) => command.unEquipItem(gameState, conf.items.unequip.id.toOption.get)
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
    val items = new Subcommand("items") {
      val list = new Subcommand("ls")
      addSubcommand(list)
      val show = new Subcommand("show") {
        val id: ScallopOption[Long] = trailArg[Long]("itemID")
      }
      addSubcommand(show)
      val equip = new Subcommand("equip") {
        val id: ScallopOption[Long] = trailArg[Long]("itemID")
      }
      addSubcommand(equip)
      val unequip = new Subcommand("unequip") {
        val id: ScallopOption[Long] = trailArg[Long]("itemID")
      }
      addSubcommand(unequip)
    }
    addSubcommand(items)
    val quests = new Subcommand("quests") {
      val list = new Subcommand("ls")
      addSubcommand(list)
      val show = new Subcommand("show") {
        val id: ScallopOption[Long] = trailArg[Long]("questID")
      }
      addSubcommand(show)
      val start = new Subcommand("start") {
        val id: ScallopOption[Long] = trailArg[Long]("questID")
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
