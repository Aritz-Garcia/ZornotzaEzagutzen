package com.e2t3.zornotzaezagutzen.wordsearch;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sopa {
    private String letras = "ABDEFGHIJKLMNOPRSTUXZ";
    private int filas = 10;
    private int columnas = 10;
    private char[][] matriz;
    private boolean[][] criba;
    private int posX;
    private int posY;
    private boolean d;
    private boolean s;
    Random rnd;
    String palabra = "";
    List<Palabra> palabras = new ArrayList<>();

    public Sopa() {
        matriz = new char[filas][columnas];
        criba = new boolean[filas][columnas];
        rnd = new Random();
        posX = 0;
        posY = 0;
        d = true;
        s = true;
    }

    /**
     * Método que recorre la matriz de 15 por 15 y asigna un caracter aleatorio a cada posici�n
     */
    public void llenarSopa() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j] = letras.charAt(rnd.nextInt(letras.length()));
                criba[i][j] = true;
            }
        }
    }

    /**
     * Método que imprime la matriz con su contenido
     */
    public char[][] getMatriz() {
        return matriz;
    }

    /**
     * Método que valida si la posición aleatoria generada, su sentido y su dirección al colocar la palabra sobreescribe a una palabra ya colocada
     *
     * @return
     */
    public boolean validarCrilla() {
        boolean a = true;
        if (s) {
            //int indicePalabra=0;
            if (d) {
                for (int i = 0; i < palabra.length(); i++) {
                    if (criba[posX][posY + i] || matriz[posX][posY + i] == palabra.charAt(i))
                        a = true;
                    else {
                        a = false;
                        break;
                    }
                    //indicePalabra++;
                }
            } else {
                for (int i = 0; i < palabra.length(); i++) {
                    if (criba[posX][(columnas - 1) - posY - i] || matriz[posX][(columnas - 1) - posY - i] == palabra.charAt(i))
                        a = true;
                    else {
                        a = false;
                        break;
                    }
                    //indicePalabra++;
                }
            }
        } else {
            //int indicePalabra=0;
            if (d) {
                for (int i = 0; i < palabra.length(); i++) {
                    if (criba[posX + i][posY] || matriz[posX + i][posY] == palabra.charAt(i))
                        a = true;
                    else {
                        a = false;
                        break;
                    }
                    //indicePalabra++;
                }
            } else {
                for (int i = 0; i < palabra.length(); i++) {
                    if (criba[(filas - 1) - posX - i][posY] || matriz[(filas - 1) - posX - i][posY] == palabra.charAt(i))
                        a = true;
                    else {
                        a = false;
                        break;
                    }
                    //indicePalabra++;
                }
            }
        }

        return a;
    }

    /**
     * M�todo que inserta una palabra de forma horizontal
     */
    public void insertarPalabraHorizontal() {
        boolean ok = false;
        int indicePalabra = 0;
        if (d) {
            if (validarCrilla()) {
                for (int i = 0; i < palabra.length(); i++) {
                    matriz[posX][i + posY] = palabra.charAt(indicePalabra);
                    criba[posX][i + posY] = false;
                    indicePalabra++;
                }
                palabras.add(new Palabra(palabra, posX, posY, true, true));
            } else {
                generarPosicionAleatoria();
                if (s) insertarPalabraHorizontal();
                else insertarPalabraVertical();
            }
        } else {
            if (validarCrilla()) {
                for (int i = 0; i < palabra.length(); i++) {
                    matriz[posX][(columnas - 1) - posY - i] = palabra.charAt(indicePalabra);
                    criba[posX][(columnas - 1) - posY - i] = false;
                    indicePalabra++;
                }
                palabras.add(new Palabra(palabra, posX, (columnas - 1) - posY, false, true));
            } else {
                generarPosicionAleatoria();
                if (s) insertarPalabraHorizontal();
                else insertarPalabraVertical();
            }
        }
    }

    /**
     * M�todo que inserta una palabra vertical
     */
    public void insertarPalabraVertical() {
        int indicePalabra = 0;
        if (d) {
            if (validarCrilla()) {
                for (int i = 0; i < palabra.length(); i++) {
                    matriz[posX + i][posY] = palabra.charAt(indicePalabra);
                    criba[posX + i][posY] = false;
                    indicePalabra++;
                }
                palabras.add(new Palabra(palabra, posX, posY, true, false));
            } else {
                generarPosicionAleatoria();
                if (s) insertarPalabraHorizontal();
                else insertarPalabraVertical();
            }
        } else {
            if (validarCrilla()) {
                for (int i = 0; i < palabra.length(); i++) {
                    matriz[(filas - 1) - posX - i][posY] = palabra.charAt(indicePalabra);
                    criba[(filas - 1) - posX - i][posY] = false;
                    indicePalabra++;
                }
                palabras.add(new Palabra(palabra, (filas - 1) - posX, posY, false, false));
            } else {
                generarPosicionAleatoria();
                if (s) insertarPalabraHorizontal();
                else insertarPalabraVertical();
            }
        }
    }

    /**
     * M�todo que crea un objeto posición desde la clase PosicionAleatoria
     */
    public void generarPosicionAleatoria() {
        PosicionAleatoria pos = new PosicionAleatoria(palabra);
        pos.generarPosicionAleatoria(filas, columnas);
        posX = pos.getI();
        posY = pos.getJ();
        d = pos.getD();
        s = pos.getS();
    }

    public static Sopa getSopa(List<String> sc) {
        Sopa sp = new Sopa();
        sp.llenarSopa();
        for (String hitza : sc) {
            sp.palabra = hitza;
            sp.generarPosicionAleatoria();
            if (sp.s) sp.insertarPalabraHorizontal();
            else sp.insertarPalabraVertical();
        }
        return sp;
    }

    public List<Palabra> getPalabras() {
        return palabras;
    }
}