package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import database.DatabaseHandler;
import model.Person;

public class RankPanel extends JPanel implements ActionListener {
    DatabaseHandler connectDB;
    ArrayList<Person> listUser;
    JLabel[][] matrixUser;
    JPanel mainPanel;
    MainScreen mainScreen;

    public RankPanel(MainScreen mainScreen) {
    	this.mainScreen = mainScreen; 
        setLayout(null);
        setSize(1000, 600);
        ImageIcon img = resizeImgs("/icon/bg.jpg");
        JLabel labelBg = new JLabel(img);
        labelBg.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        add(labelBg);
        listUser = new ArrayList<Person>();
        connectDB = new DatabaseHandler();
        listUser = connectDB.getUsers();

        mainPanel = new JPanel(new GridLayout(9, 3));
        mainPanel.setBounds(0, img.getIconHeight(), 1000, 600 - img.getIconHeight());
        mainPanel.setOpaque(false);
        add(mainPanel);
        
        sort(listUser);
        matrixUser = createMatrixUser(listUser);
        displayUser();

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(890, 530, 80, 27);
        btnExit.setBackground(Color.CYAN);
        btnExit.addActionListener(this); // Thêm ActionListener cho nút Exit
        add(btnExit);
    }

    private JLabel[][] createMatrixUser(ArrayList<Person> listUser) {
        JLabel[][] matrix = new JLabel[listUser.size() + 1][3];
        matrix[0][0] = new JLabel("Name");
        matrix[0][0].setHorizontalAlignment(SwingConstants.CENTER);
        matrix[0][1] = new JLabel("Score");
        matrix[0][1].setHorizontalAlignment(SwingConstants.CENTER);
        matrix[0][2] = new JLabel("Test Number");
        matrix[0][2].setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i <= listUser.size(); i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    String userName = listUser.get(i - 1).getUserName();
                    matrix[i][j] = new JLabel(userName);
                    matrix[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                }
                if (j == 1) {
                    Double score = listUser.get(i - 1).getScore();
                    matrix[i][j] = new JLabel(Double.toString(score));
                    matrix[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                }
                if (j == 2) {
                    int testNumber = listUser.get(i - 1).getTestNumber();
                    matrix[i][j] = new JLabel(Integer.toString(testNumber));
                    matrix[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                }
            }
        }
        return matrix;
    }

    private void displayUser() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                mainPanel.add(matrixUser[i][j]);
            }
        }
    }

    public ImageIcon resizeImgs(String src) {
        ImageIcon icon = new ImageIcon(getClass().getResource(src));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(this.getWidth(), this.getHeight() / 4, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        return icon;
    }
    public void sort(ArrayList<Person> listUser) {
        int n = listUser.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (listUser.get(j).compareTo(listUser.get(j+1)) < 0) {
                    // swap listUser[j+1] and listUser[j]
                    Person temp = listUser.get(j);
                    listUser.set(j, listUser.get(j+1));
                    listUser.set(j+1, temp);
                }
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getText().equals("Exit")) {
            if (mainScreen != null) {
                mainScreen.showDialog("Bạn có muốn quay về màn hình chính?", "Warning",false);
            } else {
                System.out.println("MainScreen is null");
            }
        }
    }

    
}
