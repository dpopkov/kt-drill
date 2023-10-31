package learn.javafx.c01p01hello

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.text.Text

class MyController {
    @FXML var actiontarget:Text? = null

    @FXML fun handleButtonAction(event: ActionEvent) {
        actiontarget?.text = "Button pressed"
    }
}
