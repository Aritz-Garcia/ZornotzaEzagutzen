package com.e2t3.zornotzaezagutzen.wordsearch;

public class Palabra {
    private String palabra;
    private int fila;
    private int columna;
    private boolean derechaIzquierdaOArribaAbajo;
    private boolean horizontal;

    public Palabra(String palabra, int fila, int columna, boolean derechaIzquierdaOArribaAbajo, boolean horizontal) {
        this.palabra = palabra;
        this.fila = fila;
        this.columna = columna;
        this.derechaIzquierdaOArribaAbajo = derechaIzquierdaOArribaAbajo;
        this.horizontal = horizontal;
    }

    public boolean getDerechaIzquierdaOArribaAbajo(){
        return derechaIzquierdaOArribaAbajo;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getDesdeFila() {
        return fila;
    }

    public int getDesdeColumna() {
        return columna;
    }

    public int getAColumna() {
        return horizontal ? aColumnaHorizontal() : acolumnaVertical();
    }

    public int getAFila() {
        return horizontal ? aFilaHorizontal() : aFilaVertical();
    }

    private int aFilaVertical() {
        return derechaIzquierdaOArribaAbajo ? (fila + palabra.length()) -1 : (fila - palabra.length()) + 1;
    }

    private int aFilaHorizontal() {
        return fila;
    }

    private int acolumnaVertical() {
        return columna;
    }

    private int aColumnaHorizontal() {
        return derechaIzquierdaOArribaAbajo ? (columna + palabra.length()) -1 : (columna - palabra.length()) +1;
    }

}
