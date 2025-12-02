package cyou.tianshu.charging.service;



public class UserService {
    public boolean login(String username, String password) {
        // Implement login logic here
        return "admin".equals(username) && "password".equals(password);
    }
}