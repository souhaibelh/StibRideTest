package mvp.model.db.dao;
import mvp.exceptions.model.NullDestinationStation;
import mvp.exceptions.model.NullOriginStation;
import mvp.model.db.DBManager;
import mvp.model.db.dto.FavoritesDto;
import mvp.exceptions.database.FavoritesTableUKViolation;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.tablepk.FavoritesPK;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritesDao implements Dao<FavoritesPK, FavoritesDto> {
    private final Connection connection;

    public FavoritesDao() {
        connection = DBManager.getInstance().getConnection();
    }

    @Override
    public FavoritesDto select(FavoritesPK favPK) {
        String query = "SELECT f.name, f.id_origin, f.id_destination, origin.name, destination.name, f.id FROM FAVORITES f" +
                " JOIN STATIONS origin ON f.id_origin = origin.id" +
                " JOIN STATIONS destination ON f.id_destination = destination.id" +
                " WHERE f.id = ?";
        FavoritesDto favDto = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, favPK.getId());

            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                favDto = new FavoritesDto(
                        result.getString(1),
                        new StationsDto(result.getString(4), result.getInt(2)),
                        new StationsDto(result.getString(5), result.getInt(3))
                );
                favDto.setId(result.getInt(6));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return favDto;
    }

    @Override
    public void insert(FavoritesDto dto) throws FavoritesTableUKViolation {
        String query = "INSERT INTO FAVORITES(name, id_origin, id_destination) VALUES(?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, dto.getName());
            pstmt.setInt(2, dto.getOrigin().getIdStation());
            pstmt.setInt(3, dto.getDestination().getIdStation());

            int count = pstmt.executeUpdate();
            System.out.println("Added " + count + " in favorites table.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new FavoritesTableUKViolation(
                        "FAVORITESDAO : INSERTALL -> " + "UNIQUE KEY VIOLATION",
                        dto.getOrigin(),
                        dto.getDestination()
                );
            }
        }
    }

    @Override
    public void delete(FavoritesPK key) {
        String query = "DELETE FROM FAVORITES WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, key.getId());

            int count = pstmt.executeUpdate();
            System.out.println("Deleted " + count + " in favorites table.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(FavoritesDto item) {
        String query = "UPDATE FAVORITES SET name = ?, id_origin = ?, id_destination = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getOrigin().getIdStation());
            pstmt.setInt(3, item.getDestination().getIdStation());
            pstmt.setInt(4, item.getId());

            int count = pstmt.executeUpdate();
            System.out.println("Updated " + count + " in favorites table.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<FavoritesDto> selectAll() {
        List<FavoritesDto> list = new ArrayList<>();
        String query = "SELECT f.name, id_origin, id_destination, origin.name, destination.name, f.id FROM FAVORITES f" +
                " JOIN STATIONS origin ON id_origin = origin.id" +
                " JOIN STATIONS destination ON id_destination = destination.id";
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                String name = result.getString(1);
                int id_origin = result.getInt(2);
                int id_destination = result.getInt(3);
                String name_origin = result.getString(4);
                String name_destination = result.getString(5);
                FavoritesDto favorite = new FavoritesDto(
                        name,
                        new StationsDto(name_origin, id_origin),
                        new StationsDto(name_destination, id_destination)
                );
                favorite.setId(result.getInt(6));
                list.add(favorite);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public int updateAll(List<FavoritesDto> updateList) throws FavoritesTableUKViolation {
        String query = "UPDATE FAVORITES SET name = ?, id_origin = ?, id_destination = ? WHERE id = ?";
        int count = 0;
        StationsDto currentOrigin = null;
        StationsDto currentDestination = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            connection.setAutoCommit(false);

            for (FavoritesDto dto : updateList) {
                pstmt.setString(1, dto.getName());
                pstmt.setInt(2, dto.getOrigin().getIdStation());
                pstmt.setInt(3, dto.getDestination().getIdStation());
                pstmt.setInt(4, dto.getId());
                currentOrigin = dto.getOrigin();
                currentDestination = dto.getDestination();
                count += pstmt.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new FavoritesTableUKViolation(
                        "FAVORITESDAO : INSERTALL -> " + "UNIQUE KEY VIOLATION",
                        currentOrigin,
                        currentDestination
                );
            }
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException f) {
                System.out.println("Couldn't rollback");
            }
        }
        return count;
    }

    public int insertAll(List<FavoritesDto> addList) throws FavoritesTableUKViolation {
        String query = "INSERT INTO FAVORITES(name, id_origin, id_destination) VALUES (?,?,?)";
        int count = 0;
        StationsDto currentOrigin = null;
        StationsDto currentDestination = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            connection.setAutoCommit(false);

            for (FavoritesDto dto : addList) {
                pstmt.setString(1, dto.getName());
                pstmt.setInt(2, dto.getOrigin().getIdStation());
                pstmt.setInt(3, dto.getDestination().getIdStation());
                currentOrigin = dto.getOrigin();
                currentDestination = dto.getDestination();
                count += pstmt.executeUpdate();
            }

            System.out.println("Added " + count + " in favorites");
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new FavoritesTableUKViolation(
                        "FAVORITESDAO : INSERTALL -> " + "UNIQUE KEY VIOLATION",
                        currentOrigin,
                        currentDestination
                );
            }
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Couldn't rollback");
            }
        }
        return count;
    }
}
