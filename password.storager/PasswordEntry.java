package password.storager;


public class PasswordEntry {
    private int id;
    private String nickname;
    private String password;

    public PasswordEntry(int id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
