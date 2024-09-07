package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.GuestsDto;
import lk.ijse.zouk.dto.TableDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableModel {

    private String splitTableID(String currentTableID){
        if (currentTableID != null){
            String [] split = currentTableID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "T00" + id;
        }else {
            return "T001";
        }
    }

    public String generateNextTableID() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM tables ORDER BY id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitTableID(resultSet.getString(1));
        }
        return splitTableID(null);
    }
    public boolean saveTable(TableDto tableDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tables VALUES (?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, tableDto.getId());
        ptsm.setString(2, tableDto.getType());
        ptsm.setString(3, tableDto.getReservationId());

        return ptsm.executeUpdate() > 0;
    }

    public List<TableDto> getAllTables() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tables";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<TableDto> tableDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            tableDtoArrayList.add(
                    new TableDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return tableDtoArrayList;
    }

    public boolean deleteTable(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM tables WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateTable(TableDto tableDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE guest SET type = ?, reservationID = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, tableDto.getType());
        pstm.setString(2, tableDto.getReservationId());
        pstm.setString(3, tableDto.getId());

        return pstm.executeUpdate() > 0;
    }
}
