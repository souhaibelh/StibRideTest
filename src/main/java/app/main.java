package app;
import javafx.application.Application;
import javafx.stage.Stage;
import mvp.model.Model;
import mvp.presentation.Presentation;
import mvp.view.InputView;
import mvp.view.MainView;

// --module-path "\path\to\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml

public class main extends Application {
    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        MainView mainView = new MainView();
        InputView inputView = new InputView(mainView);
        Presentation presentation = new Presentation(model, mainView, inputView);
        model.addObserver(presentation);
    }
}
