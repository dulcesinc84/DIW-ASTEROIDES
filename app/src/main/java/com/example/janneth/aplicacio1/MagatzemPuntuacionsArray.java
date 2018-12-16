package com.example.janneth.aplicacio1;

import java.util.Vector;

/*Esta clase almacena la lista de puntuaciones en un vector de String.
Tiene el inconveniente de que al tratarse de una variable local, cada vez que se cierre la aplicación
se perderán las puntuaciones. El constructor inicializa el array e introduce tres valores.
La idea es que aunque todavía no esté programado el juego y no podamos jugar, tengamos ya algunas puntuaciones
para poder representar una lista. El método guardarPuntuacion() se limita a insertar en la primera posición del array
un String con los puntos y el nombre. La fecha no es almacenada. El método listaPuntuaciones()
devuelve la lista de String entero, sin tener en cuenta el parámetro cantidad que debería limitar el número
de Strings devueltos. */


public class MagatzemPuntuacionsArray implements MagatzemPuntuacions {
    private Vector<String> puntuacions;

    public MagatzemPuntuacionsArray() {
        puntuacions = new Vector<String>();
        puntuacions.add("123000 Pepito Dominquez");
        puntuacions.add("111000 Pedro Martinez");
        puntuacions.add("011000 Paco Perez");

      /*  puntuacions.add("001000 Fabián Toro");
        puntuacions.add("002000 Sara Toro");
        puntuacions.add("003000 Silvia T.");
        puntuacions.add("004000 Cristina T.");
        puntuacions.add("111000 Rosa T.");
        puntuacions.add("011000 Arturo T.");
        puntuacions.add("123000 Katerine A.");
        puntuacions.add("111000 Geovanny T.");
        puntuacions.add("011000 Paco Perez");
        puntuacions.add("123000 Pepito Dominquez");
        puntuacions.add("111000 Pedro Martinez");
        puntuacions.add("011000 Paco Perez");
        puntuacions.add("123000 Pepito Dominquez");
        puntuacions.add("111000 Pedro Martinez");
        puntuacions.add("011000 Paco Perez");
        puntuacions.add("123000 Pepito Dominquez");
        puntuacions.add("111000 Pedro Martinez");
        puntuacions.add("011000 Paco Perez");*/

    }

    /**
     * @param punts puntuacion obtenida
     * @param nom   nombre del jugador
     * @param data  fecha de la partida
     */
    @Override
    public void guardarPuntuacio(int punts, String nom, long data) {
        puntuacions.add(punts + " " + nom + " " + data);
    }


    /**
     * @param quantitat cantidad indica el numero maximo de puntuaciones que ha de devolver
     * @return
     */
    @Override
    public Vector<String> llistaPuntuacions(int quantitat) {
        return puntuacions;
    }
}