import kotlin.system.measureTimeMillis

class SolveDf(private val main: Main) {

    private var statusDf = "fail"
    private var triesDf = 0

    fun solveDf() {
        val time = measureTimeMillis {
            val cubes = hashSetOf<Cube>()
            cubes.addAll(main.cubes)
            main.cubes.find { it.point == main.startPoint }?.let {
                move(Step(cube = it, stage = cubes))
            }
        }
        println("Solve Df status $statusDf. attempts $triesDf. time ${time}ms")
    }


    private val steps = mutableListOf<Step>()
    private val stepList = mutableListOf<Cube>()
    private val stepsList = mutableListOf<List<Cube>>()
    private fun move(step: Step) {
        stepList.add(step.cube)
        triesDf++
        step.cube.mass--
        step.stage.find { it.point == step.cube.point }?.let { it.mass-- }

        if (step.stage.find { _it -> _it.mass > 0 } == null && step.cube.point == main.endPoint) {
            statusDf = "Success"
            return
        }
        if (step.cube.mass != 0 && step.cube.point == main.endPoint)
            return
//Solve Df status Success. attempts 182574. time 3089ms
//Solve Df status Success. attempts 182574. time 2933ms
//Solve Df status Success. attempts 91449. time 9291ms
//Solve Df status Success. attempts 54240. time 2972ms
//Solve Df status Success. attempts 17250. time 578ms
        val childes = getNextSteps(step.cube, step.stage)
        if (stepList.size == 2 && stepsList.find { it.containsAll(stepList) } != null) {
            return
        }
        if (childes.isEmpty()) {
            stepsList.add(stepList.toList())
            stepList.clear()
        }
        childes.forEach { child ->
            val newList = step.stage.map { _it -> Cube(_it.mass, _it.point) }.toHashSet()
            val nextStep = Step(child, newList)
            steps.add(nextStep)
            move(nextStep)
        }
    }

    /*
     if (steps.find {
      it.cube.point.x == child.point.x &&
      it.cube.point.x == child.point.x &&
      it.cube.mass == child.mass && compareTwoList(it.stage,newList)
      } == null) {
x    */

 /*   fun compareTwoList(cubes1: HashSet<Cube>, cubes2: HashSet<Cube>): Boolean {
        var match = true
        cubes1.forEach { cube1 ->
            cubes2.forEach { cube2 ->
                match =
                    cube1.point.x == cube2.point.x && cube1.point.x == cube2.point.x && cube1.mass == cube2.mass && match
            }
        }
        return match
    }*/
}