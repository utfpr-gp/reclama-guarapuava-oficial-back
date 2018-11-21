package br.edu.utfpr.reclamaguarapuava.occurrences.dtos;

import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Occurrence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOccurrenceDTO {
    private Occurrence.OccurrenceStatus status;
    private AddressDTO address;
    private Long userId;

    public NewOccurrenceDTO() {
        this.status = Occurrence.OccurrenceStatus.UNRESOLVED;
    }
}
