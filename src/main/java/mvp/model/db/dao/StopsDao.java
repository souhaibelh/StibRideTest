package mvp.model.db.dao;

import mvp.model.db.DBManager;
import mvp.model.db.tablepk.StopsPK;
import mvp.model.db.dto.StopsDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StopsDao implements Dao<StopsPK, StopsDto> {
    private final Connection connection;

    public StopsDao() {
        connection = DBManager.getInstance().getConnection();
    }

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

    }

    @Override
    public void delete(StopsPK key) {

    }

    @Override
    public void update(StopsDto item) {

    }

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
