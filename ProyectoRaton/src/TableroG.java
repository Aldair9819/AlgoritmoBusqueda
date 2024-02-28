import javax.swing.*;
import java.awt.*;

public class TableroG extends JFrame {

    private double[][] matriz;
    private Casilla[][] tablero;

    public TableroG(double[][] matriz, Tablero t) {
        this.matriz = matriz;
        this.tablero = t.getTablero();

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
            int separation = 1;

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    int x = j * (cellSize + separation);
                    int y = i * (cellSize + separation);

                    // Dibujar el cuadrado
                    g.drawRect(x, y, cellSize, cellSize);

                    // Mostrar el número en el centro del cuadrado
                    String numero = String.valueOf(matriz[i][j]);
                    if(numero.equals("-1.0")){
                        numero = "X";
                    }else{
                        numero = numero.substring(0, 3);
                    }
                    int centerX = x + cellSize / 2 - 5; // Ajuste para centrar el número
                    int centerY = y + cellSize / 2 + 5; // Ajuste para centrar el número

                    // Colorear en verde si el número es múltiplo de 5
                    if (tablero[i][j].getEstado().equals(ESTADO.VISITADO.toString())) {
                        g.setColor(Color.GREEN);
                        g.fillRect(x, y, cellSize, cellSize);
                        g.setColor(Color.BLACK);
                    }

                    g.drawString(numero, centerX, centerY);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        // Ejemplo de matriz (puedes cambiarla según tus necesidades)
        double[][] ejemploMatriz = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25},
                {21, 22, 23, 24, 25},
                {21, 22, 23, 24, 25},
                {21, 22, 23, 24, 25},
                {21, 22, 23, 24, 25},
                {21, 22, 23, 24, 25},
                {21, 22, 23, 24, 25},
                {21, 22, 23, 24, 25}

        };

        SwingUtilities.invokeLater(() -> {
            new TableroG(ejemploMatriz, null);
        });
    }
    
}
