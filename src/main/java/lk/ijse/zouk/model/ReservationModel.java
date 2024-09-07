package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.GuestsDto;
import lk.ijse.zouk.dto.ReservationDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel {

    private String splitReservationID(String currentGuestID){
        if (currentGuestID != null){
            String [] split = currentGuestID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "I00" + id;
        }else {
            return "I001";
        }
    }

    public String generateNextReservationID() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM reservation ORDER BY id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitReservationID(resultSet.getString(1));
        }
        return splitReservationID(null);
    }
    public boolean saveReservation(ReservationDto reservationDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO reservation VALUES (?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, reservationDto.getId());
        ptsm.setDate(2, Date.valueOf(reservationDto.getDate()));
        ptsm.setInt(3, reservationDto.getNumberOfGuests());
        ptsm.setString(4, reservationDto.getGuestID());
        return ptsm.executeUpdate() > 0;
    }

    public List<ReservationDto> getAllReservations() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM reservation";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<ReservationDto> reservationDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            reservationDtoArrayList.add(
                    new ReservationDto(
                            resultSet.getString(1),
                            resultSet.getDate(2).toLocalDate(),
                            resultSet.getInt(3),
                            resultSet.getString(4)
                    )
            );
        }
        return reservationDtoArrayList;
    }

    public boolean deleteReservation(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM reservation WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateReservation(ReservationDto reservationDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE reservation SET date = ?, numberofGuest = ?, guestID = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(reservationDto.getDate()));
        pstm.setInt(2, reservationDto.getNumberOfGuests());
        pstm.setString(3, reservationDto.getGuestID());
        pstm.setString(4, reservationDto.getId());

        return pstm.executeUpdate() > 0;
    }
}
