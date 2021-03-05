package com.sixbynine.civ3guide.shared.cityplacement

import com.sixbynine.civ3guide.shared.map.MapConfiguration
import com.sixbynine.civ3guide.shared.map.MapConfigurations
import com.sixbynine.civ3guide.shared.map.TileInfo

data class CityPlacementPuzzle(
  val map: MapConfiguration,
  private val tileAnswers: Map<TileInfo, CityPlacementAnswer>
) {

  private constructor(map: MapConfiguration, vararg answers: CityPlacementAnswer) : this(
    map,
    map.tiles.zip(answers).toMap()
  )

  fun getAnswer(tile: TileInfo): CityPlacementAnswer {
    return tileAnswers[tile] ?: FALLBACK_WRONG_ANSWER
  }

  companion object {

    private val FALLBACK_WRONG_ANSWER = Answer("")
    private val WRONG_PLANT_COASTAL =
      Answer("Not here! Make sure to plant coastal, if you have the option.")
    private val WRONG_PLANT_RIVER =
      Answer("Not here! Plant on the river if you can.")
    private val WRONG_PLANT_BONUS_FOOD =
      Answer("Incorrect! Don't plant on bonus food!")
    private val WRONG_PLANT_1_AWAY_FROM_COAST =
      Answer("Incorrect! Don't plant 1 tile away from the coast!")
    private val WRONG_PLANT_RIVER_NO_WHALE =
      Answer(
        "Incorrect! This city gets the river, but it doesn't get the whale, which is" +
            " more important"
      )
    private val WRONG_PLANT_WASTES_TILES_NO_PRODUCTION =
      Answer("Incorrect. This wastes tiles, and doesn't have enough production")
    private val WRONG_PLANT_FOOD_NO_PRODUCTION_HILLS =
      Answer(
        "Incorrect. This city has a lot of food, but it lacks production. You should plant " +
            "so that your city can use the hills"
      )
    private val WRONG_PLANT_TOO_CLOSE_TO_CAPITAL =
      Answer("Incorrect! That's too close to your capital. You can plant a little bit further away")

    private val WRONG_PLANT_COAST_BETTER_THAN_SEA =
      Answer(
        "Incorrect! Coast tiles give more commerce than sea tiles. Better to plant to get " +
            "the maximum number of coast tiles"
      )
    private val WRONG_PLANT_WASTES_BG =
      Answer("Incorrect! This city spot wastes the bonus grassland!")

    val all: List<CityPlacementPuzzle> by lazy {
      listOf(
        CityPlacementPuzzle(
          MapConfigurations.all[6],
          Answer("Not here! Don't worry, planting on bonus commerce (the tobacco) is OK!"),
          WRONG_PLANT_COASTAL,
          Answer(
            "Yup! Great spot. Coastal, gets a lot of good land tiles, and is on the river.",
            isCorrect = true
          ),
          WRONG_PLANT_COASTAL,
          WRONG_PLANT_RIVER,
          WRONG_PLANT_RIVER
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[7],
          Answer("Correct! Planting on bonus shield tiles, like iron, is ok", isCorrect = true),
          WRONG_PLANT_BONUS_FOOD,
          Answer(
            "Incorrect! That wastes the western bonus grassland, as the tile wouldn't be " +
                "within any city's boundaries!"
          ),
          WRONG_PLANT_1_AWAY_FROM_COAST,
          WRONG_PLANT_TOO_CLOSE_TO_CAPITAL,
          WRONG_PLANT_TOO_CLOSE_TO_CAPITAL
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[8],
          Answer(
            "Correct! This is good if you want a relatively close second city",
            isCorrect = true
          ),
          Answer("Correct. This is good if you want cities far apart.", isCorrect = true),
          WRONG_PLANT_BONUS_FOOD,
          Answer("Incorrect! That wastes the western sugar, it's not within any city's boundaries!"),
          Answer("Incorrect! That's 6 tiles apart from Persepolis! Many tiles will be wasted"),
          Answer("Incorrect! That's 6 tiles apart from Persepolis! Many tiles will be wasted")
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[9],
          Answer(
            "Correct! This gets the whale and the sugar, plus many flatland and coastal tiles",
            isCorrect = true
          ),
          Answer(
            "Incorrect! This city doesn't get a lot of the important flatland tiles to the east, " +
                "and these tiles get wasted"
          ),
          WRONG_PLANT_BONUS_FOOD,
          WRONG_PLANT_RIVER_NO_WHALE,
          WRONG_PLANT_RIVER_NO_WHALE,
          WRONG_PLANT_RIVER_NO_WHALE
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[10],
          Answer("Correct! This city has a ton of food and a ton of production", isCorrect = true),
          WRONG_PLANT_BONUS_FOOD,
          WRONG_PLANT_WASTES_TILES_NO_PRODUCTION,
          WRONG_PLANT_WASTES_TILES_NO_PRODUCTION,
          WRONG_PLANT_FOOD_NO_PRODUCTION_HILLS,
          WRONG_PLANT_FOOD_NO_PRODUCTION_HILLS
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[11],
          Answer(
            "Correct! Planting cities other than your capital on bonus commerce is A-OK, and " +
                "this gets both whales",
            isCorrect = true
          ),
          WRONG_PLANT_BONUS_FOOD,
          Answer(
            "Incorrect. Planting on a bonus grassland is fine, but this means the sugar and " +
                "one of the whales is out of range"
          ),
          Answer("Incorrect. This doesn't get many coastal tiles, including the whales"),
          Answer("Incorrect. This doesn't get many coastal tiles, including the whales"),
          Answer(
            "Incorrect. This spot is good, but you can get that second whale if you plant " +
                "on the gold, and there's no real downside."
          )
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[12],
          Answer(
            "Correct! There's no downside to planting on the ivory, since it's bonus commerce. " +
                "This gets you fresh water, the wheat, and 2 bonus grasslands",
            isCorrect = true
          ),
          WRONG_PLANT_BONUS_FOOD,
          Answer(
            "Incorrect. It's ok to plant on bonus commerce (like the tobacco), but this " +
                "isn't on fresh water"
          ),
          Answer(
            "Incorrect. This spot is ok, it's fine to plant on bonus grassland. But planting on " +
                "plains is strictly better"
          ),
          Answer(
            "Incorrect. This wastes the bonus grassland 2 tiles east of Damascus, and you won't " +
                "get access to the wheat until your borders expand"
          )
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[13],
          Answer(
            "Correct. 4 tiles away, and gets you the cow instantly",
            isCorrect = true
          ),
          Answer(
            "Correct. On a small island like this, it might be good to plant 3 tiles away " +
                "for more unit support, since you don't have space for many cities",
            isCorrect = true
          ),
          Answer(
            "Incorrect. Planting on a bonus grassland is ok, but it's better to avoid it unless " +
                "you have a good reason to"
          ),
          WRONG_PLANT_1_AWAY_FROM_COAST,
          WRONG_PLANT_1_AWAY_FROM_COAST,
          Answer("Incorrect. This wastes the cow")
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[14],
          Answer(
            "Correct. This is 4 tiles from your capital, and has a lot of bonus grasslands. As " +
                "an agricultural civ, planting on fresh water is especially important",
            isCorrect = true
          ),
          Answer(
            "Correct. This is 3 tiles from your capital, but it allows this city to share the " +
                "second cow with Chichen Itza. This will be strong in the early game",
            isCorrect = true
          ),
          Answer(
            "Incorrect. The wheat is tempting, but as an agricultural civ, planting on fresh " +
                "water is essential"
          ),
          Answer(
            "Incorrect. This city is fine, but it won't have many bonus grasslands until its " +
                "borders expand"
          ),
          WRONG_PLANT_RIVER,
          Answer("Incorrect. It's good to plant on the river, but get some bonus grasslands!")
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[15],
          Answer(
            "Correct! Any city in this valley will have high production. But this one has a lot " +
                "of potential food, so that you can grow big enough to use the production",
            isCorrect = true
          ),
          Answer(
            "Incorrect. This one is fine, but the gold won't be very useful early game. " +
                "Better to plant within range of more southern grassland tiles"
          ),
          WRONG_PLANT_BONUS_FOOD,
          Answer(
            "Incorrect! This valley has low food! If you plant on the flood plains, you won't " +
                "have enough food to use the high shield tiles"
          ),
          Answer(
            "Incorrect! This valley has low food! If you plant on the flood plains, you won't " +
                "have enough food to use the high shield tiles"
          ),
          Answer(
            "Incorrect! This valley has low food. Plant within range of the southern grassland"
          )
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[16],
          Answer(
            "Correct! Planting on the tobbaco gives a perfect mix of shields and food",
            isCorrect = true
          ),
          WRONG_PLANT_BONUS_FOOD,
          Answer(
            "Incorrect.This would be solid lategame, but for the early game, it's better to have " +
                "a city directly next to the wheat"
          ),
          Answer(
            "Incorrect. Planting on the hill costs us production. Since this city is already " +
                "high food, more production is important"
          ),
          Answer(
            "Incorrect. Unless you desperately need these wines, you should aim to plant within " +
                "range of the high production tiles southwest of Seoul"
          ),
          Answer(
            "Incorrect. Unless you desperately need these wines, you should aim to plant within " +
                "range of the high production tiles southwest of Seoul"
          ),
          Answer(
            "Incorrect. Unless you desperately need these wines, you should aim to plant within " +
                "range of the high production tiles southwest of Seoul"
          ),
          Answer(
            "Incorrect. Unless you desperately need these wines, you should aim to plant within " +
                "range of the high production tiles southwest of Seoul"
          )
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[17],
          Answer(
            "Correct! this has high production for the hills and forests, and lots of food from " +
                "the grassland",
            isCorrect = true
          ),
          Answer(
            "Correct. This is weaker late game, but early game it gets all the bonus grassland " +
                "immediately and would be a god spot if you wanted to do an early military rush",
            isCorrect = true
          ),
          WRONG_PLANT_BONUS_FOOD,
          Answer("Incorrect. Plant nearer to the grassland so this city has more food late game"),
          Answer("Incorrect. Plant nearer to the grassland so this city has more food late game"),
          Answer("Incorrect. Don't plant on the bonus grassland unless you have a good reason"),
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[18],
          Answer("Correct! This gets all available tiles and is coastal", isCorrect = true),
          WRONG_PLANT_BONUS_FOOD,
          Answer(
            "Incorrect! This city wastes two desert tiles north of Salamanca. The Iroquois are " +
                "an agricultural civ, so desert tiles give solid yields when irrigated. Don't " +
                "waste them!"
          ),
          Answer(
            "Incorrect! This city wastes two desert tiles north of Salamanca. The Iroquois are " +
                "an agricultural civ, so desert tiles give solid yields when irrigated. Don't " +
                "waste them!"
          ),
          WRONG_PLANT_1_AWAY_FROM_COAST,
          WRONG_PLANT_1_AWAY_FROM_COAST,
          WRONG_PLANT_1_AWAY_FROM_COAST
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[19],
          Answer(
            "Correct! This gets the deer immediately, and the maximum number of coast tiles",
            isCorrect = true
          ),
          WRONG_PLANT_BONUS_FOOD,
          WRONG_PLANT_COAST_BETTER_THAN_SEA,
          WRONG_PLANT_COAST_BETTER_THAN_SEA,
          WRONG_PLANT_COAST_BETTER_THAN_SEA,
          WRONG_PLANT_COAST_BETTER_THAN_SEA
        ),
        CityPlacementPuzzle(
          MapConfigurations.all[20],
          Answer(
            "Correct! This works great if you want a strong city early, due to the bonus grassland",
            isCorrect = true
          ),
          Answer(
            "Correct! This is the optimal late game city. It gets all available tiles, although " +
                "it doesn't have the bonus grassland until it gets a border expand",
            isCorrect = true
          ),
          Answer(
            "Incorrect! This is 3 tiles from your cap and wastes some land tiles, so it's " +
                "weak late game, but it's not next to the bonus grassland, so it's weak early " +
                "game too"
          ),
          WRONG_PLANT_WASTES_BG,
          WRONG_PLANT_WASTES_BG,
          WRONG_PLANT_WASTES_BG,
          Answer("Incorrect! You're just trolling at this point")
        )
      )
    }
  }
}

private typealias Answer = CityPlacementAnswer

data class CityPlacementAnswer(val explanation: String, val isCorrect: Boolean = false)