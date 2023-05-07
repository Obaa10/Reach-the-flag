import kotlin.system.measureTimeMillis

class SolveUc(val main: Main) {


    private var statusUc = "fail"
    private var triesUc = 0

    fun solveUc() {
        val time = measureTimeMillis {
            val cubes = hashSetOf<Cube>()
            cubes.addAll(main.cubes)
            main.cubes.find { it.point == main.startPoint }?.let {
                move(Step(cube = it, stage = cubes))
            }
        }
        println("Solve Uc status $statusUc. attempts $triesUc. time ${time}ms")
    }


    private val stepList = mutableListOf<Cube>()
    private val others = mutableListOf<Step>()
    private val stepsList = mutableListOf<List<Cube>>()
    private fun move(step: Step) {
        stepList.add(step.cube)
        triesUc++
        step.cube.mass--
        step.stage.find { it.point == step.cube.point }?.let { it.mass-- }

        if (step.stage.find { _it -> _it.mass > 0 } == null && step.cube.point == main.endPoint) {
            statusUc = "Success"
            return
        }
        if (step.cube.mass != 0 && step.cube.point == main.endPoint)
            return
        val childes = getNextSteps(step.cube, step.stage)
        if (stepList.size == 2 && stepsList.find { it.containsAll(stepList) } != null) {
            return
        }
        if (childes.isEmpty()) {
            stepsList.add(stepList.toList())
            stepList.clear()
            return
        }

        val minChildPrice = childes.minBy { it.price }
        val newPrice = step.price + minChildPrice.price
        val theSmallestOne = others.find { it.price < newPrice }
        if (theSmallestOne == null) { //newPrice is the smallest one
            childes.forEach { child1 ->
                val newList = step.stage.map { _it -> Cube(_it.mass, _it.point) }.toHashSet()
                val nextStep = Step(child1, newList)
                move(nextStep)
            }
        } else {
            step.price = newPrice
            others.add(step)
            move(theSmallestOne)
        }
    }
}