import java.io.File

fun main() {
    val day8 = Day8()
    //day8.task(File("day8.txt").readLines())
    day8.taskPart2(File("day8.txt").readLines())

}

class Day8 {

    fun task(input: List<String>) {
        val instructions = getInstructions(input)
        val map = getMap(input)
        val steps = findStepsFormKeyToKey("AAA", "ZZZ", instructions, map)
        println("num of steps from AAA to ZZZ is $steps")
    }

    fun taskPart2(input: List<String>) {
        val instructions = getInstructions(input)
        val map = getMap(input)
        val startNodes = findAllStartNodes(map)
        val lengthToz = startNodes.map {
            findStepsFormKeyToKeyPart2(it.key, instructions, map)
        }.map { it.toLong() }
        val steps =lengthToz.reduceRight{num, acc -> lcm(num, acc)}
        println(steps)
    }

    private fun findAllStartNodes(map: Map<String, Pair<String, String>> ):Map<String, Pair<String, String>>{
        return map.filter { it.key.last()=='A' }
    }

    private fun lcm(a: Long, b: Long): Long {
        var ma = a
        var mb = b
        var remainder: Long

        while (mb != 0L) {
            remainder = ma % mb
            ma = mb
            mb = remainder
        }

        return a * b / ma
    }

    private fun findStepsFormKeyToKeyPart2(keyFrom: String, instructions:String, map:  Map<String, Pair<String, String>>): Int{
        var num = 0
        var currentKey=keyFrom
        while (true){
            instructions.forEach {
                num++
                currentKey = if (it=='L'){
                    map[currentKey]?.first ?: throw Exception("aaaaaaa")
                }else{
                    map[currentKey]?.second ?: throw Exception("aaaaaaa2")
                }

                if(currentKey.endsWith('Z')){
                    return num
                }
            }
        }
    }

    private fun findStepsFormKeyToKey(keyFrom: String, keyTo: String, instructions:String, map:  Map<String, Pair<String, String>>): Int{
        var num = 0
        var currentKey=keyFrom
        while (true){
            instructions.forEach {
                num++
                currentKey = if (it=='L'){
                    map[currentKey]?.first ?: throw Exception("aaaaaaa")
                }else{
                    map[currentKey]?.second ?: throw Exception("aaaaaaa2")
                }

                if(currentKey == keyTo){
                    return num
                }
            }
        }
    }

    private fun getInstructions(input: List<String>):String{
        return input[0]
    }

    private fun getMap(input: List<String>):Map<String, Pair<String, String>>{
        val mapInput = input.drop(2)
        val map = mapInput.associate {
            it.takeWhile { char -> char != ' ' } to Pair(
                it.dropWhile { char -> char != '(' }.drop(1).takeWhile { char -> char != ',' },
                it.takeLastWhile { char -> char != ' ' }.dropLast(1)
            )
        }
        return map
    }
}
