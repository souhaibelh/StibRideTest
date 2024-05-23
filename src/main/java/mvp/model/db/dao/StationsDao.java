package mvp.model.db.dao;
import mvp.model.db.DBManager;
import mvp.model.db.tablepk.StationsPK;
import mvp.model.db.dto.StationsDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationsDao implements Dao<StationsPK, StationsDto> {
    private final Connection connection;

    /**
     * Sets the connection to the single instance we hold
     */
    public StationsDao() {
        connection = DBManager.getInstance().getConnection();
    }

    @Override
    public void insert(StationsDto item) {
        //Nothing
    }

    @Override
    public void delete(StationsPK key) {
        //Nothing
    }

    @Override
    public void update(StationsDto item) {
        //Nothing
    }

    /**
     * Method that selects a StationsDto from the database with the primary key that we pass in parameters
     * @param key key of the stationsDto we are trying to get
     * @return the StationsDto
     */
    @Override
    public StationsDto select(StationsPK key) {
        String query = "SELECT name, id FROM STATIONS WHERE id=?";
        String secondQuery = "SELECT id_line FROM STOPS WHERE id_station = ?";
        StationsDto stationsDto = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            PreparedStatement pstmt2 = connection.prepareStatement(secondQuery);
            pstmt2.setInt(1, key.getIdStation());
            pstmt.setInt(1, key.getIdStation());
            connection.setAutoCommit(false);

            List<Integer> stationsLines = new ArrayList<>();
            ResultSet result = pstmt2.executeQuery();
            while (result.next()) {
                int id_line = result.getInt(1);
                stationsLines.add(id_line);
            }

            result = pstmt.executeQuery();
            while (result.next()) {
                int id = result.getInt(2);
                String name = result.getString(1);
                stationsDto = new StationsDto(name, id);
                stationsDto.setLines(stationsLines);
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Couldn't access to database: ");
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException f) {
                System.out.println("Couldn't rollback, state: " + f.getSQLState());
            }
        }
        return stationsDto;
    }

    /**
     * Method that selects all the StationsDto in the STATIONS table
     * @return a list containing all the StationsDto
     */
    @Override
    public List<StationsDto> selectAll() {
        List<StationsDto> stations = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT name, id FROM STATIONS";

            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(2);
                String name = result.getString(1);
                StationsDto station = new StationsDto(name, id);
                stations.add(station);
            }
        } catch (SQLException e) {
            System.out.println("Encountered a sql error: " + e.getMessage());
        }
        return stations;
    }
}
