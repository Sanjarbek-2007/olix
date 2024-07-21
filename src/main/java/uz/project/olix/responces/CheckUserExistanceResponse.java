package uz.project.olix.responces;

public record CheckUserExistanceResponse(
        Boolean exists,
        String credentials
){}
