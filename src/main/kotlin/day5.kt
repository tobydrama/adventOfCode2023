import java.io.File

fun main() {
    val day5 = Day5()
    day5.taskDayFive(File("day5.txt").readLines())
    day5.taskDayFivePart2(File("day5.txt").readLines())
}
class Day5{
    fun taskDayFive(input:List<String>){
        val seeds = getSeeds(input[0])
        val seedToSoil = getMap(input, "seed-to-soil")
        val soilToFertilizer = getMap(input, "soil-to-fertilizer")
        val fertilizerToWater = getMap(input, "fertilizer-to-water")
        val waterToLight = getMap(input, "water-to-light")
        val lightToTemperature = getMap(input, "light-to-temperature")
        val temperatureToHumidity = getMap(input, "temperature-to-humidity")
        val humidityToLocation = getMap(input, "humidity-to-location")

        val listOfNums = listOf(
            seedToSoil.second.map { getRange(it) },
            soilToFertilizer.second.map { getRange(it) },
            fertilizerToWater.second.map { getRange(it) },
            waterToLight.second.map { getRange(it) },
            lightToTemperature.second.map { getRange(it) },
            temperatureToHumidity.second.map { getRange(it) },
            humidityToLocation.second.map { getRange(it) },
        ).reversed()

        val locations = seeds.second.map {
            listOfNums.foldRight(it){ pairs, acc: Long -> findNumInListOfPairs(acc, pairs)  }
        }
        println("lowest location is ${locations.minOrNull()?.minus(1)}")
    }

    fun taskDayFivePart2(input:List<String>){
        val seeds = getFirstLinePairs(input[0])
        val seedToSoil = getMap(input, "seed-to-soil")
        val soilToFertilizer = getMap(input, "soil-to-fertilizer")
        val fertilizerToWater = getMap(input, "fertilizer-to-water")
        val waterToLight = getMap(input, "water-to-light")
        val lightToTemperature = getMap(input, "light-to-temperature")
        val temperatureToHumidity = getMap(input, "temperature-to-humidity")
        val humidityToLocation = getMap(input, "humidity-to-location")

        val listOfNums = listOf(
            seedToSoil.second.map { getRange(it) },
            soilToFertilizer.second.map { getRange(it) },
            fertilizerToWater.second.map { getRange(it) },
            waterToLight.second.map { getRange(it) },
            lightToTemperature.second.map { getRange(it) },
            temperatureToHumidity.second.map { getRange(it) },
            humidityToLocation.second.map { getRange(it) },
        ).reversed()

        var lowestNum = Long.MAX_VALUE
        seeds.forEachIndexed{ index , it ->
            val rangeOfSeeds = (it.first .. it.first+it.second)
            println(index)
            rangeOfSeeds.forEach { num->
                val tempInt = listOfNums.foldRight(num){ pairs, acc: Long -> findNumInListOfPairs(acc, pairs)  }
                if (tempInt<lowestNum){
                    lowestNum=tempInt
                }
            }
        }
        println("lowest location is $lowestNum")
    }


    private fun findNumInListOfPairs(num: Long, listOfPairs: List<Pair<LongRange, LongRange>>): Long {
        val listOfDestinationRanges = listOfPairs.map { it.first }
        val listOfSourceRanges = listOfPairs.map { it.second }
        return if (listOfSourceRanges.any { it.contains(num) }) {
            val indexOfRange = findIndex(num, listOfSourceRanges)
            val indexOfNum = (num - listOfSourceRanges[indexOfRange].first).toInt()
            return listOfDestinationRanges[indexOfRange].first + indexOfNum
        }else{
            num
        }
    }

    private fun findIndex(num: Long, listOfRanges: List<LongRange>):Int{
        for ((index, range) in listOfRanges.withIndex()){
            if (num < range.first || num > range.last){
                continue
            } else if (range.contains(num)){
                return index
            }
        }
        throw Exception("fuuuuuuuck")
    }

    private fun getSeeds(line:String): Pair<String, List<Long>> {
        return Pair("seeds", line.dropWhile { it != ':' }.drop(2).split(" ").map { it.toLong() })
    }

    private fun getFirstLinePairs(input: String): List<Pair<Long, Long>> {
        val listOfLongs = input.dropWhile { !it.isDigit() }.split(" ").map { it.toLong() }
        val listOfSeedId = listOfLongs.filterIndexed { index, _ -> index % 2 == 0 }
        val listOfSeedRange = listOfLongs.filterIndexed { index, _ -> index % 2 != 0 }
        return List(listOfSeedId.size) { index -> Pair(listOfSeedId[index], listOfSeedRange[index]) }
    }

    private fun getMap(input:List<String>, mapName: String): Pair<String, List<Triple<Long, Long, Long>>> {
        val list = input.dropWhile { !it.contains(mapName) }.drop(1).takeWhile { it.length > 1 }
        val listOfInt = list.map { it.split(" ").map { it.toLong() } }.map { Triple(it[0],it[1], it[2]) }
        return Pair(mapName, listOfInt)
    }

    private fun getRange(nums: Triple<Long, Long, Long>): Pair<LongRange,LongRange>{
        val destinationRangeStart = nums.first
        val sourceRangeStart = nums.second
        val rangeLength = nums.third

        val destinationRange = (destinationRangeStart..(destinationRangeStart+rangeLength))
        val sourceRange = (sourceRangeStart .. (sourceRangeStart+rangeLength))
        return Pair(destinationRange,sourceRange)
    }
}