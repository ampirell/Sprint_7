package courier;

public class courierCredentials {

    private String login;
    private String password;

    public courierCredentials() {
    }

    public courierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static courierCredentials from(Courier courier) {
        return new courierCredentials(courier.getLogin(), courier.getPassword());
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
