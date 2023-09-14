package password.storager;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PasswordData {
    private List<PasswordEntry> passwords;
    private File dataFile;

    public PasswordData() {
        passwords = new ArrayList<>();
        dataFile = new File("passwords.csv");
    }

    public void addPassword(String nickname, String password) {
        int id = getNextId();
        PasswordEntry entry = new PasswordEntry(id, nickname, password);
        passwords.add(entry);
    }

    public void deletePassword(int id) {
        passwords.removeIf(entry -> entry.getId() == id);
    }

    public int getNextId() {
        int maxId = 0;
        for (PasswordEntry entry : passwords) {
            if (entry.getId() > maxId) {
                maxId = entry.getId();
            }
        }
        return maxId + 1;
    }

    public List<PasswordEntry> getPasswords() {
        return passwords;
    }

    public void loadPasswords() {
        if (!dataFile.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    int id = Integer.parseInt(data[0]);
                    String nickname = data[1];
                    String encodedPassword = data[2];
                    String password = new String(Base64.getDecoder().decode(encodedPassword));
                    PasswordEntry entry = new PasswordEntry(id, nickname, password);
                    passwords.add(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePasswords() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (PasswordEntry entry : passwords) {
                String encodedPassword = Base64.getEncoder().encodeToString(entry.getPassword().getBytes());
                writer.write(entry.getId() + "," + entry.getNickname() + "," + encodedPassword);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
