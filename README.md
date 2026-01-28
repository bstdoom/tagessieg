# tagessieg

Github pages hosted statistics for Kick Off 2 match results.

## About

_Mission statement (german)_

>Zwei Freunde, nennen wir sie H. und J. spielen Kick Off 2 am Amiga 500.
> Sie spielen ein nie endendes Turnier gegeneinander um den größten Preis, den es im e-sport zu erringen gibt: den **Tagessieg**!

## Rules

* **KickOff2** always has two teams playing, `Team A` and `Team B`. Player `J` always plays `Team A`, player `H` always plays `Team B`.
* The **tournament** is played via a series of best-of-three matches, called `day`. It goes on forever, and the player with the most `days` won is in the lead.
* For each `day`, three **match**es are played, even if the winner is already fixed. Draws are possible. Unlike other tournaments, the goal difference is not taken into account, so the results are just counted as wins, losses or draws.
    * _Example:_ `J` vs. `H` = `3:0`, `0:4`, `0:0` - one win each, one draw, no winner for the `day`, although `H` scored one goal more than `J`.
    * _Example:_ `J` vs. `H` = `1:4`, `1:0`, `2:1` - `tagessieg` for `J` - although `H` scored more goals than `J`.
* **Special Rule**: the `Grand Slam` - `tagessieg` and `GrandSlam` by winning all three matches.
    * _Example:_ `J` vs. `H` = `3:0`, `2:0`, `2:1` - `tagessieg` for `J`.

## Data

* Each `day` is stored as a data entry, consisting of:
    * The date of the match in `dd.MM.yyyy` format
    * The three game results: `2:1`, `1:3`, `0:0`
    * New entries are appended to the end of a data file, this data file should contain all matches per year, _Example:_ `data/2025.csv`

## Statistics

* Most important: Count the number of `tagessieg`s total (across years) and per year.
* Also: Count the number of `Grand Slam`s total (across years) and per year.
* Also: days since last `tagessieg` and `GrandSlam`
* Also: Longest streak of `tagessieg`s and `GrandSlam`s.

