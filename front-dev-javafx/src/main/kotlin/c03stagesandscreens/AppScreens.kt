package learn.javafx.c03stagesandscreens

import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.stage.Screen
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(AppScreens::class.java, *args)
}

class AppScreens : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Stages and Scenes"

        val screenList = Screen.getScreens()
        println("# of Screens: ${screenList.size}")
        screenList.forEach { screen ->
            printInfo(screen)
        }
        Platform.exit()
    }

    private fun printInfo(screen: Screen) {
        println(
            """
            |------------------------------------
            |Resolution DPI: ${screen.dpi}
            |Screen Bounds: ${info(screen.bounds)}
            |Screen Visual Bounds: ${info(screen.visualBounds)}
            |Output Scale X: ${screen.outputScaleX}
            |Output Scale Y: ${screen.outputScaleY}
        """.trimMargin()
        )
    }

    private fun info(bounds: Rectangle2D): String {
        return String.format(
            "minX=%.2f, minY=%.2f, width=%.2f, height=%.2f",
            bounds.minX, bounds.minY, bounds.width, bounds.height
        )
    }
}