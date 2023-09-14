//This .class contains GUI of the app
package password.storager;

//---LIBRARIES---//
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordStorageApp extends JFrame {
    //---GLOBAL-VARIABILES---//
    private JTable passwordTable;
    private DefaultTableModel tableModel;
    private JTextField nicknameField;
    private JPasswordField passwordField;
    private PasswordData passwordData;

    //---PasswordStoagerApp---//
    public PasswordStorageApp() {
        setTitle("Password Storage App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        passwordData = new PasswordData();

        // Initialize components
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nickname");
        tableModel.addColumn("Password");
        passwordTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(passwordTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        nicknameField = new JTextField();
        passwordField = new JPasswordField();
        JButton addButton = new JButton("Add Password");
        JButton deleteButton = new JButton("Delete Password");
        JButton editButton = new JButton("Edit");   

        //---ADD-BUTTON-ACTIONLISTENER---//
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPassword();
            }
        });
        
        //---EDIT-BUTTON-ACTIONLISTENER---//
        editButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                editPassword();
            }
        });

        //---DELETE-BUTTON-ACTIONLISTENER---//
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePassword();
            }
        });

        inputPanel.add(new JLabel("Nickname:"));
        inputPanel.add(nicknameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.SOUTH);

        loadPasswords();

        setVisible(true);
    }

    private void addPassword() {
        String nickname = nicknameField.getText();
        String password = new String(passwordField.getPassword());

        if (!nickname.isEmpty() && !password.isEmpty()) {
            passwordData.addPassword(nickname, password);
            tableModel.addRow(new Object[]{passwordData.getNextId(), nickname, password});
            passwordData.savePasswords();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a nickname and password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editPassword(){
        
    }
    
    private void deletePassword() {
        int selectedRow = passwordTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) passwordTable.getValueAt(selectedRow, 0);
            passwordData.deletePassword(id);
            tableModel.removeRow(selectedRow);
            passwordData.savePasswords();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a password to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nicknameField.setText("");
        passwordField.setText("");
    }

    private void loadPasswords() {
        passwordData.loadPasswords();
        for (PasswordEntry entry : passwordData.getPasswords()) {
            tableModel.addRow(new Object[]{entry.getId(), entry.getNickname(), entry.getPassword()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PasswordStorageApp();
            }
        });
    }
}
