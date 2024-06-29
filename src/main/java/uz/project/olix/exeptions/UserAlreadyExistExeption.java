package uz.project.olix.exeptions;

public class UserAlreadyExistExeption extends Throwable {
    public UserAlreadyExistExeption(String message) {
        super("User with : " + message+" already exist" );
    }
}
