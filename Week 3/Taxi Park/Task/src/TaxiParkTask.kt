package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {
    val list = allDrivers.toMutableSet()
    trips.forEach {
        if (list.contains(it.driver)) {
            list.remove(it.driver)
        }
    }
    return list
}


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    var list = mutableSetOf<Passenger>()
    allPassengers.forEach { passenger ->
        var passengerTrips = 0
        trips.forEach { trips ->
            if (trips.passengers.contains(passenger)) {
                passengerTrips++
            }
        }
        if (minTrips <= passengerTrips) list.add(passenger)
    }

    return list
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    var count = 0
    var list = allPassengers.associate { Pair(it, 0) }.toMutableMap()

    trips.forEach { trip ->
        if (trip.driver == driver) {
            trip.passengers.forEach { passenger ->
                count = list[passenger]!!.plus(1)
                list.put(passenger, count)

            }
        }
    }

    var result = mutableSetOf<Passenger>()
    list.forEach {
        if (it.value > 1) result.add(it.key)
    }

    return result
}


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    var count = 0
    var list = allPassengers.associate { Pair(it, 0) }.toMutableMap()

    trips.forEach { trip ->
        trip.passengers.forEach { passenger ->
            if (trip.cost < trip.distance + trip.duration) {
                count = list[passenger]!!.plus(1)
            } else {
                count = list[passenger]!!.minus(1)
            }
            list.put(passenger, count)
        }
    }

    var result = mutableSetOf<Passenger>()
    list.forEach {
        if (it.value > 0) result.add(it.key)
    }

    return result
}


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    fun asDurationRange(duration: Int): IntRange {
        val lowerBound = ((duration / 10) * 10)
        val upperBound = lowerBound + 9
        return lowerBound.rangeTo(upperBound)
    }

    fun mostFrequent(ls: List<IntRange>): IntRange? =
            ls.groupBy { it }
                    .mapValues { it.value.size }
                    .maxBy { it.value }?.key

    val durationRanges = this.trips.map { asDurationRange(it.duration) }
    return mostFrequent(durationRanges)
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {

    if (trips.isEmpty()) return false

    var totalCost = 0.0
    var map = allDrivers.associate { Pair(it, 0.0) }.toMutableMap()
    trips.forEach {
        totalCost += it.cost
        map[it.driver] = map[it.driver]!!.plus(it.cost)
    }

    val list = map.toList().sortedBy { it.second }
    var twenty: Int = (allDrivers.size * 0.2).toInt()
    var twentyTotalCost = 0.0
    for (i in list.lastIndex downTo (list.lastIndex - twenty + 1)) {
        twentyTotalCost += list[i].second
    }

    return twentyTotalCost >= totalCost * 0.8
}