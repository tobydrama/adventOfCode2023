import java.io.File
import java.util.*
import kotlin.math.pow

fun main() {
    val day4 = Day4()
    val testList = listOf(
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
    )
    day4.taskDayFour(File("day4.txt").readLines())
    day4.taskDayFourPart2(File("day4.txt").readLines())

}
class Day4{
    fun taskDayFour(input:List<String>){
        val cards = input.map {
             getNum(it) to Pair(getWinningNums(it), getGameNums(it))
        }
        val pointsPerCard = cards.map {
            it.first to getPoints(it.second.first, it.second.second)
        }
        println("Sum of points from cards is: ${pointsPerCard.sumOf { it.second }}")

    }
    fun taskDayFourPart2(input:List<String>){
        val cards = input.map {
            getNum(it) to Pair(getWinningNums(it), getGameNums(it))
        }
        val numOfCards = mutableMapOf<Int, Int>()
        cards.forEach { numOfCards[it.first] = 1 }
        cards.forEachIndexed { indexCards, it ->
            val intersect = it.second.second.intersect(it.second.first.toSet())
            intersect.forEachIndexed { index, _ ->
                if (it.first+index+1 <=cards.size) {
                    val cardsUpdateIndex = it.first+index+1
                    val cardNum = numOfCards[cardsUpdateIndex]!! + numOfCards[indexCards+1]!!
                    numOfCards[cardsUpdateIndex] = cardNum
                }
            }
        }
        val totalNum = numOfCards.toList().sumOf { it.second }
        println("Part 2 sum is: $totalNum")
    }

    fun getPoints(winningNums: List<Int>, playingNums: List<Int>):Int{
        val intersect = playingNums.intersect(winningNums.toSet())
        return if (intersect.isEmpty()){
            0
        }else{
            2.0.pow(intersect.size-1).toInt()
        }
    }


    fun getWinningNums(input: String):List<Int>{
        val listOfWinningNums = input.dropWhile { it != ':' }.dropLastWhile { it != '|' }.split(" ").filter { it.toIntOrNull() != null }
        return listOfWinningNums.map { it.toInt() }
    }

    fun getGameNums(input: String):List<Int>{
        val getGameNums = input.dropWhile { it != '|' }.split(" ").filter { it.toIntOrNull() != null }
        return getGameNums.map { it.toInt() }
    }

    fun getNum(input: String):Int{
        val num = input.dropWhile { !it.isDigit() }.takeWhile { it.isDigit() }
        return num.toInt()
    }

}
