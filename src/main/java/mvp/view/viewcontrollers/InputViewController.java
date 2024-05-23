package mvp.view.viewcontrollers;
import javafx.scene.paint.Color;
import mvp.presentation.Presentation;
import mvp.view.UpdateMessageHandler;
import mvp.view.InputView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class InputViewController implements Initializable {
    @FXML
    private TextField nameInput;

    @FXML
    private Text updateMessage;

    @FXML
    private Button okButton;

    private final InputView inputView;

    private UpdateMessageHandler updateMessageHandler;

    public InputViewController(InputView inputView) {
        this.inputView = inputView;
    }

    public void launchUpdateMessage(String message, int duration, Color color) {
        updateMessageHandler.setUpdateMessage(message);
        updateMessageHandler.setUpdateMessageFill(color);
        updateMessageHandler.showUpdateMessage(duration);
    }

    public void addButtonsHandler(Presentation presentation) {
        addOkButtonHandler(presentation);
    }

    private void addOkButtonHandler(Presentation presentation) {
        okButton.setOnAction((e) -> presentation.finishSaveRide());
    }

    public String getRideName() {
        return nameInput.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateMessageHandler = new UpdateMessageHandler(updateMessage);
        updateMessage.setVisible(false);
    }
}
