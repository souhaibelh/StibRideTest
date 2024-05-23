package mvp.model.db.dao;

import mvp.model.db.DBManager;
import mvp.model.db.tablepk.StopsPK;
import mvp.model.db.dto.StopsDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StopsDao implements Dao<StopsPK, StopsDto> {
    private final Connection connection;

    /**
     * Sets the connection to be the single instance we hold in DBManager
     */
    public StopsDao() {
        connection = DBManager.getInstance().getConnection();
    }

    /**
     * We give it an id station, and it returns the list of all StopsDto in which our stationId is involved
     * @param idStation the station we want to get all the stops involved
     * @return list containing all the stops in which the station with the id passed in parameters is involved
     */
    public List<StopsDto> selectByStation(Integer idStation) {
        String query = "SELECT id_line, id_station, id_order FROM STOPS WHERE id_station = ?";
        List<StopsDto> stops = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, idStation);

            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                int id_line = result.getInt(1);
                int id_station = result.getInt(2);
                int id_order = result.getInt(3);
                StopsDto stopsDto = new StopsDto(id_line, id_station, id_order);
                stops.add(stopsDto);
            }
        } catch (SQLException e) {
            System.out.println("METHOD: SELECTBYSTATION");
        }
        return stops;
    }

    /**
     * Selects a stops dto from the STOPS table with the idLine and idOrder passed in parameters
     * @param idLine id line
     * @param idOrder id order
     * @return StopsDto involved in it
     */
    public StopsDto selectByLineOrder(Integer idLine, Integer idOrder) {
        String query = "SELECT id_line, id_station, id_order FROM STOPS WHERE id_line = ? AND id_order = ?";
        StopsDto stopsDto = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, idLine);
            pstmt.setInt(2, idOrder);

            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                int id_line = result.getInt(1);
                int id_station = result.getInt(2);
                int id_order = result.getInt(3);
                stopsDto = new StopsDto(id_line, id_station, id_order);
            }
        } catch (SQLException e) {
            System.out.println("METHOD: SELECTBYLINEORDER");
        }
        return stopsDto;
    }

    @Override
    public void insert(StopsDto item) {
        // Nothing here
    }

    @Override
    public void delete(StopsPK key) {
        // Nothing here
    }

    @Override
    public void update(StopsDto item) {
        // Nothing here
    }

    /**
     * Selects a Stops dto from the STOPS table with the primary key given in parameters
     * @param primaryKey the primary key of the Stop Dto
     * @return the Stops dto with the primary key given as parameter
     */
    @Override
    public StopsDto select(StopsPK primaryKey) {
        String query = "SELECT id_line, id_station, id_order FROM STOPS WHERE id_line = ? AND id_station = ?";
        StopsDto stopsDto = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, primaryKey.getIdLine());
            pstmt.setInt(2, primaryKey.getIdStation());

            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                int id_order = result.getInt(3);
                stopsDto = new StopsDto(id_order, primaryKey.getIdLine(), primaryKey.getIdStation());
            }
        } catch (SQLException e) {
            System.out.println("There was an error trying to execute a statement");
        }
        return stopsDto;
    }

    /**
     * Method that returns a list containing all the stops dto in the STOPS table
     * @return list of stops dto
     */
    @Override
    public List<StopsDto> selectAll() {
        List<StopsDto> allStops = new ArrayList<>();
        String query = "SELECT id_line, id_station, id_order FROM STOPS";
        try {
            Statement stmt = connection.createStatement();

            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                int id_line = result.getInt(1);
                int id_station = result.getInt(2);
                int id_order = result.getInt(3);
                StopsDto stopsDto = new StopsDto(id_line, id_station, id_order);
                allStops.add(stopsDto);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't execute statement: " + e.getMessage());
        }
        return allStops;
    }
}
