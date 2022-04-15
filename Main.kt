package cinema
const val FULL_PRICE = 10
const val DISCOUNT = 8

fun printSeatMap(room: MutableList<MutableList<Char>>) {
    println("Cinema:")
    for (i in 0 .. room.lastIndex) {
        for (elem in room[i]) {
            print("$elem ")
        }
        print("\n")
    }
}

fun getRightAmount(seatsNum: Int, rowsNum: Int, y: Int): Int {
    val evenRowsNum = if (rowsNum % 2 != 0) rowsNum + 1 else rowsNum
    return if (rowsNum * seatsNum < 60) {
        FULL_PRICE
    } else {
        if (y < evenRowsNum / 2) FULL_PRICE else DISCOUNT
    }
}

fun buyTicket(room: MutableList<MutableList<Char>>, seatsNum: Int, rowsNum: Int): MutableList<MutableList<Char>> {
    while (true) {
        println("Enter a row number:")
        val y = readln().toInt()
        println("Enter a seat number in that row:")
        val x = readln().toInt()
        try {
            if (room[y][x] == 'B') {
                println("That ticket has already been purchased!")
                continue
            }
        }
        catch (e: IndexOutOfBoundsException) {
            println("Wrong Input!")
            continue
        }
        room[y][x] = 'B'
        println("Ticket price: \$${getRightAmount(seatsNum, rowsNum, y)}")
        break
    }
    return room
}

fun printStats(room: MutableList<MutableList<Char>>, seatsNum: Int, rowsNum: Int) {
    var ticketSold: Int = 0
    var currentIncome: Int = 0
    var totalIncome: Int = 0
    for (i in 1 .. room.lastIndex) {
        for (j in 1..room[i].lastIndex) {
            if (room[i][j] == 'B') {
                ticketSold++
                currentIncome += getRightAmount(seatsNum, rowsNum, i)
            }
            else totalIncome += getRightAmount(seatsNum, rowsNum, i)
        }
    }
    totalIncome += currentIncome
    val percentage: Double = (ticketSold * 100) / (seatsNum.toDouble() * rowsNum.toDouble())
    val formatPercentage = "%.2f".format(percentage)
    println("Number of purchased tickets: $ticketSold")
    println("Percentage: $formatPercentage%")
    println("Current income: \$$currentIncome")
    println("Total income: \$$totalIncome")
}

fun getChoice(room: MutableList<MutableList<Char>>, rowsNum: Int, seatsNum: Int) {
    var newRoom = room
    do {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        val choice = readln()
        when (choice) {
            "1" -> printSeatMap(newRoom)
            "2" -> newRoom = buyTicket(newRoom, seatsNum, rowsNum)
            "3" -> printStats(newRoom, seatsNum, rowsNum)
        }
    } while (choice != "0")
}

fun fillRoom(seatsNum: Int, rowsNum: Int): MutableList<MutableList<Char>> {
    val room = MutableList(rowsNum + 1) {MutableList(seatsNum + 1) {' '} }
    for (i in 1 .. seatsNum) {
        room[0][i] = i.digitToChar()
    }
    for (i in 1 .. rowsNum) {
        room[i][0] = i.digitToChar()
        for (j in 1 .. seatsNum) {
            room[i][j] = 'S'
        }
    }
    return room
}

fun main() {
    println("Enter the number of rows:")
    val rowsNum = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsNum = readln().toInt()
    val room = fillRoom(seatsNum, rowsNum)
    getChoice(room, rowsNum, seatsNum)
}
