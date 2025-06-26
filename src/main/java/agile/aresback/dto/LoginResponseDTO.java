package agile.aresback.dto;

public class LoginResponseDTO {
    private boolean success;
    private String role;
    private String message;
    private String token;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(boolean success, String role, String message, String token) {
        this.success = success;
        this.role = role;
        this.message = message;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
