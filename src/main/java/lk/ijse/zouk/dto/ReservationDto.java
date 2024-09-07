package lk.ijse.zouk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private String id;
    private LocalDate date;
    private int numberOfGuests;
    private String guestID;

}
