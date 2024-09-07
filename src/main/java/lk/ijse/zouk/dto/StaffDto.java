package lk.ijse.zouk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    private String id;
    private String name;
    private String address;
    private int contact;
    private String position;
    private double salary;

    public StaffDto(String name, String address, int contact, String position, double salary) {

    }
}
