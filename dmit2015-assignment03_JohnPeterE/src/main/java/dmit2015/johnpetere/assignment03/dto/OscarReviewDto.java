package dmit2015.johnpetere.assignment03.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OscarReviewDTO {
    private Long id;
    private String category;
    private String review;
    private String username;
    private String nominee;
    private LocalDateTime createdDateTIme;
    private LocalDateTime lastModifiedDateTIme;
}
