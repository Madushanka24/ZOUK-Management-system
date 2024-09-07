package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.GuestsDto;
import lk.ijse.zouk.dto.StaffDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestModel {

    private String splitGuestID(String currentGuestID){
        if (currentGuestID != null){
            String [] split = currentGuestID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "G00" + id;
        }else {
            return "G001";
        }
    }

    public String generateNextGuestID() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM guest ORDER BY id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitGuestID(resultSet.getString(1));
        }
        return splitGuestID(null);
    }
    public boolean saveGuest(GuestsDto guestsDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO guest VALUES (?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, guestsDto.getId());
        ptsm.setString(2, guestsDto.getName());
        ptsm.setInt(3, guestsDto.getContact());
        return ptsm.executeUpdate() > 0;
    }

    public List<GuestsDto> getAllGuest() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM guest";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<GuestsDto> guestsDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            guestsDtoArrayList.add(
                    new GuestsDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3)
                    )
            );
        }
        return guestsDtoArrayList;
    }

    public boolean deleteGuest(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM guest WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateGuest(GuestsDto guestsDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE guest SET name = ?, contact = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, guestsDto.getName());
        pstm.setInt(2, guestsDto.getContact());
        pstm.setString(3, guestsDto.getId());

        return pstm.executeUpdate() > 0;
    }

}
