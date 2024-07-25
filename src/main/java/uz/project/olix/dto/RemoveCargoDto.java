package uz.project.olix.dto;

import java.util.List;

public record RemoveCargoDto(
        List<Long> cargoIds
) {}
