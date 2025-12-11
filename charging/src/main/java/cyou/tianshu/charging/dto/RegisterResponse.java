package cyou.tianshu.charging.dto;

public class RegisterResponse {
    private boolean isSuccess;
    private String message;

    public RegisterResponse() {
        this.isSuccess = false;
        this.message = "Registration failed";
    }

    public RegisterResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}