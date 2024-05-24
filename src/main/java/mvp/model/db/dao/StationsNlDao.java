package mvp.model.db.dao;

import mvp.model.db.DBManager;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.tablepk.StationsPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationsNlDao implements Dao<StationsPK, StationsDto> {
    private final Connection connection;

    public StationsNlDao() {
        connection = DBManager.getInstance().getConnection();
    }

    @Override
    public void insert(StationsDto item) {

    }

    @Override
    public void delete(StationsPK key) {

    }

    @Override
    public void update(StationsDto item) {

    }

    @Override
    public StationsDto select(StationsPK key) {
        String query = "SELECT id, name FROM STATIONS_NL WHERE id = ?";
        StationsDto dto = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, key.getIdStation());

            ResultSet result = pstmt.getResultSet();
            while (result.next()) {
                dto = new StationsDto(
                        result.getString(2),
                        result.getInt(1)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dto;
    }

    @Override
    public List<StationsDto> selectAll() {
        List<StationsDto> list = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT id, name FROM STATIONS_NL";

            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                list.add(
                        new StationsDto(
                                result.getString(2),
                                result.getInt(1)
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
