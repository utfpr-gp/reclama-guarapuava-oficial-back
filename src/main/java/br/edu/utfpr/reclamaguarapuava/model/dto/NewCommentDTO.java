package br.edu.utfpr.reclamaguarapuava.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Carlos Henrique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDTO {

    private LocalDate dateCommentCreated;
    private Long userId;
    private Long occurrenceId;
    private String description;
}
