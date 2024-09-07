package lk.ijse.zouk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto extends SupplierDto {
    private String id;
    private String type;
    private double price;
    private String description;
    private int quantity;


}
