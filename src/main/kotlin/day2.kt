import java.io.File

fun main() {
    val day2 = Day2()
    day2.taskDayTwo(File("day2.txt").readLines())
}
class Day2{
fun taskDayTwo(input:List<String>){
    val partOneMap = input.map {
        getGameId(it) to getHigestColors(it.dropWhile { char -> char != ':' }.drop(2))
    }
    val validRed = 12
    val validBlue = 14
    val validgreen = 13

    val validGames = partOneMap.filter {
        it.second[0].second <= validgreen &&
        it.second[1].second <= validRed &&
        it.second[2].second <= validBlue
    }

    val sumValidGames = validGames.sumOf { it.first }
    println("The sum of valid game id's is: $sumValidGames")

    val partTwoMap = input.map {
        getGameId(it) to getHigestColors(it.dropWhile { char -> char != ':' }.drop(2))
    }
    val powerGame = partTwoMap.map { it.second[0].second * it.second[1].second * it.second[2].second }.sumOf { it }
    println("The sum of the power of games is: $powerGame")

}

fun getGameId(input: String): Int{
    return input.drop(5).takeWhile { it != ':' }.toInt()
}

fun getHigestColors(input: String): List<Pair<String, Int>>{
    val splittGame = input.split("; ")
    val splittList = splittGame.flatMap { it.split(", ") }
    //println(splittList)
    val green = splittList.filter { it.contains("green") }.map { getNum(it) }.maxOrNull()!!
    val red = splittList.filter { it.contains("red") }.map { getNum(it) }.maxOrNull()!!
    val blue = splittList.filter { it.contains("blue") }.map { getNum(it) }.maxOrNull()!!

    return listOf(Pair("green", green), Pair("red", red), Pair("blue", blue))
}

fun getNum(input: String): Int{
    return input.takeWhile { it.isDigit() }.toInt()
}
}

