package FIUBA.CineXplore.dto;

import FIUBA.CineXplore.model.Actor;

public class ActorDTO {
    public Long id;
    public String fullName;

    public static ActorDTO toActorDTO(Actor actor) {
        ActorDTO dto = new ActorDTO();
        dto.id = actor.getActorId();
        dto.fullName = actor.getFullName();
        return dto;
    }
}
