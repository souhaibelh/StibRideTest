package mvp.presentation;
import mvp.communication.Colors;
import mvp.communication.Errors;
import mvp.communication.Success;
import mvp.model.Model;
import mvp.model.Node;
import mvp.model.util.ModelEvent;
import mvp.model.util.ModelUpdate;
import mvp.model.util.Observer;
import mvp.view.FavoriteTableRow;
import mvp.view.InputView;
import mvp.view.MainView;
import mvp.view.viewcontrollers.*;
import javafx.scene.paint.Color;
import mvp.model.db.dto.FavoritesDto;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.tablepk.FavoritesPK;
import java.util.ArrayList;
import java.util.List;

public class Presentation implements Observer {
    private final Model model;
    private final MainViewController mainViewController;
    private final InputViewController inputViewController;
    private final SaveTabController saveTab;
    private final SearchTabController searchTab;
    private final ShortestPathController shortestPathController;
    private final InputView inputView;

    public Presentation(Model model, MainView mainView, InputView inputView) {
        this.model = model;
        this.inputView = inputView;
        mainViewController = mainView.getViewController();
        inputViewController = inputView.getInputViewCtrl();
        saveTab = mainView.getSaveTabController();
        searchTab = mainView.getSearchTabController();
        shortestPathController = mainView.getShortestPathController();
        saveTab.addButtonsHandler(this);
        searchTab.addButtonsHandler(this);
        init();
    }

    /**
     * Gets all stations through the model and passes them to the view to add them to the searchable combo boxes.
     */
    private void init() {
        searchTab.setSearchableComboBoxes(model.getAllStations());
        refreshFavorites();
    }

    /**
     * Method that will update the model and view according to what updates the model sends.
     * @param update the type of the update (will let us know what was updated and information about it if needed)
     */
    @Override
    public void update(ModelUpdate update) {
        ModelEvent event = update.getType();
        Object[] information = update.getInformation();
        switch (event) {
            case SHORTEST_PATH_CALCULATED -> {
                searchTab.disableSearchButton(false);
                shortestPathController.clearTable();
                List<Node> path = model.getShortestPath();
                mainViewController.setNumberOfStations(path.size());
                path.forEach((node) -> shortestPathController.addToTable(node.getStation()));
                mainViewController.showBottomInformationBar();
                mainViewController.launchUpdateMessage(Success.SHORTEST_PATH_SUCCESS.getSuccessText(), 3, Colors.SUCCESS.getColor());

            } case SAVE_RIDE_SUCCESS -> {
                mainViewController.launchUpdateMessage(Success.RIDE_SAVED_SUCCESS.getSuccessText(), 3, Colors.SUCCESS.getColor());
                refreshFavorites();

            } case DELETE_FAVORITE_RIDE_SUCCESS -> {
                mainViewController.launchUpdateMessage(Success.RIDE_DELETED_SUCCESS.getSuccessText(), 3, Colors.SUCCESS.getColor());
                refreshFavorites();

            } case SAVE_FAVORITE_CHANGES_SUCCESS -> {
                mainViewController.launchUpdateMessage(Success.CHANGES_SAVED_SUCCESS.getSuccessText(), 3, Colors.SUCCESS.getColor());

            } case FAVORITES_TABLE_UK_VIOLATION -> {
                StationsDto org = (StationsDto) information[0];
                StationsDto dst = (StationsDto) information[1];
               mainViewController.launchUpdateMessage(
                       Errors.FAVORITES_TABLE_UK_VIOLATION.getErrorText() + " (" + org.getName() + ", " + dst.getName() + ")",
                       3,
                       Color.RED
               );
            }
        }
    }

    /**
     * Validates a favorite dto by checking its name, its destination and origin and also checks if there is already
     * another favorite dto in the favorites table view with the same destination and origin
     * @param dto dto we want to validate
     * @return boolean saying if its valid or no
     */
    public boolean validateFavDto(FavoritesDto dto) {
        boolean isValid = true;
        if (dto.getName().isEmpty()) {
            isValid = false;
            mainViewController.launchUpdateMessage(
                    Errors.NO_NAME_TEXT.getErrorText(),
                    3,
                    Colors.ERROR.getColor()
            );
        } else if (!dto.getName().matches("^[0-9]*[a-zA-Z]+[0-9]*$")) {
            isValid = false;
            mainViewController.launchUpdateMessage(
                    Errors.INVALID_NAME_TEXT.getErrorText(),
                    3,
                    Colors.ERROR.getColor()
            );
        } else if (dto.getOrigin() == null) {
            isValid = false;
            mainViewController.launchUpdateMessage(
                    Errors.NULL_ORIGIN_STATION.getErrorText(),
                    3,
                    Colors.ERROR.getColor()
            );
        } else if (dto.getDestination() == null) {
            isValid = false;
            mainViewController.launchUpdateMessage(
                    Errors.NULL_DESTINATION_STATION.getErrorText(),
                    3,
                    Colors.ERROR.getColor()
            );
        } else if (containsOrgDst(dto)) {
            isValid = false;
            mainViewController.launchUpdateMessage(
                    Errors.FAVORITES_TABLE_UK_VIOLATION.getErrorText() + " (" + dto.getOrigin() + ", " + dto.getDestination() + ")",
                    3,
                    Colors.ERROR.getColor()
            );
        }
        return isValid;
    }

    /**
     * Receives a favorites dto and checks if there is already another favorite dto in the favorites table view with
     * the same destination and origin
     * @param dto favorite dto
     * @return true if it contains the favorites dto, false if it doesn't
     */
    public boolean containsOrgDst(FavoritesDto dto) {
        List<FavoritesDto> allDto = model.getAllFavoriteRides();
        for (FavoritesDto dt : allDto) {
            if ((dt.getDestination().equals(dto.getDestination())
            && dt.getOrigin().equals(dto.getOrigin())) && !dto.equals(dt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes in the favorite table row list and validates all the rows before queuing them to add or delete
     * @param rowList list of favorite table to validate
     * @return true if they are valid, false if they aren't
     */
    public boolean validateRows(List<FavoriteTableRow> rowList) {
        for (FavoriteTableRow row : rowList) {
            String name = row.getName().getText();
            StationsDto org = row.getOrg().getValue();
            StationsDto dst = row.getDst().getValue();
            FavoritesDto dto = new FavoritesDto(name, org, dst);
            boolean isValid = validateFavDto(dto);

            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    /**
     * Searches a ride if the origin and destination are not null, if they are it informs the user
     */
    public void searchRidesTabSearcher() {
        StationsDto origin = searchTab.getOrigin();
        StationsDto destination = searchTab.getDestination();

        if (origin == null) {
            mainViewController.launchUpdateMessage(Errors.NULL_ORIGIN_STATION.getErrorText(), 5, Color.RED);
        } else if (destination == null) {
            mainViewController.launchUpdateMessage(Errors.NULL_DESTINATION_STATION.getErrorText(), 5, Color.RED);
        } else {
            model.shortestPath(searchTab.getOrigin(), searchTab.getDestination());
        }
    }

    /**
     * Launches the window to save the ride, the window that asks the name of the ride we want to save
     */
    public void launchSaveRide() {
        StationsDto origin = searchTab.getOrigin();
        StationsDto destination = searchTab.getDestination();

        if (origin == null) {
            mainViewController.launchUpdateMessage(Errors.NULL_ORIGIN_STATION.getErrorText(), 3, Color.RED);
        } else if (destination == null) {
            mainViewController.launchUpdateMessage(Errors.NULL_DESTINATION_STATION.getErrorText(), 3, Color.RED);
        } else {
            inputView.show();
            inputViewController.addButtonsHandler(this);
        }
    }

    /**
     * Processes the name the user introduces to check if its valid or no
     */
    public void finishSaveRide() {
        InputViewController ctrl = inputView.getInputViewCtrl();
        String name = ctrl.getRideName();
        if (name.isEmpty()) {
            ctrl.launchUpdateMessage(
                    Errors.NO_NAME_TEXT.getErrorText(),
                    3,
                    Colors.ERROR.getColor()
            );
        } else if (!name.matches("^[0-9]*[a-zA-Z]+[0-9]*$")) {
            ctrl.launchUpdateMessage(
                    Errors.INVALID_NAME_TEXT.getErrorText(),
                    3,
                    Colors.ERROR.getColor()
            );
        } else {
            FavoritesDto dtoToSave = new FavoritesDto(
                    ctrl.getRideName(),
                    searchTab.getOrigin(),
                    searchTab.getDestination()
            );
            model.saveRide(dtoToSave);
            inputView.close();
        }
    }

    /**
     * Searches a path of a favorite dto
     * @param f favorite dto
     */
    public void searchRidesTabSearcher(FavoritesDto f) {
        boolean valid = validateFavDto(f);
        if (valid) {
            model.shortestPath(f.getOrigin(), f.getDestination());
        }
    }

    /**
     * Deletes a favorite dto from the Favorites table
     * @param f dto to delete
     */
    public void deleteFavoriteRide(FavoritesDto f) {
        FavoritesPK pk = new FavoritesPK(f.getId());
        model.deleteFavoriteRide(pk);
    }

    /**
     * Refreshes the favorites tableView
     */
    public void refreshFavorites() {
        saveTab.clearFavRidesTable();
        List<FavoritesDto> favorites = model.getAllFavoriteRides();
        List<StationsDto> allStations = model.getAllStations();
        List<FavoriteTableRow> favTabRows = new ArrayList<>();
        favorites.forEach((f) -> {
            FavoriteTableRow favoriteRow = new FavoriteTableRow(f, allStations);
            favoriteRow.getDeleteButton().setOnAction((e) -> {
                deleteFavoriteRide(f);
            });
            favoriteRow.getRunButton().setOnAction((e) -> {
                FavoritesDto updatedDto = new FavoritesDto(
                        favoriteRow.getName().getText(),
                        favoriteRow.getOrg().getValue(),
                        favoriteRow.getDst().getValue()
                );
                searchRidesTabSearcher(updatedDto);
            });
            favTabRows.add(favoriteRow);
        });
        saveTab.setFavTable(favTabRows);
        shortestPathController.clearTable();
    }

    /**
     * Saves the favorite rides changes in the table view to the Favorites table
     */
    public void saveFavRidesChanges() {
        List<FavoriteTableRow> rowsList = saveTab.getSavedRidesList();
        rowsList.forEach((e) -> System.out.println(e.getName().getText()));
        boolean valid = validateRows(rowsList);
        if (valid) {
            List<FavoritesDto> favDtoList = convertRowsToDto(rowsList);
            model.saveFavRides(favDtoList);
            refreshFavorites();
        }
    }

    /**
     * Receives FavoriteTableRow and converts them to favoritesDto we can save
     * @param rowsList list of rows
     * @return list of favoritesDto
     */
    private List<FavoritesDto> convertRowsToDto(List<FavoriteTableRow> rowsList) {
        List<FavoritesDto> favDtoList = new ArrayList<>();
        rowsList.forEach((favRow) -> {
            FavoritesDto dto = new FavoritesDto(
                    favRow.getName().getText(),
                    favRow.getOrg().getValue(),
                    favRow.getDst().getValue()
            );
            dto.setId(favRow.getId());
            favDtoList.add(dto);
        });
        return favDtoList;
    }

    /**
     * Method that adds an empty ride row in the table
     */
    public void addRideRow() {
        FavoritesDto favDto = new FavoritesDto();
        FavoriteTableRow favTabRow = new FavoriteTableRow(favDto, model.getAllStations());
        favTabRow.getDeleteButton().setOnAction((e) -> {
            saveTab.getFavTable().getItems().remove(favTabRow);
        });
        favTabRow.getRunButton().setOnAction((e) -> {
            FavoritesDto dto = new FavoritesDto(
                    favTabRow.getName().getText(),
                    favTabRow.getOrg().getValue(),
                    favTabRow.getDst().getValue()
            );
            searchRidesTabSearcher(dto);
        });
        saveTab.addFavoriteRideEmptyRow(favTabRow);
    }
}
