package lk.ijse.zouk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private double vipTicketPrice;
    private double platinumTicketPrice;
    private String eventID;
}
