package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.EventDto;
import lk.ijse.zouk.dto.GuestsDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventModel {
    private String splitEventId(String currentEventID){
        if (currentEventID != null){
            String [] split = currentEventID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "E00" + id;
        }else {
            return "E001";
        }
    }

    public String generateNextEventID() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM event ORDER BY id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitEventId(resultSet.getString(1));
        }
        return splitEventId(null);
    }
    public boolean saveEvent(EventDto eventDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO event VALUES (?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, eventDto.getId());
        ptsm.setString(2, eventDto.getName());
        ptsm.setDate(3, Date.valueOf(eventDto.getDate()));
        ptsm.setString(4, eventDto.getGuestArtist());
        return ptsm.executeUpdate() > 0;
    }

    public List<EventDto> getAllEvent() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM event";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<EventDto> eventDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            eventDtoArrayList.add(
                    new EventDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDate(3).toLocalDate(),
                            resultSet.getString(4)
                    )
            );
        }
        return eventDtoArrayList;
    }

    public boolean deleteEvent(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM event WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateGuest(EventDto eventDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE guest SET name = ?, date = ?, guestArtist = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, eventDto.getName());
        pstm.setDate(2, Date.valueOf(eventDto.getDate()));
        pstm.setString(3, eventDto.getGuestArtist());
        pstm.setString(4, eventDto.getId());

        return pstm.executeUpdate() > 0;
    }
}
