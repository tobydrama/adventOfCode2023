import java.io.File

fun main() {
    val day1 = Day1()
    day1.taskDayOne(File("day1.txt").readLines())
}
class Day1{
fun taskDayOne(input:List<String>){
    val numMapPartOne = input.map {
        findCalibrationNumPartOne(it)
    }
    val numMapPartTwo = input.map {
        findCalibrationNumPartTwo(it)
    }
    val sumPartOne = numMapPartOne.sum()
    val sumPartTwo = numMapPartTwo.sum()

    println("Sum of all Calibration Numbers for part one is: $sumPartOne")
    println("Sum of all Calibration Numbers for part two is: $sumPartTwo")
}

fun findCalibrationNumPartOne(input:String): Int{
    val firstNum = (input.dropWhile { !it.isDigit() }).first()
    val lastNum = (input.dropLastWhile { !it.isDigit() }).last()
    return "$firstNum$lastNum".toInt()
}

fun findCalibrationNumPartTwo(input:String): Int{
    val firstNum = firstDigit(input)
    val lastNum = lastDigit(input)
    return "$firstNum$lastNum".toInt()
}

val validLetters = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun firstDigit(input: String):Int{
    if (input[0].isDigit()){
        return input[0].digitToInt()
    }
    val key = validLetters.keys.firstOrNull { input.regionMatches(0, it,0, it.length) }
        ?: return firstDigit(input.drop(1))
    return validLetters.getValue(key)
}

fun lastDigit(input: String):Int{
    if (input[input.length-1].isDigit()){
        return input[input.length-1].digitToInt()
    }
    val key = validLetters.keys.firstOrNull { input.regionMatches(input.length-it.length, it,0, it.length) }
        ?: return lastDigit(input.dropLast(1))
    return validLetters.getValue(key)
}
}