package com.example.janneth.aplicacio1;

import android.app.Activity;
import android.os.Bundle;

/*Es una actividad que muestra el fragment de PreferenciesFragment*/
public class Preferencies extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Desde una actividad podemos visualizar un fragment en tiempo de ejecución.
        Para ello utilizamos el manejador de fragments de la activdad (getFragmentManager())
        y comenzamos una transacción (beginTransaction()). Una transacción es una operación de insertado,
        borrado o reemplazo de fragments. En el ejemplo vamos a reemplazar el contenido de la actividad por un
        nuevo fragment de la clase PreferenciasFragment. Finalmente se llama a commit()
        para que se ejecute la transacció
         */

        /*getFragmentManager es un manejador de fragmentos, y comienza una transaccion
        * Transaccion es la operacion de insertar, borrar o reemplazar un fragmento
        * commit ejecuta la operacion*/
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferenciesFragment()).commit();

    }

}
