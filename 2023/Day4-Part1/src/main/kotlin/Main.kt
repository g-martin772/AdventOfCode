import java.io.File
import java.io.InputStream
import java.util.*

fun main(args: Array<String>) {
    val inputStream: InputStream = File("input").inputStream()

    var result: Int = 0;

    inputStream.bufferedReader().forEachLine {
        if (!it.isEmpty())
            result += findWins(it);
    }

    println(result)
}

fun findWins(line: String): Int {
    val data: String = line.split(": ")[1]
    val winNumbersString: String = data.split("|")[0]
    val hasNumbersString: String = data.split("|")[1]

    val winNumbers: MutableList<Int> = mutableListOf();
    val hasNumbers: MutableList<Int> = mutableListOf();

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
            if (lineResult == 0)
                lineResult = 1
            else
                lineResult *= 2
    }

    return lineResult
}