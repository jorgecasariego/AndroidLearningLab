package com.jcstudio.mobilecodingacademy.learning.concurrency.experiments

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class RaceConditionResult(
    val expectedValue: Int,
    val actualValue: Int,
    val lostUpdates: Int
)


class RaceConditionExperiment {

    /*
        This experiment creates:
        100 coroutines
        ×
        1.000 increments
        =
        100.000 expected increments
     */
    suspend fun run(
        coroutineCount: Int = 100,
        incrementsPerCoroutine: Int = 1_000
    ): RaceConditionResult = withContext(Dispatchers.Default) {

        var counter = 0

        /*
        * coroutineScope no es para cambiar de dispatcher.
        * Su función es crear un scope estructurado para lanzar coroutines
        * hijas y esperar a que todas terminen antes de continuar.
        *
        * withContext(Default)
        *     ↓
        * Estamos usando el pool de CPU
        *
        * coroutineScope
        *     ↓
        * Ahora agrupamos varias coroutines hijas
        * y esperamos a todas
        * */
        coroutineScope {
            /*
                    // crea una lista con coroutineCount elementos.

                    List(coroutineCount) {
                        launch {
                            // ...
                        }
                    }

                    Ejemplo:
                    List(3) {
                        "Hello"
                    }

                    produce:

                    listOf("Hello", "Hello", "Hello")

             */
            List(coroutineCount) {
                launch {    // launch devuelve un objeto Job.
                            // en conjunto List() y launch {} crea una lista de `Job`
                    repeat(incrementsPerCoroutine) {    // repeat(n) ejecuta un bloque n veces.1
                        counter++
                    }
                }
            }.joinAll()
        }

        val expectedValue = coroutineCount * incrementsPerCoroutine

        RaceConditionResult(
            expectedValue = expectedValue,
            actualValue = counter,
            lostUpdates = expectedValue - counter
        )
    }
}