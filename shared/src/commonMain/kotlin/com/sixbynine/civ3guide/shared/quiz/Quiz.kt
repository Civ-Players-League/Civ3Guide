package com.sixbynine.civ3guide.shared.quiz

import com.sixbynine.civ3guide.shared.MR.images
import dev.icerock.moko.resources.ImageResource

data class Quiz(
  val image: ImageResource,
  val question: String,
  val answers: List<QuizAnswer>
) {

  constructor(image: ImageResource, question: String, vararg answers: QuizAnswer) : this(
    image,
    question,
    answers.toList()
  )

  companion object {
    val all: List<Quiz> by lazy {
      listOf(
        Quiz(
          images.quiz0,
          "Why is this not the correct first worker move?",
          QuizAnswer(
            "Because the +1 food from irrigating is lost to the despotism penalty. I should mine instead.",
            explanation = null,
            isCorrect = true
          ),
          QuizAnswer(
            "Because the grassland along the river is higher priority, it produces more commerce.",
            "Commerce is good, but the shield from the bonus grassland is more important."
          ),
          QuizAnswer(
            "Because shields are more important than food. I should mine instead.",
            "Food is a bit more valuable than shields early game. But the food from irrigating is wasted due to the despotism penalty."
          ),
          QuizAnswer(
            "Because I should road before irrigating.",
            "Generally, it's better to mine or irrigate before roading. But here, the food from irrigating is wasted due to the despotism penalty."
          )
        ),
        Quiz(
          images.quiz1,
          "Why is irrigating this plains tile the wrong worker move?",
          QuizAnswer(
            "The bonus food is lost to the despotism penalty.",
            "Plains with wheat gives 3 food, 2 with the despotism penalty. So with irrigation, you get 4 food, 3 with the despotism penalty.",
          ),
          QuizAnswer(
            "It's better to mine the wheat, because shields are more important than food.",
            "Generally, food is more important than shields."
          ),
          QuizAnswer(
            "It's better to mine the wheat, because we already have more than enough food.",
            "Generally, food is a bit more important than shields. But with this land, food is abundant, shields are scarce. And this is one of the few tiles we can mine.",
            isCorrect = true
          ),
          QuizAnswer(
            "It's better to irrigate the flood plains.",
            "Wheat on plains is a better tile, and it's usually better to work your best accessible tile first. Plus, we need the shields it gives us, since shields are scarce here."
          )
        ),
        Quiz(
          images.quiz2,
          "Why is mining this cow the wrong move?",
          QuizAnswer(
            "It's better to irrigate, because food is more important early game than shields.",
            "Food is generally more important than shields early game, unless you have a lot of food or few shields.",
            isCorrect = true
          ),
          QuizAnswer(
            "It's better to irrigate, because the shields from mining will be lost to the despotism penalty.",
            "A cow gives 1 shield. So mining it gives 2 shields, no shield is lost."
          ),
          QuizAnswer(
            "It's better to mine the bonus grassland, because it's on a river and gives more commerce.",
            "We work the best tile first, and food and shields are much more important than commerce early game.",
          ),
          QuizAnswer(
            "It's better to road first! The shields from mining is lost to the despotism penalty, and you can't irrigate this tile because there's no fresh water.",
            "The shield wouldn't be lost. And because Seoul is on a river, all tile adjacent to Seoul can be irrigated."
          )
        )
      )
    }
  }
}

data class QuizAnswer(val text: String, val explanation: String?, val isCorrect: Boolean = false)

