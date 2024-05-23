package mvp.view;
import mvp.view.viewcontrollers.MainViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mvp.view.viewcontrollers.SaveTabController;
import mvp.view.viewcontrollers.SearchTabController;
import mvp.view.viewcontrollers.ShortestPathController;

public class MainView extends Stage {
    private final MainViewController mainViewController;
    private final SearchTabController searchTabController;
    private final SaveTabController saveTabController;
    private final ShortestPathController shortestPathController;

    public MainView() throws Exception {
        mainViewController = new MainViewController();
        FXMLLoader load = new FXMLLoader(getClass().getResource("fxml/mainview.fxml"));
        load.setController(mainViewController);
        Parent parent = load.load();

        searchTabController = mainViewController.getSearchTabController();
        saveTabController = mainViewController.getSaveTabController();
        shortestPathController = mainViewController.getShortestPathController();

        Scene scene = new Scene(parent);
        this.setScene(scene);
        this.show();
    }

    public ShortestPathController getShortestPathController() {return shortestPathController;}

    public SearchTabController getSearchTabController() { return searchTabController; }

    public SaveTabController getSaveTabController() { return saveTabController; }

    public MainViewController getViewController() {
        return this.mainViewController;
    }
}
