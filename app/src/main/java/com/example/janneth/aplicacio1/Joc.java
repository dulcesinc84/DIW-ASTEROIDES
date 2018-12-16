package com.example.janneth.aplicacio1;

import android.app.Activity;
import android.os.Bundle;
/*actividad que controle la pantalla del juego */
public class Joc extends Activity {

    private VistaJoc vistaJoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joc);
        vistaJoc=(VistaJoc)findViewById(R.id.VistaJoc);
    }

    @Override
    protected void onPause() {
        super.onPause();
        vistaJoc.getFil().pausar();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        vistaJoc.getFil().reanudar();
    }

    @Override
    protected void onDestroy() {
        vistaJoc.getFil().aturar();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
