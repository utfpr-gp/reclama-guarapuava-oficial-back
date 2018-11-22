package br.edu.utfpr.reclamaguarapuava.model.dto;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewOccurrenceDTO {
    private Occurrence.OccurrenceStatus status = Occurrence.OccurrenceStatus.UNRESOLVED;;
    private AddressDTO address;
    private Long userId;    
}
