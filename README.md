# Procedurally Generated MH-like Command Line game

## What's inside?

* monster hunter like command-line game
* procedurally generated content (items, craft system, monsters & quests)
* dot file generation from generated craft system

## Motivations

### Learn

1. discover Scala concepts, syntax & ecosystem from scratch
2. try-out design patterns (decorator, abstract factory, builders...)
3. tackle design challenges (S.O.L.I.D)


* __S__ingle Responsibility Principle
* __O__pen-Closed Principle
* __L__iskov Substitution Principle
* __I__nterface Segregation Principle
* __D__ependency-Inversion Principle

### A Respectful Game

1. random-free after procedural generation
2. no quest order/level restrictions on the player
3. no time-consuming mechanics (farming, grinding, randomized loot,...)
4. quests are instantaneous
5. configurable and (hackable...I tried)

## Game Mechanics

see `game-mechanics.md`

## Dependencies

* docopt (command line parsing, included as sources <https://github.com/docopt/docopt.java>)
* scalatest (scala unit tests, <http://www.scalatest.org/>)
* typesafe (config file parsing, <https://github.com/lightbend/config>)
* org.scala-graph (dot generation for craft tree, <http://scala-graph.org/>)

## Notes

* doc can be generated with `sbt doc`
* tests can be run with `sbt test`

## Game Configuration

see `src/main/resources/application.conf`

## TODO

- [ ] take status into account inside quest logic
- [ ] add game stats
- [ ] make craft generation more configurable/hackable
- [ ] add feasibility check (check at least 1st quest can be completed with provided equipment)

## FixMe

- [ ] charm slot limit can be bypassed by equipping/un-equipping armors
