package game.console

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
        case List(conf.craft, conf.craft.show) => command.showCraft(gameState, conf.craft.show.id.toOption.get)
        case List(conf.item, conf.item.list) => command.listInventory(gameState)
        case List(conf.item, conf.item.show) => command.showItem(gameState, conf.item.show.id.toOption.get)
        case List(conf.item, conf.item.equip) => command.equipItem(gameState, conf.item.equip.id.toOption.get)
        case List(conf.item, conf.item.unequip) => command.unEquipItem(gameState, conf.item.unequip.id.toOption.get)
        case List(conf.quest, conf.quest.list) => command.listQuests(gameState)
        case List(conf.quest, conf.quest.show) => command.showQuest(gameState, conf.quest.show.id.toOption.get)
        case List(conf.quest, conf.quest.start) => command.startQuest(gameState, conf.quest.start.id.toOption.get)
        case List(conf.quit) => println("quit")
        case _ =>
      }
    } catch {
      case ScallopException(message) => System.err.println(s"$message")
    }
  }

  private class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val hunter = new Subcommand("hunter", "h") {
      val show = new Subcommand("show")
      addSubcommand(show)
      val rename = new Subcommand("rename") {
        val name: ScallopOption[String] = trailArg[String]("newName")
      }
      addSubcommand(rename)
    }
    addSubcommand(hunter)
    val item = new Subcommand("item", "i") {
      val list = new Subcommand("ls")
      addSubcommand(list)
      val show = new Subcommand("show") {
        val id: ScallopOption[Long] = trailArg[Long]("itemID")
      }
      addSubcommand(show)
      val equip = new Subcommand("equip", "e") {
        val id: ScallopOption[Long] = trailArg[Long]("itemID")
      }
      addSubcommand(equip)
      val unequip = new Subcommand("unequip", "u") {
        val id: ScallopOption[Long] = trailArg[Long]("itemID")
      }
      addSubcommand(unequip)
    }
    addSubcommand(item)
    val craft = new Subcommand("craft", "c") {
      val show = new Subcommand("show") {
        val id: ScallopOption[Long] = trailArg[Long]("itemID")
      }
      addSubcommand(show)
    }
    addSubcommand(craft)
    val quest = new Subcommand("quest") {
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
    addSubcommand(quest)
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
