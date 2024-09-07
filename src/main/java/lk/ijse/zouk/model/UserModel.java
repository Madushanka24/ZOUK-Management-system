package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.RegistrationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public boolean registerUser(RegistrationDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO admin VALUES (?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, dto.getName());
        ptsm.setString(2, dto.getPassword());

        return ptsm.executeUpdate() > 0;
    }
    public boolean isValidUser(String userName, String pw) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM admin WHERE name = ? AND password = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, userName);
        ptsm.setString(2,pw);

        ResultSet resultSet = ptsm.executeQuery();

        return resultSet.next();
    }
    public boolean check(String user, String pw) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM admin WHERE name = ? AND password = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1, user);
        ptsm.setString(2,pw);

        ResultSet resultSet = ptsm.executeQuery();

        return resultSet.next();
    }


}
