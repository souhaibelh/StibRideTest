package mvp.view.viewcontrollers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mvp.model.db.dto.StationsDto;
import mvp.presentation.Presentation;
import org.controlsfx.control.SearchableComboBox;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchTabController implements Initializable {
    private final String LOGOURL = "mvp/view/viewcontrollers/img/logostib.png";

    @FXML
    private ImageView logoStib;

    @FXML
    private SearchableComboBox<StationsDto> choiceOrigin;

    @FXML
    private SearchableComboBox<StationsDto> choiceDestination;

    @FXML
    private Button searchBtn;

    @FXML
    private Button saveRideButton;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        initLogo();
    }

    public void addButtonsHandler(Presentation presentation) {
        saveRideButtonHandler(presentation);
        searchButtonHandler(presentation);
    }

    public void disableSearchButton(boolean disable) {
        searchBtn.setDisable(disable);
    }

    public StationsDto getDestination() {
        return choiceDestination.getValue();
    }

    public StationsDto getOrigin() {
        return choiceOrigin.getValue();
    }

    public void saveRideButtonHandler(Presentation presentation) {
        saveRideButton.setOnAction((e) -> presentation.launchSaveRide());
    }

    public void searchButtonHandler(Presentation presentation) {
        searchBtn.setOnAction((e) -> presentation.searchRidesTabSearcher());
    }

    public void setSearchableComboBoxes(List<StationsDto> list) {
        choiceDestination.getItems().setAll(list);
        choiceOrigin.getItems().setAll(list);
    }

    public void setCustomComboBoxes(StationsDto origin, StationsDto destination) {
        choiceOrigin.setValue(origin);
        choiceDestination.setValue(destination);
    }

    private void initLogo() {
        try {
            InputStream imgReader = new FileInputStream(getClass().getClassLoader().getResource(LOGOURL).getFile());
            Image logoImg = new Image(imgReader);
            logoStib.setImage(logoImg);
            logoStib.setScaleY(0.8);
            logoStib.setScaleX(0.8);
        } catch (IOException e) {
            System.out.println("Couldn't read logo image at: " + LOGOURL);
        }
    }
}
