package fr.pangolins.leProPlusPlus.responses;

public class ErrorResponse extends RuntimeException {
    private String message;
    private String localizedMessage;

    public ErrorResponse(String message, String localizedMessage) {
        this.message = message;
        this.localizedMessage = localizedMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }
}
