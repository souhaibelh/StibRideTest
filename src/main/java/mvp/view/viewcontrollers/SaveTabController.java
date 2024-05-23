package mvp.view.viewcontrollers;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.dto.StopsDto;
import mvp.presentation.Presentation;
import mvp.view.FavoriteTableRow;
import org.controlsfx.control.SearchableComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SaveTabController implements Initializable {

    @FXML
    private TableView<FavoriteTableRow> favRidesTable;

    @FXML
    private TableColumn<FavoriteTableRow, HBox> favRideOptionsCol;

    @FXML
    private TableColumn<FavoriteTableRow, TextField> favRideNameCol;

    @FXML
    private TableColumn<FavoriteTableRow, SearchableComboBox<StationsDto>> favRideOrgCol;

    @FXML
    private TableColumn<FavoriteTableRow, SearchableComboBox<StationsDto>> favRideDstCol;

    @FXML
    private Button addRideRow;

    @FXML
    private Button saveFavoriteRideBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFavoriteTable();
    }

    public void addButtonsHandler(Presentation presentation) {
        updateFavoriteRidesHandler(presentation);
        addRideRowHandler(presentation);
    }

    private void initFavoriteTable() {
        favRideOptionsCol.setCellValueFactory((e) -> new SimpleObjectProperty<>(e.getValue().getButtonBar()));
        favRideNameCol.setCellValueFactory((e) -> new SimpleObjectProperty<>(e.getValue().getName()));
        favRideOrgCol.setCellValueFactory((e) -> new ReadOnlyObjectWrapper<>(e.getValue().getOrg()));
        favRideDstCol.setCellValueFactory((e) -> new ReadOnlyObjectWrapper<>(e.getValue().getDst()));
        favRidesTable.getColumns().setAll(favRideOptionsCol, favRideNameCol, favRideOrgCol, favRideDstCol);
    }

    public void updateFavoriteRidesHandler(Presentation presentation) {
        saveFavoriteRideBtn.setOnAction((e) -> presentation.saveFavRidesChanges());
    }

    private void addRideRowHandler(Presentation presentation) {
        addRideRow.setOnAction((e) -> presentation.addRideRow());
    }

    public void addFavoriteRideEmptyRow(FavoriteTableRow favTabRow) {
        favRidesTable.getItems().add(favTabRow);
    }

    public List<FavoriteTableRow> getSavedRidesList() {
        ObservableList<FavoriteTableRow> allFavRides = favRidesTable.getItems();
        return new ArrayList<>(allFavRides);
    }

    public TableView<FavoriteTableRow> getFavTable() {
        return favRidesTable;
    }

    public void setFavTable(List<FavoriteTableRow> list) {
        clearFavRidesTable();
        favRidesTable.setItems(FXCollections.observableList(list));
        resetRowStationBackgroundColor();
    }

    public void resetRowStationBackgroundColor() {
        favRidesTable.getItems().forEach((f) -> {
            f.getDst().setStyle("-fx-border-color: transparent");
            f.getOrg().setStyle("-fx-border-color: transparent");
        });
    }

    public void clearFavRidesTable() {
        favRidesTable.getItems().clear();
    }
}
