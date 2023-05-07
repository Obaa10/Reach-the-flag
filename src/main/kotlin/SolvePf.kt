import kotlin.system.measureTimeMillis

class SolvePf(private val main: Main) {

    private var statusFd = "fail"
    private var triesPf = 0
    fun solveFd() {
        val time = measureTimeMillis {
            val startPoint = main.cubes.find { it.point == main.startPoint }!!
            down(mutableListOf(Step(startPoint, main.cubes)).toHashSet())
        }
        println("Solve Fd status $statusFd. attempts $triesPf. time ${time}ms")
    }

    private val childesNextSteps = mutableSetOf<Step>()
    private val badStep = mutableListOf<HashSet<Point>>()
    private var stageDone = false
    private var nextStepStage = listOf<Cube>()
    private var skipCounter = 0
    private fun down(nextSteps: HashSet<Step>) {
        childesNextSteps.clear()
        //println(nextSteps.size)
        triesPf++
        //println("badStep.size ${badStep.size}")
        //println("skipCounter $skipCounter")
        for (step in nextSteps) {
            triesPf++
            step.stage.find { _it -> _it == step.cube }?.let { _it ->
                _it.mass--
                _it.visited = true
            }
            step.cube.mass--
            step.cube.visited = true
            stageDone = step.stage.find { _it -> _it.mass > 0 } == null
            if (stageDone && step.cube.point == main.endPoint) {
                statusFd = "Success"
                return
            }
            if (step.cube.mass != 0 && step.cube.point == main.endPoint) {
                //badStep.add(step.stage.filter { it.visited }.map { it.point }.toHashSet())
                //println("Child in the end and mass is != 0\n${badStep.last()}")
                skipCounter++
                continue
            }

            if (stageDone) {
                //badStep.add(step.stage.filter { it.visited }.map { it.point }.toHashSet())
                //println("All is visited\n${badStep.last()}")
                skipCounter++
                continue
            }
            //it.cube == step.cube &&
            //println(badStep)
            //println(step.stage.filter { it2 -> it2.visited }.map { it3 -> it3.point })
            //if (badStep.find { it == step.stage.filter { it2 -> it2.visited }.map { it3 -> it3.point }} !=null)
//            {//  println("Duplicated bad step")
//                skipCounter++
//                continue
//            }
            val stepChildes = getNextSteps(step.cube, step.stage)
            if (stepChildes.isNotEmpty()) {
                nextStepStage = step.stage.map { Cube(it.mass, it.point, visited = it.visited) }
                stepChildes.forEach { child ->
                    childesNextSteps.add(
                        Step(
                            Cube(child.mass, child.point, visited = child.visited),
                            nextStepStage.toHashSet()
                        )
                    )
                }
            } else {
                /*badStep.add(step.stage.filter { it.visited }.map { it.point }.toHashSet())
                println("Childes is empty\n${badStep.last()}")
                println("Childes is empty\n${step.cube.point}")
                skipCounter++*/
                continue
            }
        }
        if (childesNextSteps.isNotEmpty()) down(childesNextSteps.toHashSet())
    }
}