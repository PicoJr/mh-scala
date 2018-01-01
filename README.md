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
4. try out reactive programming with [rescala](https://github.com/guidosalva/REScala/)
5. discover Scala generics ([bounds](https://docs.scala-lang.org/tour/upper-type-bounds.html) & [variances](https://docs.scala-lang.org/tour/variances.html))


* Single Responsibility Principle
* Open-Closed Principle
* Liskov Substitution Principle
* Interface Segregation Principle
* Dependency-Inversion Principle

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
* rescala (reactive scala, <https://github.com/guidosalva/REScala/>)

## Notes

* doc can be generated with `sbt doc`
* tests can be run with `sbt test`

## Game Configuration

see `src/main/resources/application.conf`

## TODO

- [ ] take status into account inside quest logic
- [x] add game stats
- [ ] make craft generation more configurable/hackable
- [ ] add feasibility check (check at least 1st quest can be completed with provided equipment)

## FixMe

- [ ] charm slot limit can be bypassed by equipping/un-equipping armors
