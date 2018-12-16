package com.example.janneth.aplicacio1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, GestureOverlayView.OnGesturePerformedListener{

    /***********Gesturas************/
    private GestureLibrary llibreria;
    private TextView sortida;


    private Button bJugar;
    private Button bConf;
    private Button bAcercaDe;
    private Button bSortir;
    private MediaPlayer mp;
    /*variable para almacenar las puntuaciones
    * El modificador static permite compartir el valor de una variable entre todos los objetos de la clase.
    * Es decir, aunque se creen varios objetos, solo existirá una única variable almacen compartida por todos los objetos.
    * El modificador public permite acceder a la variable desde fuera de la clase. Por lo tanto, no será necesario crear métodos
    * getters y setters. Para acceder a esta variable escribir el nombre de la clase seguida de un punto y el nombre de la variable.
    * Es decir MainActivity.magatzem1. */
    public static MagatzemPuntuacions magatzem = new MagatzemPuntuacionsArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp=MediaPlayer.create(this, R.raw.audio);
        mp.start();

        ///*Gesturas ******************************************/////////////
        llibreria= GestureLibraries.fromRawResource(this, R.raw.gestures);
        if(!llibreria.load()) finish();
        GestureOverlayView gesturesView= (GestureOverlayView)findViewById(R.id.gestures);
        // associa escoltador d'event de la gestura en la mateixa classe
        gesturesView.addOnGesturePerformedListener(this);



        /*Sin emplear clases internas*/
        bJugar = (Button) findViewById(R.id.button1);
        bJugar.setOnClickListener(this);

        bConf = (Button) findViewById(R.id.button2);
        bConf.setOnClickListener(this);
        bConf.setOnLongClickListener(this);

        bAcercaDe = (Button) findViewById(R.id.button3);
        bAcercaDe.setOnClickListener(this);

        bSortir = (Button) findViewById(R.id.button4);
        bSortir.setOnClickListener(this);
        bSortir.setOnLongClickListener(this);

        /*int pos=mp.getCurrentPosition();
        mp.seekTo(pos);*/

        // Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        //La actividad esta creada

    }
        /*****************Empleando clase interna Anónima***********/
        /********Botón jugar************/
        /*bJugar = (Button) findViewById(R.id.button1);
        bJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llançarJoc(null);
            }
        });

        /*************Boton configurar *******/
        /*Button bConf = (Button) findViewById(R.id.button2);
        bConf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                abrirPreferencias(null);
            }

        });
        bConf.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mostrarPreferencias(null);
                return true;
            }
        });

        /*********Boton Acerca de*************/
        /*bAcercaDe = (Button) findViewById(R.id.button3);
        bAcercaDe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                llancarAcercaDe(null);
            }
        });

        /************Boton Salir************/
        /*Button bSortir = (Button) findViewById(R.id.button4);
        //bSortir.setBackgroundResource(R.drawable.degradat);
        bSortir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        bSortir.setOnLongClickListener(new View.OnLongClickListener() {
            //            @Override
            public boolean onLongClick(View view) {
                llancarPunctuacions(null);
                return false;
            }
        });

    }

    /*Lanza la actividad preferencias cuando es pulsado el botón configurar*/
    public void abrirPreferencias(View view) {
        Intent i = new Intent(this, Preferencies.class);
        startActivity(i);

    }

    /*pref es el objeto de la clase SharedPreferences y le asigna las
    preferencias definidas para la aplicación. A continuación crea el String s y le asigna los
    valores de dos de las  preferencias. Se utilizan los métodos pref.getBoolean() y pref.getString(),
    que disponen de dos parámetros: el valor de key que queremos buscar ("musica" y "fragments")etc
    y el valor asignado por defecto en caso de no encontrar esta key.
    Finalmente se visualiza el resultado utilizando la clase Toast. Los tres parámetros indicados son
    el contexto (nuestra actividad),  el String a mostrar y el tiempo que se estará mostrando esta
    información.*/
    public void mostrarPreferencias(View view) {

        Resources res = this.getResources();
        String opGrafic[] = res.getStringArray(R.array.tipusGrafics);
        String opConexion[] = res.getStringArray(R.array.tipoConexion);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String s = "Musica: " + pref.getBoolean("musica", false) +
                "\nGraficos: " + opGrafic[Integer.parseInt(pref.getString("graficos", "?"))] +
                "\nFragmentos: " + pref.getString("fragments", "?") +
                "\nMultijugadores: " + pref.getBoolean("Multijugador", false) +
                "\nMax Jugadores: " + pref.getString("maximoJugadores", "?") +
                "\nTipo conexion: " + opConexion[Integer.parseInt(pref.getString("tipoConexion", "?"))];

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /**Metodo que se ejecuta cuando hacemos un click largo en el boton salir
     * @param view
     * lanza una nueva actividad para mostrar las puntuaciones*/
    public void llancarPunctuacions(View view) {
        Intent i = new Intent(this, Puntuacions.class);
        startActivity(i);
    }


    /* método en la actividad principal que será ejecutado cuando sea pulsado el botón Acerca de.
    * método crea un Objeto de la clase Intent se pasa como parámetros el contexto uzado
    * @param this es actividad principal
    * @param AcercaDe es la actividad enviada
    * Intent es la volutntad de realizar alguna acció, la uzaremos para lanzar una activida
    * */
    public void llancarAcercaDe(View view) {
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
        mp.pause();
    }

    public void llancarSalir(View view) {

        finish();
    }

    /**
     * @param view
     */
    public void llançarJoc(View view) {
        Intent i = new Intent(this, Joc.class);
        startActivityForResult(i, 1234);
    }

    /*Activar el menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //crea el objeto en java que represetna el menu
        MenuInflater infl = getMenuInflater();
        //asocia el menu creado en XML al objeto de java
        infl.inflate(R.menu.menu_main, menu);
        //indica que se puede visualizar (activar) el menu
        return true;
    }
    /*cada vez que se selecciona el menu se llama al siguiente metodo*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.configuracio) {
            abrirPreferencias(null);
            return true;
        }


        if (id == R.id.acercaDe) {
            llancarAcercaDe(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case  R.id.button1: llançarJoc(null);break;
            case  R.id.button2: abrirPreferencias(null);break;
            case  R.id.button3: llancarAcercaDe(null);break;
            case  R.id.button4: llancarSalir(null);break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case  R.id.button2: mostrarPreferencias(null);break;
            case  R.id.button4: llancarPunctuacions(null);break;
        }
        return false;
    }


    /*****************Gesturas***************************************/
    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions=llibreria.recognize(gesture);
        String predictionName="";
        double predictionScore=0;

        for(Prediction prediction:predictions){
            if(predictionScore<prediction.score){
                predictionScore=prediction.score;
                predictionName=prediction.name;
            }
        }

        switch(predictionName){
            case "acercade":
                startActivity(new Intent(this, AcercaDe.class));
                break;
            case "cancelar":
                System.exit(0);
                break;
            case "configurar":
                startActivity(new Intent(this, Preferencies.class));
                break;
            case "jugar":
                startActivity(new Intent(this, Joc.class));
                break;
            default:
                Toast.makeText(this, "Error...", Toast.LENGTH_SHORT).show();
        }
    }

   protected void onStart(){
        super.onStart();
        //Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show();
       //La actividad esta a punto de hacerse visible
    }
    protected void onResume(){
        super.onResume();
        mp.start();
       // Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show();
        //La actividad se ha vuelto visible
    }
    protected void onPause(){
        super.onPause();
        mp.pause();
       // Toast.makeText(this,"onPause",Toast.LENGTH_SHORT).show();
        //La actividad esta a punto de ser detenida
    }
    protected void onStop(){
        mp.pause();
        super.onStop();
      //  Toast.makeText(this,"onStop",Toast.LENGTH_SHORT).show();
        //La actividad ya no es visible esta detenida
    }
    protected void onRestart(){
        super.onRestart();
       // Toast.makeText(this,"onRestar",Toast.LENGTH_SHORT).show();
    }
    protected void onDestroy(){
      //  Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
        //  La actividad esta a punto de ser destruida
        //mp.release();
        super.onDestroy();
    }
}
