import java.util.Random;

public class Tablero {
    private Casilla[][] tablero;
    private int fila, columna;

    private Casilla casillaInicial;
    private Casilla casillaFinal;

    public Tablero(int fila, int columna, int porcentajeObstaculos) {
        this.fila = fila;
        this.columna = columna;
        this.tablero = new Casilla[fila][columna];

        for(int i = 0; i < fila; i++){
            for(int j = 0; j < columna; j++){
                tablero[i][j] = new Casilla(i, j);
            }
        }

        generarObstaculos(porcentajeObstaculos);
    }

    public void generarObstaculos(int porcentajeObstaculos){
        Random random = new Random();
        int obstaculos = (int) (this.fila * this.columna * porcentajeObstaculos / 100);
        for (int i = 0; i < obstaculos; i++) {
            while(true){
                int f = random.nextInt(this.fila);
                int c = random.nextInt(this.columna);
                if(!tablero[f][c].getEstado().equals(ESTADO.OBSTACULO.toString())){
                    tablero[f][c].setEstado(ESTADO.OBSTACULO);
                    break;
                }
            }
        }
    }

    public String toString() {
        String s = "   ";

        for(int i = 0; i < columna; i++){
            if(i<10){
                s += "0"+i+" ";
            }else{
                s += i+" ";
            }
            
        }
        s += "\n"+"   ";

        for(int i = 0; i < columna; i++){
            s += "___";
        }

        s += "\n";

        for(int i = 0; i < fila; i++){
            if(i<10){
                s += " "+i+"| ";
            }else{
                s += i+"| ";
            }
            for(int j = 0; j < columna; j++){
                switch(tablero[i][j].getEstado()){
                    case "NOVISITADO":
                        if(tablero[i][j].isMeta()){
                            s += "F  ";
                            casillaFinal = tablero[i][j];
                            break;
                        }
                        s += "N  ";
                        break;
                    case "VISITADO":
                        s += "V  ";     
                        break;
                    case "OBSTACULO":
                        s += "X  ";
                        break;
                    case "INICIO":
                        s += "I  ";
                        break;
                    case "ESTADOACTUAL":
                        s += "A  ";
                        tablero[i][j].setEstado(ESTADO.VISITADO);
                        break;
                    case "MEJORRECORRIDO":
                        s += "M  ";
                        break;
                    case "RECORRIDO":
                        s += "R  ";
                        break;
                }
                s+= "";
                    
            }
            s += "\n";
        }
        return s;
    }

    public void setCasillaInicial(int posX, int posY){
        
        try{
            if(tablero[posX][posY].isMeta()){
                System.out.println("No se puede colocar el inicio en esa posicion");
                return;
            }else if(tablero[posX][posY].getEstado().equals(ESTADO.OBSTACULO.toString())){
                System.out.println("No se puede colocar el inicio en esa posicion");
                return;
            }else{
            this.casillaInicial = tablero[posX][posY];
            this.casillaInicial.setEstado(ESTADO.INICIO);
            }
        
        }catch(Exception e){
            System.out.println("No se puede colocar el final en esa posicion");
        }
    }

    public void setCasillaFinal(int posX, int posY){
        try{
            if(tablero[posX][posY].getEstado().equals(ESTADO.INICIO.toString())){
                System.out.println("No se puede colocar el final en esa posicion");
                return;
            }else if(tablero[posX][posY].getEstado().equals(ESTADO.OBSTACULO.toString())){
                System.out.println("No se puede colocar el final en esa posicion");
                return;
            }else{
            this.casillaFinal = tablero[posX][posY];
            this.casillaFinal.setMeta(true);
            }
        
        }catch(Exception e){
            System.out.println("No se puede colocar el final en esa posicion");
        }
    }

    public boolean isCasillaInicialExist(){
        return this.casillaInicial != null;
    }

    public boolean isCasillaFinalExist(){
        return this.casillaFinal != null;
    }

    public boolean isDosCasillasLibres(){
        int contador = 0;
        for(int i = 0; i < fila; i++){
            for(int j = 0; j < columna; j++){
                if(tablero[i][j].getEstado().equals(ESTADO.NOVISITADO.toString())){
                    contador++;
                    if(contador == 2){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void resetTablero(){
        for(int i = 0; i < fila; i++){
            for(int j = 0; j < columna; j++){
                tablero[i][j].setPadre(null);
                if(tablero[i][j].getEstado().equals(ESTADO.VISITADO.toString())|| 
                tablero[i][j].getEstado().equals(ESTADO.MEJORRECORRIDO.toString())){
                    tablero[i][j].setEstado(ESTADO.NOVISITADO);
                }
                tablero[i][j].setDistanciaFinal(0);
            }
        }

        tablero[casillaInicial.getPosX()][casillaInicial.getPosY()].setEstado(ESTADO.INICIO);
        tablero[casillaInicial.getPosX()][casillaInicial.getPosY()].setDistanciaFinal(0);

    }

    public boolean isCasillaFinal(Casilla casilla){
        return casilla.getPosX() == casillaFinal.getPosX() && casilla.getPosY() == casillaFinal.getPosY();
    }

    public boolean isCasillaInicial(Casilla casilla){
        return casilla.getPosX() == casillaInicial.getPosX() && casilla.getPosY() == casillaInicial.getPosY();
    }
    
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public Casilla getCasilla(int posX, int posY){
        return tablero[posX][posY];
    }

    public Casilla getCasillaInicial() {
        return casillaInicial;
    }

    public Casilla getCasillaFinal() {
        return casillaFinal;
    }

    public Casilla[][] getTablero() {
        return tablero;
    }

    public double[][] getMatrizDistanciaFinal(){
        double[][] matriz = new double[this.fila][this.columna];
        for(int i = 0; i < this.fila; i++){
            for(int j = 0; j < this.columna; j++){
                if(this.tablero[i][j].getEstado().equals(ESTADO.OBSTACULO.toString())){
                    matriz[i][j] = -1;
                }else{
                    matriz[i][j] = this.tablero[i][j].getDistanciaFinal();
                }
               
            }
        }
        return matriz;
    }



    
    
}
