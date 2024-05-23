package mvp.view;
import mvp.view.viewcontrollers.InputViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class InputView extends Stage {
    private InputViewController inputViewController;

    public InputView(Stage owner) {
        inputViewController = null;
        try {
            FXMLLoader loader = new FXMLLoader(InputView.class.getResource("fxml/inputnameview.fxml"));
            inputViewController = new InputViewController(this);
            loader.setController(inputViewController);
            Parent node = loader.load();
            Scene scene = new Scene(node);
            this.setScene(scene);
            this.initOwner(owner);
            this.initModality(Modality.WINDOW_MODAL);
        } catch (IOException e) {
            System.out.println("Couldn't find fxml file related to InputView");
        }
    }

    public InputViewController getInputViewCtrl() {
        return inputViewController;
    }
}
