package com.example.assignment1

import android.graphics.Color

class Climber {
    private var score: Int = 0
    fun climb(): Int {
        when(score){
            in 0..2 -> ++score
            in 3..7 -> climbGreen(score)
            in 9..15 -> climbRed(score)
        }
        return score
    }

    fun fall(): Int {
        if(score > 3){
            score -= 3
        }
        else {
            score = 0
        }
        return score
    }

    fun reset(): Int {
        score = 0
        return score
    }

    // Additional functions for calculating
    fun climbGreen(score: Int ): Int {
        this.score += 2
        return score
    }

    fun climbRed(score: Int): Int {
        this.score += 3
        return score
    }

    // FUnction setting color to the score text
    fun scoreColorManagement(): Int {
        return when(score) {
            in 1..3 -> Color.BLUE
            in 5..9 -> Color.GREEN

            in 12..18 -> Color.RED
            else -> Color.BLACK
        }
    }
}