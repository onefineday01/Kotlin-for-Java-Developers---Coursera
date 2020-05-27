package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var right = 0
    var wrong = 0
    var guessChange = guess
    var secretChange = secret
    var index: Int
    for (i in secretChange.indices) {
        if (guessChange[i] == secretChange[i]) {
            right++
            guessChange = guessChange.substring(0, i) + guessChange[i].toLowerCase() + guessChange.substring(i + 1)
            secretChange = secretChange.substring(0, i) + secretChange[i].toLowerCase() + secretChange.substring(i + 1)
        }
    }

    for (i in guessChange.indices) {
        if (secretChange.contains(guessChange[i]) &&
                guessChange[i] != secretChange[i]) {

            wrong++
            index = secretChange.indexOf(guessChange[i])
            secretChange = secretChange.substring(0, index) + secretChange[index].toLowerCase() + secretChange.substring(index + 1)
        }
    }
    return Evaluation(right, wrong)
}
