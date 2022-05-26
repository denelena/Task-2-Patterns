package ru.netology.data;

public class LoginInfo {
    private String login;
    private String password;
    private String status;

    public LoginInfo(String login, String password, String status){
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
