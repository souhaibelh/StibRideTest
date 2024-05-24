package mvp.view.viewcontrollers;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mvp.model.db.dto.StationsDto;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShortestPathController implements Initializable {
    @FXML
    private TableView<StationsDto> table;

    @FXML
    private TableColumn<StationsDto, String> stationsCol;

    @FXML
    private TableColumn<StationsDto, List<Integer>> stationsLignes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
    }

    private void initTable() {
        stationsCol.setCellValueFactory((e) -> new SimpleObjectProperty<>(e.getValue().getName()));
        stationsLignes.setCellValueFactory((e) -> new SimpleObjectProperty<>(e.getValue().getLines()));
        table.getColumns().setAll(stationsCol, stationsLignes);
    }

    public List<StationsDto> getStationsDtoInSearch() {
        return new ArrayList<>(table.getItems());
    }

    public void clearTable() {
        table.getItems().clear();
    }

    public void addToTable(StationsDto element) {
        table.getItems().add(element);
    }
}
