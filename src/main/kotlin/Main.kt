import java.util.*
import kotlin.collections.HashSet

data class Point(var x: Int, var y: Int)
data class Cube(var mass: Int, var point: Point, var price: Int = 1, var visited: Boolean = false)
data class Player(var point: Point)
data class Stage(var maxX: Int, var maxY: Int)
data class Step(
    val cube: Cube, val stage: HashSet<Cube>, var price: Int = 0
)


class Main(
    val stage: Stage, val startPoint: Point, val endPoint: Point, val customCubes: HashSet<Cube>
) {
    var cubes = hashSetOf<Cube>()
    private var user: Player


    init {
        setStage()
        user = Player(startPoint)
    }

    private fun setStage() {
        for (x in 0..stage.maxX) {
            for (y in 0..stage.maxY) {
                var cube = customCubes.find { e -> e.point.x == x && e.point.y == y }
                if (cube != null) {
                    cubes.add(cube)
                } else if (startPoint.x == x && startPoint.y == y) cubes.add(Cube(0, Point(x, y)))
                else cubes.add(Cube(1, Point(x, y)))
            }
        }
    }


    fun available(cube: Cube): Boolean {
        cube.apply {
            return if (point.x < 0 || point.y < 0) false
            else if (point.x > stage.maxX || point.y > stage.maxY) false
            else mass > 0
        }
    }

    private fun move(x: Int, y: Int) {
        cubes.find { e -> e.point.x == x && e.point.y == y }?.let { cube ->
            if (available(cube)) {
                user.point = cube.point
                cube.mass--
            }
        }
    }

    fun moveRight() {
        move(user.point.x + 1, user.point.y)
    }

    fun moveLeft() {
        move(user.point.x - 1, user.point.y)
    }

    fun moveTop() {
        move(user.point.x, user.point.y + 1)
    }

    fun moveBottom() {
        move(user.point.x, user.point.y - 1)
    }

    fun gameStatus(): String {
        cubes.filter { e -> e.point.x != endPoint.x && e.point.y != endPoint.y }.find { e -> e.mass > 0 }?.let {
            return "Fail"
        }
        return "Success"
    }

    fun printStage() {
        for (y in stage.maxY downTo 0) {
            println("")
            for (x in 0..stage.maxX) {
                if (user.point.x == x && user.point.y == y) {
                    print("*")
                } else if (endPoint.x == x && endPoint.y == y) {
                    print("^")
                } else {
                    print(cubes.find { e -> e.point.x == x && e.point.y == y }?.mass)
                }
                print(" ")
            }
        }
    }


}


fun main() {
    println("max x, max y")
    val maxX = 5//readLine()!!.toInt()
    val maxY = 5//readLine()!!.toInt()
    println("startX, startY")
    val startX = 5//readLine()!!.toInt()
    val startY = 3//readLine()!!.toInt()
    println("endX, endY")
    val endX = 0//readLine()!!.toInt()
    val endY = 4//readLine()!!.toInt()
    var next = "2"
    val customCubes = hashSetOf<Cube>(
        Cube(0, Point(0, 5)),
        Cube(0, Point(5, 4)),
        Cube(0, Point(5, 5)),
        Cube(0, Point(5, 0)),
        Cube(2, Point(4, 1)),
        Cube(2, Point(1, 4))
    )
    while (next != "e") {
        println("custom cube x, y, mass, e to end")
        if (readLine().toString() == "e") break
        val x = readLine()!!.toInt()
        val y = readLine()!!.toInt()
        val mass = readLine()!!.toInt()
        customCubes.add(Cube(mass, Point(x, y)))
        println("press e to end add custom cubes")
        next = readLine().toString()
    }
    val main = Main(Stage(maxX, maxY), Point(startX, startY), Point(endX, endY), customCubes)
    while (next != "z") {
        main.printStage()
        println()
        println("Game started...")
        println("-z to end the game,\n-x Df solve,\n-u Pf solve,\n-m Uc solve,\n-g A* solve,\n-r restart game")
        next = readLine().toString()
        Solve(main)
        when (next) {
//            "w" -> main.moveTop()
//            "s" -> main.moveBottom()
//            "a" -> main.moveLeft()
//            "d" -> main.moveRight()
//            "p" -> main.printStage()
            "x" -> SolveDf(main).solveDf()
            "u" -> SolvePf(main).solveFd()
            "m" -> SolveUc(main).solveUc()
            "g" -> SolveA(main).solveA()
            "r" -> main()
        }
        // println("Game status ${main.gameStatus()}")
    }
}

