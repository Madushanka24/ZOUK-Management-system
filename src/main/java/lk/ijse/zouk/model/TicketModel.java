package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.GuestsDto;
import lk.ijse.zouk.dto.TicketDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketModel {

    public boolean saveTicket(TicketDto ticketDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tickets VALUES (?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setDouble(1, ticketDto.getVipTicketPrice());
        ptsm.setDouble(2, ticketDto.getPlatinumTicketPrice());
        ptsm.setString(3, ticketDto.getEventID());
        return ptsm.executeUpdate() > 0;
    }

    public List<TicketDto> getAllTickes() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tickets";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<TicketDto> ticketDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            ticketDtoArrayList.add(
                    new TicketDto(
                            resultSet.getDouble(1),
                            resultSet.getDouble(2),
                            resultSet.getString(3)
                    )
            );
        }
        return ticketDtoArrayList;
    }

    public boolean deleteTickets(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM tickets WHERE eventID = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateTicket(TicketDto ticketDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE guest SET vipPrice = ?, platinumPrice = ?  WHERE eventID = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDouble(1, ticketDto.getVipTicketPrice());
        pstm.setDouble(2, ticketDto.getPlatinumTicketPrice());
        pstm.setString(3, ticketDto.getEventID());

        return pstm.executeUpdate() > 0;
    }
}
