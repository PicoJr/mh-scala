# Procedurally Generated MH-like Command Line game

## Why

### Procedural Craft Tree Generation

* craft tree is procedurally generated
* it can be exported as a dot file

### A Respectful Game

1. random-free after procedural generation
2. no quest order/level restrictions on the player
3. no time-consuming mechanics (farming, grinding, randomized loot,...)
4. quests are instantaneous
5. configurable and (hackable...I tried)

## Commands

```
MH
Usage:
  mh hunter show
  mh item ls
  mh item (equip | unequip) <itemId>...
  mh item show <itemId>...
  mh quest (ls | start <questId>...)
  mh craft show <itemId>...
  mh craft new <itemId1> <itemId2>
  mh score
  mh quit
  mh (-h | --help)
  mh --version
Options:
  -h --help    Show this screen.
  --version    Show version
```

## Game Configuration

see `src/main/resources/application.conf`

## Default Game Mechanics

### Goal

The player is a *hunter*, the goal is to complete all *quests* (slay all *monsters*).

### Hunter Stats & Equipment

* The player starts with some default items: a weapon, full armor and a charm (talisman).

* The player stats are solely based on his/her equipment.

### Quest Results

* When a player starts a quest, result is computed according to player stats and monster stats.
* The computation is determinist and non-random.

### Quests Loot

* When a quest is successfully completed, the player receives *materials*.
* the loot is always the same for a given game and a given quest.

### Craft System

* items can be crafted during the game.
* craft recipes work as: `item level i` + `material level i` = `item level i + 1`
* craft ingredients (items and materials) are not consumed (avoids farming).

### Scoring

Basic scoring is implemented: `score` = `quests attempts` vs `quest number`
ie the perfect score is obtained when the player completes all quests without failing.

## Dependencies

* docopt (command line parsing, included as sources <https://github.com/docopt/docopt.java>)
* scalatest (scala unit tests, <http://www.scalatest.org/>)
* typesafe (config file parsing, <https://github.com/lightbend/config>)
* org.scala-graph (dot generation for craft tree, <http://scala-graph.org/>)

## Notes

* doc can be generated with `sbt doc`
* tests can be run with `sbt test`

## TODO

- [ ] take status into account inside quest logic
- [ ] add game stats
- [ ] make craft generation more configurable/hackable
- [ ] add feasibility check (check at least 1st quest can be completed with provided equipment)

## FixMe

- [ ] charm slot limit can be bypassed by equipping/un-equipping armors