package addons.chartjs.demo

import de.peekandpoke.kraft.addons.chartjs.ChartJs
import de.peekandpoke.kraft.addons.chartjs.demo.ChartJsDemoUtils
import kotlin.math.max
import kotlin.random.Random

data class ChartJsDemoData(
    val dataLength: Int = 6,
    val datasets: List<Dataset> = emptyList(),
) {
    data class Dataset(
        val label: String,
        val color: String,
        val values: List<Number>
    )

    private fun nextRandomInt() = Random.nextInt(-50, 50)

    fun randomize() = copy(
        datasets = datasets.map {
            it.copy(values = it.values.map { nextRandomInt() })
        }
    )

    fun addDatasets(n: Int = 1): ChartJsDemoData {
        if (n == 0) return this

        return addDataset("DS ${datasets.size + 1}").addDatasets(n - 1)
    }

    fun addDataset(label: String) = copy(
        datasets = datasets.plus(
            Dataset(
                label = label,
                color = ChartJsDemoUtils.nextColor(datasets.size),
                values = (0 until dataLength).map { nextRandomInt() }
            )
        )
    )

    fun dropLastDatasets(n: Int = 1) = copy(
        datasets = datasets.dropLast(n)
    )

    fun incDataLength(): ChartJsDemoData {
        val newLength = dataLength + 1

        return copy(
            dataLength = newLength,
            datasets = datasets.map { set ->
                set.copy(
                    values = (0 until newLength).map { set.values.getOrNull(it) ?: nextRandomInt() }
                )
            }
        )
    }

    fun decDataLength(): ChartJsDemoData {
        val newLength = max(0, dataLength - 1)

        return copy(
            dataLength = newLength,
            datasets = datasets.map { set ->
                set.copy(
                    values = set.values.subList(0, newLength)
                )
            }
        )
    }
}
