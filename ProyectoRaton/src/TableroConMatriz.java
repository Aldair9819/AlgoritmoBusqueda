import javax.swing.*;

import java.awt.*;


public class TableroConMatriz extends JFrame {

    private int filas = 8;
    private int columnas = 8;
    private Casilla[][] tablero;

    public TableroConMatriz(Casilla[][] tablero) {
        this.tablero = tablero;
        this.filas = tablero.length;
        this.columnas = tablero[0].length;
    }

    public void mapaCoordenadas(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tablero con Matriz");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(filas, columnas));

        // Rellenar el tablero con colores alternos y datos de la matriz
        Color colorObstaculo = Color.BLACK;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Color color = Color.WHITE;
                String coordenada = "(" + j + "," + i + ")";
                switch(tablero[i][j].getEstado()){
                    case "OBSTACULO":
                        color = colorObstaculo;
                        coordenada = "";
                        break;
                    default:
                        break;
                }
                
                CasillaPanel cuadro = new CasillaPanel(coordenada);
                cuadro.setBackground(color);

                // Agregar un borde a cada casilla
                //cuadro.setBorder(new LineBorder(Color.BLACK, 1));

                panel.add(cuadro);
            }
        }

        add(panel);

        // Ajustar el tamaño de la fuente para que se ajuste a las casillas
        Font font = new Font("Arial", Font.PLAIN, 8);
        setFont(font);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tablero con Matriz");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(filas, columnas));

        // Rellenar el tablero con colores alternos y datos de la matriz
        Color colorVisitado = Color.BLUE;
        Color colorMejorRuta = Color.GREEN;
        Color colorObstaculo = Color.BLACK;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Color color = Color.WHITE;
                String coordenada = "";
                switch(tablero[i][j].getEstado()){
                    case "VISITADO":
                        color = colorVisitado;
                        break;
                    case "MEJORRECORRIDO":
                        color = colorMejorRuta;
                        break;
                    case "OBSTACULO":
                        color = colorObstaculo;
                        break;
                    case "INICIO":
                        color = Color.YELLOW;
                        coordenada = "(" + i + "," + j + ")";
                        break;
                    default:
                        break;
                }
                if(tablero[i][j].isMeta()){
                    color = Color.RED;
                    coordenada = "(" + i + "," + j + ")";
                }
                
                CasillaPanel cuadro = new CasillaPanel(coordenada);
                cuadro.setBackground(color);

                // Agregar un borde a cada casilla
                //cuadro.setBorder(new LineBorder(Color.BLACK, 1));

                panel.add(cuadro);
            }
        }

        add(panel);

        // Ajustar el tamaño de la fuente para que se ajuste a las casillas
        Font font = new Font("Arial", Font.PLAIN, 8);
        setFont(font);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static class CasillaPanel extends JPanel {
        private String valor;

        public CasillaPanel(String valor) {
            this.valor = valor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dibujar el valor en el centro de la casilla
            g.drawString(String.valueOf(valor), getWidth() / 5, getHeight() / 2);
        }
    }


}
