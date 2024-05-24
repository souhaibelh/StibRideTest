package mvp.view;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.SearchableComboBox;
import mvp.model.db.dto.FavoritesDto;
import mvp.model.db.dto.StationsDto;

import java.util.List;

public class FavoriteTableRow {
    private final SearchableComboBox<StationsDto> org;
    private final SearchableComboBox<StationsDto> dst;
    private final HBox buttonBar;
    private final TextField name;
    private final Button deleteButton;
    private final Button runButton;
    private final Integer id;

    public FavoriteTableRow(FavoritesDto favDto, List<StationsDto> stationsList) {
        id = favDto.getId();
        name = new TextField();
        name.setText(favDto.getName());

        org = new SearchableComboBox<>();
        dst = new SearchableComboBox<>();

        org.setItems(FXCollections.observableList(stationsList));
        dst.setItems(FXCollections.observableList(stationsList));

        buttonBar = new HBox();
        deleteButton = new Button("D");
        runButton = new Button("R");
        buttonBar.getChildren().addAll(deleteButton, runButton);
        initButtons();

        StationsDto origin = favDto.getOrigin();
        StationsDto destination = favDto.getDestination();
        org.getItems().forEach((s) -> {
            if (s.equals(origin)) {
                org.setValue(s);
            }
        });

        dst.getItems().forEach((d) -> {
            if (d.equals(destination)) {
                dst.setValue(d);
            }
        });
    }

    public void initButtons() {
        deleteButton.setPrefHeight(25);
        deleteButton.setPrefWidth(25);
        runButton.setPrefHeight(25);
        runButton.setPrefWidth(25);
        buttonBar.setSpacing(5);
        buttonBar.setAlignment(Pos.CENTER_LEFT);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getRunButton() {
        return runButton;
    }

    public SearchableComboBox<StationsDto> getOrg() {
        return this.org;
    }

    public SearchableComboBox<StationsDto> getDst() {
        return this.dst;
    }

    public TextField getName() {
        return name;
    }

    public HBox getButtonBar() {
        return buttonBar;
    }

    public Integer getId() {
        return id;
    }
}
