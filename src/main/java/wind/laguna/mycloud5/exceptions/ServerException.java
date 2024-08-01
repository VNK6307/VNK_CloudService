package wind.laguna.mycloud5.exceptions;

public class ServerException extends RuntimeException {

    public ServerException(String apiMessage, String message) {
        super(apiMessage + " : " + message);
    }
}


