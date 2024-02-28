import javax.swing.*;
import java.awt.*;

public class Pruebas extends JFrame {

    private int[][] matriz;

    public Pruebas(int[][] matriz) {
        this.matriz = matriz;

        initUI();
    }

    private void initUI() {
        setTitle("Tablero");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new BoardPanel());

        setVisible(true);
    }

    class BoardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int filas = matriz.length;
            int columnas = matriz[0].length;

            int cellSize = 50;
            int separation = 10;

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    String numero = String.valueOf(matriz[i][j]);
                    int x = j * (cellSize + separation) + separation;
                    int y = i * (cellSize + separation) + separation;
                    g.drawString(numero, x, y);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Ejemplo de matriz (puedes cambiarla según tus necesidades)
        int[][] ejemploMatriz = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };

        SwingUtilities.invokeLater(() -> {
            new Pruebas(ejemploMatriz);
        });
    }
}
