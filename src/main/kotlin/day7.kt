import java.io.File
import java.util.Collections

fun main() {
    val day7 = Day7()
    day7.task(File("day7.txt").readLines())
    day7.taskPart2(File("day7.txt").readLines())

}

class Day7 {

    fun task(input: List<String>) {
        val handAndBet = input.map {
            getHand(it) to getBet(it)
        }
        val mapScore = handAndBet.map {
            it to getHandScoring(it.first)
        }
        val sortHands = mapScore.sortedWith(
            compareBy({ it.second },
                { getValueOfChar(it.first.first[0]) },
                { getValueOfChar(it.first.first[1]) },
                { getValueOfChar(it.first.first[2]) },
                { getValueOfChar(it.first.first[3]) },
                { getValueOfChar(it.first.first[4]) }
            )
        ).mapIndexed { index, it -> it.first to it.first.second * (index + 1) }
        println("sum of part 1: "+sortHands.sumOf { it.second })
    }

    fun taskPart2(input: List<String>) {
        val handAndBet = input.map {
            getHand(it) to getBet(it)
        }
        val mapScore = handAndBet.map {
            it to getHandScoringPart2(it.first)
        }

        val sortHands = mapScore.sortedWith(
            compareBy({ it.second },
                { getValueOfCharPart2(it.first.first[0]) },
                { getValueOfCharPart2(it.first.first[1]) },
                { getValueOfCharPart2(it.first.first[2]) },
                { getValueOfCharPart2(it.first.first[3]) },
                { getValueOfCharPart2(it.first.first[4]) }
            )
        ).mapIndexed { index, it -> it.first to it.first.second * (index + 1) }.mapIndexed{ index, it -> it to (index+1)}


        println("sum of part 2: "+sortHands.sumOf { it.first.second })
    }

    private fun getValueOfChar(char: Char): Int {
        val listOfChar = listOf(
            Pair('A', 14),
            Pair('K', 13),
            Pair('Q', 12),
            Pair('J', 11),
            Pair('T', 10),
            Pair('9', 9),
            Pair('8', 8),
            Pair('7', 7),
            Pair('6', 6),
            Pair('5', 5),
            Pair('4', 4),
            Pair('3', 3),
            Pair('2', 2)
        )
        val pair = listOfChar.find { it.first == char } ?: throw Exception("fuuuu")
        return pair.second
    }

    private fun getValueOfCharPart2(char: Char): Int {
        val listOfChar = listOf(
            Pair('A', 14),
            Pair('K', 13),
            Pair('Q', 12),
            Pair('T', 10),
            Pair('9', 9),
            Pair('8', 8),
            Pair('7', 7),
            Pair('6', 6),
            Pair('5', 5),
            Pair('4', 4),
            Pair('3', 3),
            Pair('2', 2),
            Pair('J', 1)
        )
        val pair = listOfChar.find { it.first == char } ?: throw Exception("fuuuu")
        return pair.second
    }

    private fun getHandScoringPart2(hand: String): Int {
        val listOfChar = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        val mapOfChars = listOfChar.map { Collections.frequency(hand.toList(), it) }
            .mapIndexed { index, it -> listOfChar[index] to it }.filter { it.second != 0 }
        val sortedMapOfChar = mapOfChars.sortedWith(
            compareBy({ it.second }, { getValueOfChar(it.first) }
            )
        )
        val newJokerCharMap =
            if (sortedMapOfChar[0].first == 'J' && sortedMapOfChar[0].second == 5) {
                sortedMapOfChar
            } else if (sortedMapOfChar.any { it.first == 'J' }) {
                val jokers = sortedMapOfChar.find { it.first == 'J' } ?: throw Exception("fuuuuu")
                val listWithoutJoker = sortedMapOfChar.filter { it.first != 'J' }
                val higestRated = listWithoutJoker.last()
                listWithoutJoker.dropLast(1) + (Pair(higestRated.first, higestRated.second + jokers.second))
            } else sortedMapOfChar
        return when {
            newJokerCharMap.any { it.second == 5 } -> 8
            newJokerCharMap.any { it.second == 4 } -> 7
            newJokerCharMap.any { it.second == 3 } && newJokerCharMap.any { it.second == 2 } -> 6
            newJokerCharMap.any { it.second == 3 } && newJokerCharMap.any { it.second == 2 } -> 5
            newJokerCharMap.any { it.second == 3 } && !newJokerCharMap.any { it.second == 2 } -> 4
            newJokerCharMap.count { it.second == 2 } == 2 -> 3
            newJokerCharMap.count { it.second == 2 } == 1 && !newJokerCharMap.any { it.second > 2 } -> 2
            else -> 1
        }
    }

    private fun getHandScoring(hand: String): Int {
        val listOfChar = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        val mapOfChars = listOfChar.map { Collections.frequency(hand.toList(), it) }
        return when {
            mapOfChars.any { it == 5 } -> 8
            mapOfChars.any { it == 4 } -> 7
            mapOfChars.any { it == 3 } && mapOfChars.any { it == 2 } -> 6
            mapOfChars.any { it == 3 } && mapOfChars.any { it == 2 } -> 5
            mapOfChars.any { it == 3 } && !mapOfChars.any { it == 2 } -> 4
            mapOfChars.count { it == 2 } == 2 -> 3
            mapOfChars.count { it == 2 } == 1 && !mapOfChars.any { it > 2 } -> 2
            else -> 1
        }
    }

    private fun getHand(line: String): String {
        return line.takeWhile { it != ' ' }
    }

    private fun getBet(line: String): Int {
        return line.dropWhile { it != ' ' }.drop(1).toInt()
    }
}
