package learn.javafx.c09concurrency

import javafx.application.Application
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Service
import javafx.concurrent.Task
import javafx.concurrent.Worker
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.stage.WindowEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import learn.javafx.utils.VerticalStrut
import java.time.LocalTime
import java.util.concurrent.Executors

fun main() {
    Application.launch(AppConcurrency::class.java)
}

class AppConcurrency : Application() {
    private val closeListeners = mutableListOf<() -> Unit>()

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Concurrency"
        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST) { closeListeners.forEach { it() } }

        val root = StackPane().apply {
            children.add(buildContents())
        }
        with(primaryStage) {
            scene = Scene(root, 540.0, 400.0)
            show()
        }
    }

    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    private fun buildContents(): Node {
        val tf = TextField()
        val btnFreeze = Button("Freezing Thread Example").apply {
            setOnAction {
                Thread.sleep(5000)
                tf.text = "Updated at ${LocalTime.now()}"
            }
        }
        /* Dirty remedy - using Thread */
        val btnThread = Button("Additional Thread Example").apply {
            setOnAction {
                Thread {
                    Thread.sleep(5000)
                    tf.text = "Updated at ${LocalTime.now()}"
                }.start()
            }
        }
        /* Dirty remedy - using Thread with updating in UI thread */
        val btnRunLater = Button("JavaFX Application Thread Example").apply {
            setOnAction {
                Thread {
                    Thread.sleep(5000)
                    Platform.runLater {
                        tf.text = "Updated at ${LocalTime.now()}"
                    }
                }.start()
            }
        }
        val boxedThread = VBox(
            4.0,
            Text("Using just Thread:"),
            btnFreeze,
            btnThread,
            btnRunLater,
            tf,
        )

        /* Pool of threads and  javafx.concurrent.Task */
        val pool = Executors.newFixedThreadPool(10).also { pool ->
            closeListeners.add(pool::shutdown)
        }
        val slider2 = Slider(0.0, 1.0, 0.0)
        val text2 = Text()
        val btn2 = Button("Start Task").apply {
            setOnAction {
                val task = object : Task<Unit>() {
                    override fun call() {
                        updateMessage("Started")
                        for (i in 0..<1000) {
                            if (isCancelled) {
                                break
                            }
                            try {
                                Thread.sleep(10)
                            } catch (e: InterruptedException) {
                                break
                            }
                            updateProgress(i.toDouble(), 1000.0)
                        }
                        updateMessage("Done")
                    }
                }
                userData = task
                slider2.valueProperty().bind(task.progressProperty())
                text2.textProperty().bind(task.messageProperty())
                pool.submit(task)
            }
        }
        val btnCancel2 = Button("Cancel").apply {
            setOnAction { (btn2.userData as Task<*>).cancel() }
        }
        val boxedPooledTask = VBox(
            4.0,
            Text("Using Pool of threads and  javafx.concurrent.Task:"),
            HBox(4.0, btn2, btnCancel2, slider2, text2)
        )

        /* Using Service */
        val slider3 = Slider(0.0, 1.0, 0.0)
        val text3 = Text()
        val service3 = object : Service<Unit>() {
            override fun createTask(): Task<Unit> = object : Task<Unit>() {
                override fun call() {
                    updateMessage("Started")
                    for (i in 0..<1000) {
                        if (isCancelled) {
                            break
                        }
                        try {
                            Thread.sleep(10)
                        } catch (e: InterruptedException) {
                            break
                        }
                        updateProgress(i.toDouble(), 1000.0)
                    }
                    updateMessage("Done")
                }
            }.also {
                slider3.valueProperty().bind(it.progressProperty())
                text3.textProperty().bind(it.messageProperty())
            }

            override fun cancelled() = unbindAll()
            override fun failed() = unbindAll()
            override fun succeeded() = unbindAll()
            private fun unbindAll() {
                slider3.valueProperty().unbind()
                text3.textProperty().unbind()
            }
        }
        val btnStart3 = Button("Start Service").apply {
            setOnAction {
                if (service3.state == Worker.State.READY) {
                    service3.start()
                }
            }
        }
        val btnCancel3 = Button("Reset Service").apply {
            setOnAction {
                service3.cancel()
                service3.reset()
                slider3.value = 0.0
                text3.text = ""
            }
        }
        val boxedService = VBox(
            Text("Using javafx.concurrent.Service with its own thread pool:"),
            HBox(4.0, btnStart3, btnCancel3, slider3, text3),
        )

        /* Using Coroutines */
        val outputData: ObservableList<String> = FXCollections.observableArrayList("", "", "", "")
        fun ObservableList<String>.pushMsg(s: String) {
            add(0, s)
            if (size > 4) {
                removeAt(4)
            }
        }

        val outputListView = ListView(outputData)
        val btnStartCountdown = Button("Start Countdown").apply {
            setOnAction {
                /* Launching coroutine in JavaFX main UI thread */
                val job = GlobalScope.launch(Dispatchers.Main) {
                    for (i in 10 downTo 1) {
                        outputData.pushMsg("Countdown $i")
                        delay(500L)
                    }
                    outputData.pushMsg("Done")
                }
                userData = job
            }
        }
        val btnStopCountdown = Button("Stop Countdown").apply {
            setOnAction {
                (btnStartCountdown.userData as Job).cancel()
            }
        }
        val btnStartCalculatingPi = Button("Calculate PI").apply {
            suspend fun CoroutineScope.stupidPi(): ReceiveChannel<Double> = produce(Dispatchers.Default) {
                var i = 1L
                var sign = 1
                var value = 0.0
                while (true) {
                    value += 4.0 * sign / (2.0 * i.toDouble() - 1.0)
                    sign *= -1
                    i++
                    if (i % 100_000_000 == 0L) {
                        send(value)
                    }
                }
            }
            setOnAction {
                val job = GlobalScope.launch(Dispatchers.Main) {
                    val producer = stupidPi();
                    while (true) {
                        outputData.pushMsg(producer.receive().toString())
                    }
                }
                userData = job
            }
        }
        val btnStopCalculatingPi = Button("Stop calculating PI").apply {
            setOnAction {
                (btnStartCalculatingPi.userData as Job).cancel()
            }
        }
        val boxedCoroutines = VBox(
            4.0,
            Text("Using coroutines:"),
            HBox(
                4.0,
                VBox(
                    HBox(btnStartCountdown, btnStopCountdown),
                    HBox(btnStartCalculatingPi, btnStopCalculatingPi)
                ),
                outputListView
            )
        )

        return VBox(
            5.0,
            boxedThread,
            VerticalStrut(10),
            boxedPooledTask,
            VerticalStrut(10),
            boxedService,
            VerticalStrut(10),
            boxedCoroutines,
        ).apply {
            style = "-fx-padding: 10;"
        }
    }
}