package tictactoegame;

import java.awt.Color;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindowForm extends javax.swing.JFrame {

    public MainWindowForm() {
       super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setSize(507, 525);
        setVisible(true);
    }

    private void initComponents() {

        header = new javax.swing.JLabel();
        singlePlayer = new javax.swing.JButton();
        multiPlayer = new javax.swing.JButton();
        subHeader = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        header.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        header.setForeground(new java.awt.Color(255, 150, 100));
        header.setText("Tic Tac Toe");
        getContentPane().add(header);
        header.setBounds(190, 70, 150, 50);

        singlePlayer.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        singlePlayer.setText("Single Player");
        singlePlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                singlePlayerActionPerformed(evt);
            }
        });
        getContentPane().add(singlePlayer);
        singlePlayer.setBounds(60, 260, 130, 40);

        multiPlayer.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        multiPlayer.setText("Multiplayer");
        multiPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    multiPlayerActionPerformed(evt);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        getContentPane().add(multiPlayer);
        multiPlayer.setBounds(330, 260, 130, 40);

        subHeader.setFont(new java.awt.Font("Algerian", 0, 36)); // NOI18N
        subHeader.setForeground(new java.awt.Color(255, 150, 100));
        subHeader.setText("Choose a mode to Continue");
        getContentPane().add(subHeader);
        subHeader.setBounds(10, 120, 500, 40);

        background.setPreferredSize(new java.awt.Dimension(505, 526));
        getContentPane().add(background);
        background.setBounds(0, 0, 505, 530);

        pack();
    }

    private void singlePlayerActionPerformed(java.awt.event.ActionEvent evt) {
        new SinglePlayer();
        setVisible(false);
    }

    private void multiPlayerActionPerformed(java.awt.event.ActionEvent evt) throws MalformedURLException {
        setVisible(false);
        new ConnectMultiPlayer();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindowForm().setVisible(true);
            }
        });
    }

    private javax.swing.JLabel background;
    private javax.swing.JLabel header;
    private javax.swing.JButton multiPlayer;
    private javax.swing.JButton singlePlayer;
    private javax.swing.JLabel subHeader;

}
