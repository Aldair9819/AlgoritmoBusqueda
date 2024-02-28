

public class Casilla {
    private int posX;
    private int posY;
    private ESTADO estado = ESTADO.NOVISITADO;
    private boolean meta = false;
    private double distanciaFinal;
    private Casilla padre = null;

    public Casilla(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

    }

    public String getEstado() {
        return estado.toString();
    }

    public void setEstado(ESTADO estado) {
        this.estado = estado;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isMeta() {
        return meta;
    }

    public void setMeta(boolean meta) {
        this.meta = meta;
    }

    public double getDistanciaFinal() {
        return distanciaFinal;
    }

    public void setDistanciaFinal(double distanciaFinal) {
        this.distanciaFinal = Math.abs(distanciaFinal);
    }

    public String toString() {
        return "Casilla{" + "posX=" + posX + ", posY=" + posY+"}\n";
    }

    public Casilla getPadre() {
        return padre;
    }

    public void setPadre(Casilla padre) {
        this.padre = padre;
    }

    
}
