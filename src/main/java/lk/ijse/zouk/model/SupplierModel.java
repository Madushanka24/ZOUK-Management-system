package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.InventoryDto;
import lk.ijse.zouk.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    private String splitSupplierID(String currentSupplierID){
        if (currentSupplierID != null){
            String [] split = currentSupplierID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "S00" + id;
        }else {
            return "S001";
        }
    }

    public String generateNextSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM supplier ORDER BY id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitSupplierID(resultSet.getString(1));
        }
        return splitSupplierID(null);
    }
    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier VALUES (?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, supplierDto.getId());
        ptsm.setString(2, supplierDto.getName());
        ptsm.setInt(3, supplierDto.getContact());
        return ptsm.executeUpdate() > 0;
    }

    public ArrayList<SupplierDto> getAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<SupplierDto> supplierDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            supplierDtoArrayList.add(
                    new SupplierDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3)
                    )
            );
        }
        return supplierDtoArrayList;
    }

    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET name = ?, contact = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, supplierDto.getName());
        pstm.setInt(2, supplierDto.getContact());
        pstm.setString(3, supplierDto.getId());
        return pstm.executeUpdate() > 0;
    }
}
