package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.RegistrationDto;
import lk.ijse.zouk.dto.StaffDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffModel {

    private String splitStaffId(String currentStaffId){
        if (currentStaffId != null){
            String [] split = currentStaffId.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "S00" + id;
        }else {
            return "S001";
        }
    }

    public String generateNextStaff() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM staff ORDER BY id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitStaffId(resultSet.getString(1));
        }
        return splitStaffId(null);
    }

    public boolean saveStaff(StaffDto staffDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO staff VALUES (?,?,?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, staffDto.getId());
        ptsm.setString(2, staffDto.getName());
        ptsm.setString(3, staffDto.getAddress());
        ptsm.setInt(4, staffDto.getContact());
        ptsm.setString(5, staffDto.getPosition());
        ptsm.setDouble(6, staffDto.getSalary());
        return ptsm.executeUpdate() > 0;
    }

    public List<StaffDto> getAllStaff() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM staff";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<StaffDto> staffDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            staffDtoArrayList.add(
                    new StaffDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getDouble(6)
                    )
            );
        }
        return staffDtoArrayList;
    }

    public boolean deleteStaff(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM staff WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updatestaff(StaffDto staffDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE staff SET name = ?, address = ?, contact = ?, position = ?, salary = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, staffDto.getName());
        pstm.setString(2, staffDto.getAddress());
        pstm.setInt(3, staffDto.getContact());
        pstm.setString(4, staffDto.getPosition());
        pstm.setDouble(5, staffDto.getSalary());
        pstm.setString(6, staffDto.getId());

        return pstm.executeUpdate() > 0;
    }
}
