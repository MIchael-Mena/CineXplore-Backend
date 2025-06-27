package FIUBA.CineXplore.dto;

import FIUBA.CineXplore.model.Director;

public class DirectorDTO {
    public Long id;
    public String fullName;

    public static DirectorDTO toDirectorDTO(Director director) {
        DirectorDTO dto = new DirectorDTO();
        dto.id = director.getDirectorId();
        dto.fullName = director.getFullName();
        return dto;
    }
}
