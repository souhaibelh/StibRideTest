package mvp.view.viewcontrollers;
import javafx.scene.paint.Color;
import mvp.view.UpdateMessageHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable  {
    @FXML
    private SearchTabController searchTabController;

    @FXML
    private SaveTabController saveTabController;

    @FXML
    private ShortestPathController shortestPathController;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane planMetroContainer;

    @FXML
    private ImageView planMetro;

    @FXML
    private Label searchFinalised;

    @FXML
    private Text numberOfStations;

    @FXML
    private HBox numberOfStationsContainer;

    @FXML
    private Text updateMessage;

    @FXML
    private Tab searchtab;

    private UpdateMessageHandler updateMessageHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateMessageHandler = new UpdateMessageHandler(updateMessage);
        initImages();
        initBottomInformation();
    }

    public SearchTabController getSearchTabController() {
        return searchTabController;
    }

    public SaveTabController getSaveTabController() {
        return saveTabController;
    }

    public ShortestPathController getShortestPathController() {
        return shortestPathController;
    }

    public void launchUpdateMessage(String message, int duration, Color color) {
        updateMessageHandler.setUpdateMessage(message);
        updateMessageHandler.setUpdateMessageFill(color);
        updateMessageHandler.showUpdateMessage(duration);
    }

    public void initBottomInformation() {
        searchFinalised.setVisible(false);
        numberOfStationsContainer.setVisible(false);
    }

    public void setNumberOfStations(int number) {
        numberOfStations.setText(String.valueOf(number));
    }

    private void initImages() {
        scrollPane.setContent(planMetroContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        InputStream is = readFile();
        Image metroPlan = new Image(is);
        planMetro.setImage(metroPlan);
        planMetro.setFitWidth(1200);
        planMetroContainer.setPrefWidth(1200);
    }

    private InputStream readFile() {
        File file = new File(getClass().getResource("img/metroplan.png").getFile());
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find file at: " + "img/metroplan.png" + ". Error: " + e.getMessage());
        }
        return is;
    }

    public void showBottomInformationBar() {
        searchFinalised.setVisible(true);
        numberOfStationsContainer.setVisible(true);
    }
}
