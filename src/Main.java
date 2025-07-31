// git push --force origin main - only do this if changes were made somewhere else
// https://introcs.cs.princeton.edu/java/11cheatsheet/

import javax.swing.JFrame;

public class Main {
    // Main body of the program
    public static void main(String[] args) throws Exception {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Untitled Game");

        Render gameWindow = new Render();
        window.add(gameWindow);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gameWindow.startGameRendering();
    }
}
