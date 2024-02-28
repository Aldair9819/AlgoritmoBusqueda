public enum ESTADO {
    NOVISITADO("NOVISITADO"),VISITADO("VISITADO"), 
    OBSTACULO("OBSTACULO"), INICIO("INICIO"),
    ESTADOACTUAL("ESTADOACTUAL"),MEJORRECORRIDO("MEJORRECORRIDO");
    private String estado;

    private ESTADO(String estado) {
        this.estado = estado;
    }

    public String toString() {
        return estado;
    }


}
