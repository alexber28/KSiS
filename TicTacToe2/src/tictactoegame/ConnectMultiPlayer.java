/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoegame;

import javax.swing.*;
import java.net.URL;

public class ConnectMultiPlayer extends javax.swing.JFrame {

    private String ip;
    private int port;
    private String username;
    public ConnectMultiPlayer() {
        super("Multiplayer- Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setSize(507, 525);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        ipInput = new javax.swing.JTextField();
        ipLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        header = new javax.swing.JLabel();
        portInput = new javax.swing.JTextField();
        submit = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        userInput = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(ipInput);
        ipInput.setBounds(110, 140, 190, 20);

        ipLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ipLabel.setForeground(new java.awt.Color(255, 255, 255));
        ipLabel.setText("IP :");
        getContentPane().add(ipLabel);
        ipLabel.setBounds(30, 140, 34, 20);

        portLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        portLabel.setForeground(new java.awt.Color(255, 255, 255));
        portLabel.setText("Port");
        getContentPane().add(portLabel);
        portLabel.setBounds(30, 190, 26, 17);

        header.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        header.setForeground(new java.awt.Color(255, 255, 255));
        header.setText("Please enter your IP and Port number");
        getContentPane().add(header);
        header.setBounds(70, 60, 370, 30);
        getContentPane().add(portInput);
        portInput.setBounds(110, 190, 90, 20);

        submit.setText("Proceed");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        getContentPane().add(submit);
        submit.setBounds(310, 260, 100, 23);

        userLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        userLabel.setForeground(new java.awt.Color(255, 255, 255));
        userLabel.setText("Username:");
        getContentPane().add(userLabel);
        userLabel.setBounds(30, 90, 110, 40);
        getContentPane().add(userInput);
        userInput.setBounds(110, 100, 190, 20);

        URL imageURL=getClass().getClassLoader().getResource("background.png");
        ImageIcon icon;
        if (imageURL != null) {
            icon = new ImageIcon(imageURL);
            background.setIcon(icon); // NOI18N
        }
        else
            System.out.println("Url is null");
        getContentPane().add(background);
        background.setBounds(0, 0, 505, 530);

        pack();
    }

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        ip=ipInput.getText();
        port=Integer.parseInt(portInput.getText());
        username=userInput.getText();
        System.out.println("IP: "+ip+", Port: "+port+", Username: "+username);
        new TicTacToe(ip, port, username);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConnectMultiPlayer().setVisible(true);
            }
        });
    }

    private javax.swing.JLabel background;
    private javax.swing.JLabel header;
    private javax.swing.JTextField ipInput;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JTextField portInput;
    private javax.swing.JLabel portLabel;
    private javax.swing.JButton submit;
    private javax.swing.JTextField userInput;
    private javax.swing.JLabel userLabel;

}
