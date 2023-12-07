import java.io.File
fun main() {
    val day3 = Day3()
    day3.taskDayThree(File("day3.txt").readLines())
    day3.taskDayThreePartTwo(File("day3.txt").readLines())
}
class Day3{

var num = 0
var hasSymbol = false
var listOfInts = mutableListOf<Int>()
fun taskDayThree(input: List<String>) {

    for ((indexOfLine, line) in input.withIndex()) {
        for ((indexOfChar, char) in line.withIndex()) {
            if (char.isDigit() && indexOfChar == line.length-1) {
                addNum(char.digitToInt())
                checkAroundChar(lineIndex = indexOfLine, charIndex = indexOfChar, input)
                addNumOrRest()

            } else if (char.isDigit()) {
                addNum(char.digitToInt())
                checkAroundChar(lineIndex = indexOfLine, charIndex = indexOfChar, input)
            } else {
                addNumOrRest()
            }
        }
    }
    val sumEngineParts = listOfInts.sumOf { it }
    println("Sum of engine parts is: $sumEngineParts")
}
    fun addNumOrRest(){
        if (hasSymbol && num > 0) {
            val tempInt = num
            listOfInts.add(tempInt)
            num = 0
            hasSymbol = false
        } else {
            num = 0
            hasSymbol = false
        }
    }


var num1Around = 0
var num2Around = 0
var hasGear = true
var listOfIntsPartTwo = mutableListOf<Int>()

fun taskDayThreePartTwo(input: List<String>) {
    for ((indexOfLine, line) in input.withIndex()) {
        for ((indexOfChar, char) in line.withIndex()) {
            if (char == '*') {
                hasGear = true
                checkAroundGear(lineIndex = indexOfLine, charIndex = indexOfChar, input)
            } else if (hasGear && num1Around > 0 && num2Around > 0) {
                val tempInt = num1Around * num2Around
                listOfIntsPartTwo.add(tempInt)
                num1Around = 0
                num2Around = 0
                hasGear = false
            } else {
                num1Around = 0
                num2Around = 0
                hasGear = false
            }
        }
    }
    val sumofGears = listOfIntsPartTwo.sumOf { it }
    println("Sum of gears is: $sumofGears")
}

fun checkAroundGear(lineIndex: Int, charIndex: Int, input: List<String>) {
    when (lineIndex) {
        0 -> {
            checkLinePartTwo(charIndex, input[0])
            checkLinePartTwo(charIndex, input[1])
        }
        input.size - 1 -> {
            checkLinePartTwo(charIndex, input[lineIndex])
            checkLinePartTwo(charIndex, input[lineIndex - 1])
        }
        else -> {
            checkLinePartTwo(charIndex, input[lineIndex])
            checkLinePartTwo(charIndex, input[lineIndex - 1])
            checkLinePartTwo(charIndex, input[lineIndex + 1])
        }
    }
}

fun checkLinePartTwo(charIndex: Int, line: String) {
    return when {
        charIndex == 0 && line[1].isDigit() -> putNum(getNum(1, line))
        charIndex == line.length - 1 && line[charIndex - 1].isDigit() -> putNum(getNum(charIndex - 1, line))
        (line[charIndex] == '*' || !line[charIndex].isDigit()) && line[charIndex - 1].isDigit() && line[charIndex + 1].isDigit() -> {
            putNum(getNum(charIndex - 1, line))
            putNum(getNum(charIndex + 1, line))
        }
        else -> {
            if (line[charIndex - 1].isDigit()) {
                putNum(getNum(charIndex - 1, line))
            } else if (line[charIndex + 1].isDigit()) {
                putNum(getNum(charIndex + 1, line))
            } else if (line[charIndex].isDigit()) {
                putNum(getNum(charIndex, line))
            } else {
            }
        }
    }
}

fun putNum(numAround: Int) {
    if (num1Around == 0) {
        num1Around = numAround
    } else {
        num2Around = numAround
    }
}

fun getNum(numIndex: Int, line: String): Int {
    var a = "" + line[numIndex]
    var currentIndex = numIndex + 1
    while (currentIndex < line.length && line[currentIndex].isDigit()) {
        a += "${line[currentIndex]}"
        currentIndex++
    }
    currentIndex = numIndex - 1
    while (currentIndex >= 0 && line[currentIndex].isDigit()) {
        a = "${line[currentIndex]}$a"
        currentIndex--
    }
    return a.toInt()
}

fun checkAroundChar(lineIndex: Int, charIndex: Int, input: List<String>) {
    val currentLine = input[lineIndex]
    if (lineIndex == 0) {
        val bellowLine = input[1]
        if (!hasSymbol) {
            hasSymbol = checkLine(charIndex, currentLine) || checkLine(charIndex, bellowLine, true)
        }
    } else if (lineIndex == input.size - 1) {
        val aboveLine = input[lineIndex - 1]
        if (!hasSymbol) {
            hasSymbol = checkLine(charIndex, currentLine) || checkLine(charIndex, aboveLine, true)
        }
    } else {
        val bellowLine = input[lineIndex + 1]
        val aboveLine = input[lineIndex - 1]
        if (!hasSymbol) {
            hasSymbol = checkLine(charIndex, currentLine) || checkLine(charIndex, aboveLine, true) || checkLine(
                charIndex,
                bellowLine,
                true
            )
        }
    }
}

fun checkLine(charIndex: Int, line: String, middle: Boolean = false): Boolean {
    return when {
        charIndex == 0 -> lookAtIndex(1, line)
        charIndex == line.length - 1 -> lookAtIndex(charIndex - 1, line)
        middle -> lookAtIndex(charIndex - 1, line) || lookAtIndex(charIndex, line) || lookAtIndex(charIndex + 1, line)
        else -> lookAtIndex(charIndex - 1, line) || lookAtIndex(charIndex + 1, line)
    }
}

fun lookAtIndex(indexNum: Int, line: String): Boolean {
    return !line[indexNum].isDigit() && line[indexNum] != '.'
}


fun addNum(newNum: Int) {
    num = if (num == 0) {
        newNum
    } else {
        "$num$newNum".toInt()
    }
}
}