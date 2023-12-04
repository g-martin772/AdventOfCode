import java.io.File
import java.io.InputStream

val Cards = mutableMapOf<Int, Int>()

fun main(args: Array<String>) {
    val inputStream: InputStream = File("input").inputStream()

    var result = 0;

    inputStream.bufferedReader().forEachLine {
        if (it.isNotEmpty())
            findWins(it);
    }

    for (card in Cards) {
        result += calculateCard(card.key, card.value)
    }

    println(result)
}

fun calculateCard(cardNumber: Int, cardWins: Int): Int {
    var sum = 1
    for (i in 1..cardWins) {
        sum += calculateCard(cardNumber + i, Cards[cardNumber + i]!!)
    }
    return sum;
}

fun findWins(line: String) {
    val data = line.split(": ")[1]
    val gameNumber = line.split(": ")[0].split("\\s+".toRegex())[1].toInt()
    val winNumbersString = data.split("|")[0]
    val hasNumbersString = data.split("|")[1]

    val winNumbers = mutableListOf<Int>()
    val hasNumbers = mutableListOf<Int>()

    winNumbersString.split(" ").forEach { num ->
        if (num.isNotEmpty())
            winNumbers.add(num.toInt())
    }

    hasNumbersString.split(" ").forEach { num ->
        if (num.isNotEmpty())
            hasNumbers.add(num.toInt())
    }

    var lineResult: Int = 0;

    hasNumbers.forEach { num ->
        if (winNumbers.contains(num))
            lineResult++;
    }

    Cards[gameNumber] = lineResult
}