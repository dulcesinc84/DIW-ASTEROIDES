package com.example.janneth.aplicacio1;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

/*La clase PreferenceFragment permite crear un fragment que contiene una ventana
con las opciones de preferencias definidas en un recurso XML. Un fragment es un elemento que
puede ser incrustado dentro de una actividad*/
public class PreferenciesFragment extends PreferenceFragment {


    /*crea un fragmento que ha de contener las preferencias */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencies);

       // final EditTextPreference fragmentos = (EditTextPreference)findPreference(getResources().getString(R.string.p3_key));


        /*En muchas ocasiones vas a querer limitar los valores que un usuario puede introducir en las preferencias.
        Por ejemplo, podría ser interesante que el valor introducido por el usuario en la preferencia número de fragmentos
        solo pudiera tomar valores entre 0 y 9. Para conseguir esto podemos utilizar el escuchador de evento
        onPreferenceChangeListener que podremos asignar a una preferencia.*/
        final EditTextPreference fragmentos = (EditTextPreference)findPreference(getResources().getString(R.string.p3_key));
        fragmentos.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        int valor;
                        try{
                            valor=Integer.parseInt((String)newValue);
                        } catch(Exception e) {
                            Toast.makeText(getActivity(), "Ha de ser un número", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        if(valor>=0 && valor<=9){
                            fragmentos.setSummary("En cuantos trozos se divide un asteroide("+valor+")");
                            return true;
                        } else {
                            Toast.makeText(getActivity(), "Maximo de fragmentos 9", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }
        );
    }
}

/*Se obtiene una referencia de la preferencia fragmentos, para asignarle un escuchador
que será llamado cuando cambie su valor. El escuchador comienza convirtiendo el valor introducido a entero.
En caso de producirse un error es por que el usuario no ha introducido un valor adecuado.
En este caso, mostramos un mensaje y devolvemos false para que el valor de la preferencia no sea modificado.
Si no hay error, tras verificar el rango de valores aceptables, modificamos la explicación de la preferencia
 para que aparezca el nuevo valor entre paréntesis y devolvemos true para aceptar este valor. S
 i no está en el rango, mostramos un mensaje indicando el problema y devolvemos false.*/