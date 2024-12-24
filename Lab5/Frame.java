import javax.swing.*;
import java.awt.*;

public class Frame {
    private static JFrame frame;
    private static ImageIcon closedDoors;
    private static ImageIcon openDoors;
    private static JLayeredPane layeredPane;
    private static JLabel[][] closedElevators;
    private static JLabel[][] openElevators;
    private static JTextArea logArea;

    public static void createFrame() {
        frame = new JFrame("Elevators Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        frame.add(layeredPane, BorderLayout.CENTER);

        ImageIcon wallIcon = new ImageIcon("wall.jpg");
        closedDoors = new ImageIcon("closed.png");
        openDoors = new ImageIcon("open.png");

        JLabel wall = new JLabel(wallIcon);
        closedElevators = new JLabel[2][4];
        openElevators = new JLabel[2][4];

        layeredPane.add(wall, JLayeredPane.DEFAULT_LAYER);
        wall.setBounds(0, 0, wallIcon.getIconWidth(), wallIcon.getIconHeight());
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                closedElevators[i][j] = new JLabel(closedDoors);
                openElevators[i][j] = null;
                layeredPane.add(closedElevators[i][j], JLayeredPane.PALETTE_LAYER);
            }
        }

        int x1 = 266, x2 = 905;
        for (int i = 0; i < 4; i++) {
            closedElevators[0][i].setBounds(x1, (3-i) * 144, closedDoors.getIconWidth(), closedDoors.getIconHeight());
            closedElevators[1][i].setBounds(x2, (3-i) * 144, closedDoors.getIconWidth(), closedDoors.getIconHeight());
        }

        logArea = new JTextArea(7, 1);
        logArea.setEditable(false);
        Font font = new Font("Arial", Font.BOLD, 12);
        logArea.setFont(font);
        logArea.setForeground(Color.WHITE);

        Color backgroundColor = new Color(28, 28, 28);
        logArea.setBackground(backgroundColor);

        JScrollPane logScrollPane = new JScrollPane(logArea);
        frame.add(logScrollPane, BorderLayout.SOUTH);

        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void changeToOpen(int num, int floor) {
        layeredPane.remove(closedElevators[num-1][floor-1]);
        openElevators[num-1][floor-1] = new JLabel(openDoors);
        layeredPane.add(openElevators[num-1][floor-1], JLayeredPane.PALETTE_LAYER);
        openElevators[num-1][floor-1].setBounds(closedElevators[num-1][floor-1].getBounds());
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public static void changeToClosed(int num, int floor) {
        layeredPane.remove(openElevators[num-1][floor-1]);
        openElevators[num-1][floor-1] = null;
        layeredPane.add(closedElevators[num-1][floor-1], JLayeredPane.PALETTE_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public static void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
}
