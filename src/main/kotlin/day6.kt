import java.io.File

fun main() {
    val day6 = Day6()
    day6.task(File("day6.txt").readLines())
    day6.taskPart2(File("day6.txt").readLines())

}
class Day6{
    fun task(input:List<String>){
        val times = getNumes(input[0])
        val distance = getNumes(input[1])
        val betterNumOfBetterScores = times.mapIndexed{index, s -> getBetterDistance(s, distance[index]) }.map { it.size }
        val score = betterNumOfBetterScores.reduceRight{i, acc ->  i*acc}
        println("multiple sum of better scored is $score" )

    }
    fun taskPart2(input:List<String>){
        val time = getNumes(input[0]).map { it.toString() }.reduceRight{i, acc -> i+acc }.toLong()
        val distance = getNumes(input[1]).map { it.toString() }.reduceRight{i, acc -> i+acc }.toLong()
        val betterWinnings = getBetterDistancePart2(time, distance).size
        println("you can win $betterWinnings")
    }

    private fun getBetterDistancePart2(time: Long, currentDistance:Long):List<Long>{
        val listOfLong = mutableListOf<Long>()
        for (num in 1..time){
            val timeRemaining = time - num
            if (num*timeRemaining>currentDistance){
                listOfLong.add(num)
            }
        }
        return listOfLong
    }

    private fun getBetterDistance(time: Int, currentDistance:Int):List<Int>{
        val listOfInt = mutableListOf<Int>()
        for (num in 1..time){
            val timeRemaining = time - num
            if (num*timeRemaining>currentDistance){
                listOfInt.add(num)
            }
        }
        return listOfInt
    }

    private fun getNumes(line: String): List<Int>{
        return line.dropWhile { !it.isDigit() }.split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
    }
}