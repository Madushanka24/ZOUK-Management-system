package lk.ijse.zouk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String id;
    private String name;
    private LocalDate date;
    private String guestArtist;
}
