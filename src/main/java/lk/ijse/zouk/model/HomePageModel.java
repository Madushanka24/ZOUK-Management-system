package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.HomePageDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePageModel {
    public boolean updateSummary(HomePageDto homePageDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE monthsummary SET totalProfit = ?, soldTickets = ?, totalEvents = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDouble(1, homePageDto.getTotalProfit());
        pstm.setInt(2, homePageDto.getSoldTickets());
        pstm.setInt(3, homePageDto.getTotalEvents());
        pstm.setString(4, homePageDto.getId());
        return pstm.executeUpdate() > 0;
    }

    public List<HomePageDto> getAllSummary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM monthsummary";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<HomePageDto> homePageDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            homePageDtoArrayList.add(
                    new HomePageDto(
                            resultSet.getString(1),
                            resultSet.getDouble(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4)
                    )
            );
        }
        return homePageDtoArrayList;
    }

}
