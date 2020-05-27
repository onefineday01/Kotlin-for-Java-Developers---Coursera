package nicestring

fun String.isNice(): Boolean {
    if(isNullOrEmpty()) return false

    var count = 0

    if (notContainsbubabe(this)) count++
    if (containsVowels(this)) count++
    if (containsLetters(this)) count++

    return count >= 2
}

fun notContainsbubabe(text: String): Boolean = (!text.contains("bu") &&
        !text.contains("ba") &&
        !text.contains("be"))

fun containsVowels(text: String): Boolean {
    var ans = 0
    text.forEach {
        if (it == 'a' || it == 'e' || it == 'i' || it == 'o' || it == 'u') {
            ans++
        }
    }
    return ans >= 3
}

fun containsLetters(text: String): Boolean {
    for (i in 1 until text.length) {
        if (text[i] == text[i-1])
            return true
    }
    return false
}