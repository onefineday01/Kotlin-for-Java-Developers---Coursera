package board

fun createSquareBoard(width: Int): SquareBoard = object
    : SquareBoard {

    private var _table: Array<Array<Cell>>? = null
    val table: Array<Array<Cell>>
        get() {
            var matrix = arrayOf<Array<Cell>>()
            if (_table == null) {
                for (i in 1..this.width) {
                    var row = arrayOf<Cell>()
                    for (j in 1..this.width) {
                        row += Cell(i, j)
                    }
                    matrix += row
                }
                _table = matrix
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }

    override val width: Int
        get() = width


    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (i in 1..width && j in 1..width) {
            table[i - 1][j - 1]
        } else {
            null
        }
    }

    override fun getCell(i: Int, j: Int): Cell {
        if (i in 1..width && j in 1..width) {
            return table[i - 1][j - 1]
        } else {
            throw IllegalArgumentException()
        }
    }

    override fun getAllCells(): Collection<Cell> {
        val list = mutableListOf<Cell>()
        for (i in 1..width) {
            for (j in 1..width) {
                list.add(table[i - 1][j - 1])
            }
        }
        return list
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val list = mutableListOf<Cell>()
        val iterator = IntProgression.fromClosedRange(jRange.first, if (jRange.last < width) jRange.last else width, jRange.step)
        for (j in iterator) {
            list.add(table[i - 1][j - 1])
        }
        return list
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val list = mutableListOf<Cell>()
        val iterator: IntProgression = IntProgression.fromClosedRange(iRange.first, if (iRange.last < width) iRange.last else width, iRange.step)
        for (i in iterator) {
            list.add(table[i - 1][j - 1])
        }
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        if (width == 1) return null
        var cell: Cell? = null
        when (direction) {
            Direction.DOWN -> {
                if (i < width) cell = table[i][j - 1]

            }
            Direction.LEFT -> {
                if (j > 1) cell = table[i - 1][j - 2]
            }

            Direction.RIGHT -> {
                if (j < width) cell = table[i - 1][j]
            }

            Direction.UP -> {
                if (i > 1) cell = table[i - 2][j - 1]
            }
        }
        return cell
    }

}


fun <T> createGameBoard(width: Int): GameBoard<T> = object : GameBoard<T> {

    private var _table: Array<Array<CellWithInformation<T>>>? = null
    val table: Array<Array<CellWithInformation<T>>>
        get() {
            var matrix = arrayOf<Array<CellWithInformation<T>>>()
            val t: T? = null
            if (_table == null) {

                for (i in 1..this.width) {
                    var row = arrayOf<CellWithInformation<T>>()
                    for (j in 1..this.width) {
                        row += CellWithInformation(Cell(i, j), t)
                    }
                    matrix += row
                }
                _table = matrix
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }

    override val width: Int
        get() = width


    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (i in 1..width && j in 1..width) {
            table[i - 1][j - 1].cell
        } else {
            null
        }
    }

    override fun getCell(i: Int, j: Int): Cell {
        print(table)
        if (i in 1..width && j in 1..width) {
            return table[i - 1][j - 1].cell
        } else {
            throw IllegalArgumentException()
        }
    }

    override fun getAllCells(): Collection<Cell> {
        val list = mutableListOf<Cell>()
        for (i in 1..width) {
            for (j in 1..width) {
                list.add(table[i - 1][j - 1].cell)
            }
        }
        return list
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val list = mutableListOf<Cell>()
        val iterator = IntProgression.fromClosedRange(jRange.first, if (jRange.last < width) jRange.last else width, jRange.step)
        for (j in iterator) {
            list.add(table[i - 1][j - 1].cell)
        }
        return list
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val list = mutableListOf<Cell>()
        val iterator: IntProgression = IntProgression.fromClosedRange(iRange.first, if (iRange.last < width) iRange.last else width, iRange.step)
        for (i in iterator) {
            list.add(table[i - 1][j - 1].cell)
        }
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        if (width == 1) return null
        var cell: Cell? = null
        when (direction) {
            Direction.DOWN -> {
                if (i < width) cell = table[i][j - 1].cell

            }
            Direction.LEFT -> {
                if (j > 1) cell = table[i - 1][j - 2].cell
            }

            Direction.RIGHT -> {
                if (j < width) cell = table[i - 1][j].cell
            }

            Direction.UP -> {
                if (i > 1) cell = table[i - 2][j - 1].cell
            }
        }
        return cell
    }

    override fun get(cell: Cell): T? {
        return table[cell.i - 1][cell.j - 1].t
    }

    override fun set(cell: Cell, value: T?) {
        table[cell.i - 1][cell.j - 1].t = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        val list = mutableListOf<Cell>()
        var cell: Cell
        for (i in 1..width) {
            for (j in 1..width) {
                cell = table[i - 1][j - 1].cell
                if (predicate(table[i - 1][j - 1].t))
                    list.add(cell)
            }
        }
        return list
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        for (i in 1..width) {
            for (j in 1..width) {
                if (predicate(table[i - 1][j - 1].t)) return table[i - 1][j - 1].cell
            }
        }
        return null
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        for (i in 1..width) {
            for (j in 1..width) {
                if (predicate(table[i - 1][j - 1].t)) return true
            }
        }
        return false
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        for (i in 1..width) {
            for (j in 1..width) {
                if (!predicate(table[i - 1][j - 1].t)) return false
            }
        }
        return true
    }

}