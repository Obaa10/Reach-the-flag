import java.lang.Math.pow
import kotlin.math.sqrt


class Solve(_main: Main) {

    init {
        main = _main
    }

    companion object {
        lateinit var main: Main
    }
}

fun getNextSteps(cube: Cube, _cubes: HashSet<Cube>): MutableList<Cube> {
    val cubes = mutableListOf<Cube>()
    _cubes.find { it.point == Point(cube.point.x + 1, cube.point.y) }
        ?.let { if (Solve.main.available(it)) cubes.add(it) }
    _cubes.find { it.point == Point(cube.point.x - 1, cube.point.y) }
        ?.let { if (Solve.main.available(it)) cubes.add(it) }
    _cubes.find { it.point == Point(cube.point.x, cube.point.y + 1) }
        ?.let { if (Solve.main.available(it)) cubes.add(it) }
    _cubes.find { it.point == Point(cube.point.x, cube.point.y - 1) }
        ?.let { if (Solve.main.available(it)) cubes.add(it) }
    return cubes
}