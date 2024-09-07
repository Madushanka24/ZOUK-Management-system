package lk.ijse.zouk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePageDto {
    private String id;
    private double totalProfit;
    private int soldTickets;
    private int totalEvents;
}
