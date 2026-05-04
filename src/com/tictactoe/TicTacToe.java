package com.tictactoe;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TicTacToe extends JFrame implements ActionListener {
    private final JButton[] buttons = new JButton[9];
    private boolean xTurn = true;
    private final JLabel statusLabel;
    private final JLabel scoreLabel;
    private final JPanel boardPanel;
    private final JButton resetButton;
    private int xScore = 0;
    private int oScore = 0;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(380, 520));
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(18, 28, 47));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));

        JLabel titleLabel = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 230, 180));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        statusLabel.setForeground(new Color(235, 235, 235));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        headerPanel.add(statusLabel, BorderLayout.CENTER);

        scoreLabel = new JLabel(getScoreText(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(200, 215, 255));
        headerPanel.add(scoreLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(3, 3, 8, 8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        boardPanel.setBackground(new Color(12, 18, 33));

        for (int i = 0; i < 9; i++) {
            buttons[i] = createGameButton();
            boardPanel.add(buttons[i]);
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(18, 28, 47));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));

        resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resetButton.setBackground(new Color(255, 165, 0));
        resetButton.setForeground(Color.BLACK);
        resetButton.setFocusPainted(false);
        resetButton.setBorder(new LineBorder(new Color(255, 200, 80), 2, true));
        resetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(e -> resetGame());
        footerPanel.add(resetButton, BorderLayout.CENTER);

        add(footerPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(new Color(12, 18, 33));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateButtonFonts();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createGameButton() {
        JButton button = new JButton("");
        button.setFont(new Font("Segoe UI", Font.BOLD, 48));
        button.setBackground(new Color(30, 46, 79));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(120, 170, 255), 3, true));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(50, 72, 120));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(30, 46, 79));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(20, 34, 62));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(50, 72, 120));
                }
            }
        });
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton.getText().isEmpty()) {
            if (xTurn) {
                clickedButton.setText("X");
                clickedButton.setForeground(new Color(255, 90, 90));
                statusLabel.setText("Player O's Turn");
            } else {
                clickedButton.setText("O");
                clickedButton.setForeground(new Color(115, 235, 255));
                statusLabel.setText("Player X's Turn");
            }
            xTurn = !xTurn;
            checkWinner();
        }
    }

    private void checkWinner() {
        int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] pos : winPositions) {
            String b1 = buttons[pos[0]].getText();
            String b2 = buttons[pos[1]].getText();
            String b3 = buttons[pos[2]].getText();

            if (!b1.isEmpty() && b1.equals(b2) && b1.equals(b3)) {
                statusLabel.setText("Player " + b1 + " Wins!");
                statusLabel.setForeground(new Color(255, 230, 180));
                highlightWinner(pos);
                if (b1.equals("X")) xScore++; else oScore++;
                scoreLabel.setText(getScoreText());
                disableButtons();
                return;
            }
        }

        boolean draw = true;
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                draw = false;
                break;
            }
        }

        if (draw) {
            statusLabel.setText("It's a Draw!");
            statusLabel.setForeground(new Color(192, 220, 255));
        }
    }

    private void highlightWinner(int[] pos) {
        for (int index : pos) {
            buttons[index].setBackground(new Color(80, 170, 110));
            buttons[index].setForeground(Color.WHITE);
        }
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
            button.setBackground(new Color(25, 34, 55));
            button.setBorder(new LineBorder(new Color(75, 110, 175), 2, true));
        }
    }

    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(new Color(30, 46, 79));
            button.setBorder(new LineBorder(new Color(120, 170, 255), 3, true));
            button.setForeground(Color.WHITE);
        }
        xTurn = true;
        statusLabel.setText("Player X's Turn");
        statusLabel.setForeground(new Color(235, 235, 235));
    }

    private String getScoreText() {
        return String.format("Score - X: %d   |   O: %d", xScore, oScore);
    }

    private void updateButtonFonts() {
        int size = Math.max(24, getWidth() / 10);
        for (JButton button : buttons) {
            button.setFont(new Font("Segoe UI", Font.BOLD, size));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}
