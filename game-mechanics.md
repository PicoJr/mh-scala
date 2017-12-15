# Game Mechanics

## Goal

The player is a *hunter*, the goal is to complete all *quests* (slay all *monsters*).

## Hunter Stats & Equipment

* The player starts with some default items: a weapon, full armor and a charm (talisman).

* The player stats are solely based on his/her equipment.

## Quest Results

* When a player starts a quest, result is computed according to player stats and monster stats.
* The computation is determinist and non-random.

## Quests Loot

* When a quest is successfully completed, the player receives *materials*.
* the loot is always the same for a given game and a given quest.

## Craft System

* items can be crafted during the game.
* craft recipes work as: `item level i` + `material level i` = `item level i + 1`
* craft ingredients (items and materials) are not consumed (avoids farming).

## Scoring

Basic scoring is implemented: `score` = `quests attempts` vs `quest number`
ie the perfect score is obtained when the player completes all quests without failing.
