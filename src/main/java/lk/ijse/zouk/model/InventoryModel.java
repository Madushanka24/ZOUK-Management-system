package lk.ijse.zouk.model;

import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.InventoryDto;
import lk.ijse.zouk.dto.InventorySupplierDto;
import lk.ijse.zouk.dto.StaffDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryModel {

    private String splitInventoryID(String currentInventoryID){
        if (currentInventoryID != null){
            String [] split = currentInventoryID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "I00" + id;
        }else {
            return "I001";
        }
    }

    public String generateNextInventoryID() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM inventory ORDER BY id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitInventoryID(resultSet.getString(1));
        }
        return splitInventoryID(null);
    }
    public boolean saveInventory(InventoryDto inventoryDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO inventory VALUES (?,?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, inventoryDto.getId());
        ptsm.setString(2, inventoryDto.getType());
        ptsm.setDouble(3, inventoryDto.getPrice());
        ptsm.setString(4, inventoryDto.getDescription());
        ptsm.setInt(5, inventoryDto.getQuantity());
        return ptsm.executeUpdate() > 0;
    }

    public boolean saveinventorySupplierDetails(InventorySupplierDto inventorySupplierDto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO inventorysupplierdetails VALUES (?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, inventorySupplierDto.getInventoryID());
        ptsm.setString(2, inventorySupplierDto.getSupplierID());
        return ptsm.executeUpdate() > 0;
    }

    public List<InventoryDto> getAllInventory() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM inventory";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<InventoryDto> inventoryDtoArrayList = new ArrayList<>();

        while(resultSet.next()) {
            inventoryDtoArrayList.add(
                    new InventoryDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDouble(3),
                            resultSet.getString(4),
                            resultSet.getInt(5)
                    )
            );
        }
        return inventoryDtoArrayList;
    }

    public boolean deleteInventory(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM inventory WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateInventory(InventoryDto inventoryDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE inventory SET type = ?, price = ?, description = ?, quantity = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, inventoryDto.getType());
        pstm.setDouble(2, inventoryDto.getPrice());
        pstm.setString(3, inventoryDto.getDescription());
        pstm.setInt(4, inventoryDto.getQuantity());
        pstm.setString(5, inventoryDto.getId());
        return pstm.executeUpdate() > 0;
    }
}
