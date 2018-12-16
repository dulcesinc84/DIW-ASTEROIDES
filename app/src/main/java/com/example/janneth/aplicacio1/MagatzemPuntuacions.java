package com.example.janneth.aplicacio1;

import java.util.Vector;


/*
* La interfaz es una clase abstracta pura, es decir una clase donde se indican los métodos pero no se implementa ninguno
* (en este caso se dice que los métodos son abstractos). Permite al programador de la clase establecer la
* estructura de esta (nombres de métodos, sus parámetros y tipos que retorna, pero no el código de cada método).
* Una interfaz también puede contener constantes, es decir campos de tipo staticy final.
Las diferentes clases que definamos para almacenar puntuaciones han de implementar esta interfaz.
Como ves tiene dos métodos.


5. Veamos a continuación una clase que utiliza esta interfaz. Para ello crea en el proyecto la clase AlmacenPuntuacionesArray.*/
public interface MagatzemPuntuacions {


   /**Este método permite guardar la puntuación de una partida, con los parámetros
     * @param punts puntuacion obtenida
     * @param nom nombre del jugador
     * @param data fecha de la partida*/
    public void guardarPuntuacio(int punts, String nom, long data);

    /**Este método permite obtener una lista de puntuaciones previamente almacenadas.
     * @param quantitat cantidad indica el numero maximo de puntuaciones que ha de devolver
     * @return lista de puntuaciones previamente almacenadas.*/
    public Vector<String> llistaPuntuacions(int quantitat);
}
