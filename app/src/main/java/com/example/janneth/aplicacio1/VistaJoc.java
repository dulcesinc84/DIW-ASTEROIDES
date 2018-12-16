package com.example.janneth.aplicacio1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.Vector;

/*responsable de la ejecución el juego
* Como ves se han declarado tres métodos. En el constructor creamos los asteroides e
* inicializamos su velocidad, ángulo y rotación. Sin embargo, resulta imposible iniciar su posición,
* dado que no conocemos el alto y ancho de la pantalla. Esta información será conocida cuando se llame
* a onSizeChanged(). Observa cómo en esta función los asteroides están situados de forma aleatoria.
* El último método, onDraw(), es el más importante de la clase View, dado que es el responsable de dibujar la vista.

2. Hemos creado una vista personalizada. No tendría demasiado sentido, pero podrá ser utilizada en cualquier
otra aplicación que desarrolles. Visualiza el Layout juego.xml y observa como la nueva vista se integra
perfectamente en el entorno de desarrollo.**/

public class VistaJoc extends View implements SensorEventListener {

    /**********Variables MISSIL*****************/
    private Grafic missil;
    private static int PAS_VELOCITAT_MISSIL = 12;
    private boolean missilActiu=false;
    private int tempsMissil;
    private Drawable drawableMissil;

    //Manejador de sensores
    private SensorManager mSensorManager;
    List<Sensor> llistaSensors;

    /**********Variables per la NAU*****************/
    private Grafic nau; // Gràfic de la nau
    private int girNau; // Increment de direció
    private double acceleracioNau; // Augment de velocitat
    private static final int MAX_VELOCITAT_NAU=20;
    // increment estandar de gir i accelaració
    private static final int PAS_GIR_NAU=5;
    private static final float PAS_ACCELERACIO_NAU=0.5f;


    private Vector<Grafic> asteroides;// vector con los asteroides
    private int numAsteroides = 5; //numero inicial de asteroides
    private int numFragments = 3;  //Fragments en que es dividen

    // FILS I TEMPS
    // Fil encarregat de processar el joc
    private ThreadJoc fil=new ThreadJoc();
    // Cada quan volem processar canvis (ms)
    private static int PERIODE_PROCES=50;
    // Quan es va realitzar el darrer procés
    private long darrerProces=0;

    public VistaJoc(Context context, AttributeSet attrs) {
        super(context, attrs);

        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getContext());

        // Registre el sensor d'orientació i indica gestió d'events.
        //En el constructor registra el sensor e indica que nuestro objeto recogerá la
        // llamada callback:
        SensorManager mSensorManager=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        /*int sensorType=0;
        if(pref.getString(getResources().getString(R.string.pa4_key), "1").equals("0"))
            sensorType=Sensor.TYPE_ACCELEROMETER;
        if(pref.getString(getResources().getString(R.string.pa4_key), "1").equals("1"))
            sensorType=Sensor.TYPE_ORIENTATION;*/
        //List<Sensor> llistaSensors= mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        int sensorType =0;

        if (pref.getString(getResources().getString(R.string.pa4_key),"1").equals("0"))
            sensorType=Sensor.TYPE_ACCELEROMETER;

        if (pref.getString(getResources().getString(R.string.pa4_key),"1").equals("1"))
            sensorType=Sensor.TYPE_ORIENTATION;
        llistaSensors= mSensorManager.getSensorList(sensorType);
        if(!llistaSensors.isEmpty()){
            Sensor orientacioSensor=llistaSensors.get(0);
            mSensorManager.registerListener(this, orientacioSensor, SensorManager.SENSOR_DELAY_GAME);
        }



        //declara y obtiene las imagenes
        Drawable drawableNau, drawableAsteroide, drawableMissil;
       // drawableAsteroide = context.getResources().getDrawable(R.drawable.asteroide1);
        drawableNau = context.getResources().getDrawable(R.drawable.nau);

        if(pref.getString("graficos","1").equals("0")){

            /**Activar l'acceleració per software*****/
            setLayerType(View.LAYER_TYPE_SOFTWARE,null);

            /*******************  Activa los misiles   *************/
            ShapeDrawable dMissil=new ShapeDrawable(new RectShape());
            dMissil.getPaint().setColor(Color.WHITE);
            dMissil.getPaint().setStyle(Paint.Style.STROKE);
            dMissil.setIntrinsicWidth(15);
            dMissil.setIntrinsicHeight(3);
            drawableMissil=dMissil;


            Path pathAsteroide = new Path();
            pathAsteroide.moveTo((float)0.3,(float)0.0);
            pathAsteroide.lineTo((float)0.6,(float)0.0);
            pathAsteroide.lineTo((float)0.6,(float)0.3);
            pathAsteroide.lineTo((float)0.8,(float)0.2);
            pathAsteroide.lineTo((float)1.0,(float)0.4);
            pathAsteroide.lineTo((float)0.8,(float)0.6);
            pathAsteroide.lineTo((float)0.9,(float)0.9);
            pathAsteroide.lineTo((float)0.8,(float)1.0);
            pathAsteroide.lineTo((float)0.4,(float)1.0);
            pathAsteroide.lineTo((float)0.0,(float)0.6);
            pathAsteroide.lineTo((float)0.0,(float)0.2);
            pathAsteroide.lineTo((float)0.3,(float)0.0);

            ShapeDrawable dAsteroide = new ShapeDrawable(new PathShape(pathAsteroide,1,1));
            dAsteroide.getPaint().setColor(Color.WHITE);
            dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
            dAsteroide.setIntrinsicWidth(50);
            dAsteroide.setIntrinsicHeight(50);
            drawableAsteroide=dAsteroide;
            setBackgroundColor(Color.BLACK);


            //nave en vectorial

            Path pathNau= new Path();
            pathNau.moveTo((float)0.0, (float)0.0);
            pathNau.lineTo((float)1.0, (float)0.5);
            pathNau.lineTo((float)0.0, (float)1.0);
            pathNau.lineTo((float)0.0, (float)0.0);
            ShapeDrawable dNau= new ShapeDrawable(new PathShape(pathNau,1,1));
            dNau.getPaint().setColor(Color.WHITE);
            dNau.getPaint().setStyle(Paint.Style.STROKE);
            dNau.setIntrinsicWidth(40);
            dNau.setIntrinsicHeight(35);
            drawableNau=dNau;

        }else{
            drawableAsteroide= context.getResources().getDrawable(R.drawable.asteroide1);
            drawableMissil=context.getResources().getDrawable(R.drawable.missil1);
        }




        //inicializa los asteroides
        asteroides = new Vector<Grafic>();
        //inicializa la variable nave
        nau=new Grafic(this, drawableNau);
        //inicializa el misil
        missil=new Grafic(this, drawableMissil);

       for (int i = 0; i < numAsteroides; i++) {
            Grafic asteroide = new Grafic(this, drawableAsteroide);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngle((int) (Math.random() * 360));
            asteroide.setRotacio((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }
    }

    @Override
    protected void onSizeChanged(int ample, int alt, int ampla_ant, int alt_ant) {
        super.onSizeChanged(ample, alt, ampla_ant, alt_ant);

        //posicionar la nave en el centro de la vista
        nau.setCenY(alt/2);
        nau.setCenX(ample/2);
        // una vegada que coneixem la nostra amplada i altura posiciona els asteroides
        for (Grafic asteroide : asteroides) {
            do {
                asteroide.setCenX((int) (Math.random() * ample));
                asteroide.setCenY((int) (Math.random() * alt));
            } while (asteroide.distancia(nau) < (ample + alt) / 5);
        }
        darrerProces= System.currentTimeMillis();
        fil.start();

    }

    synchronized protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /***********Dibujar el misil si esta activado*******/
        if(missilActiu){
            missil.dibuixaGrafic(canvas);
        }


        for (Grafic asteroide : asteroides) {
            asteroide.dibuixaGrafic(canvas);
        }

        //dibujar la nave en el canvas
        nau.dibuixaGrafic(canvas);
    }

    /********* ACTUALITZA ELS VALORS DELS ELEMENTS ÉS A DIR, GESTIONA ELS MOVIMENTS********/
    synchronized protected void actualitzaFisica() {
        // Hora actual en milisegons
        long ara = System.currentTimeMillis();
        // No fer res si el periode de proces NO s'ha complert
        if (darrerProces + PERIODE_PROCES > ara) {
            return;
        }
        // Per una execució en temps real calculem retard
        double retard = (ara - darrerProces) / PERIODE_PROCES;
        darrerProces = ara; // Per la propera vegada
        // Actualitzame velocitat i direcció de la nau a partir de
        // girNau i acceleracioNau segons l'entrada del jugador
        nau.setAngle((int) (nau.getAngle() + girNau * retard));
        double nIncX = nau.getIncX() + acceleracioNau * Math.cos(Math.toRadians(nau.getAngle())) * retard;
        double nIncY = nau.getIncY() + acceleracioNau + Math.sin(Math.toRadians(nau.getAngle())) * retard;

        // Actualitzem si el mòdul de la velocitat no passa el màxim
        if (Math.hypot(nIncX, nIncY) <= MAX_VELOCITAT_NAU) {
            nau.setIncX(nIncX);
            nau.setIncY(nIncY);
        }
        // Actualitzem les posicions X i Y
        nau.incrementaPos(retard);
        for (int i = 0; i < asteroides.size(); i++) {
            asteroides.get(i).incrementaPos(retard);
        }

        // Actualizem posicio del missil
        if(missilActiu){
            missil.incrementaPos(retard);
            tempsMissil-=retard;
            if(tempsMissil<0){
                missilActiu=false;
            }else{
                for(int i=0; i<asteroides.size(); i++){
                    if(missil.verificaColisio(asteroides.get(i))){
                        destrueixAsteroide(i);
                        break;
                    }
                }
            }
        }
    }//actualiza fisica



    private boolean hihaValorInicial=false;
    private float valorInicial;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float valor=event.values[1]; // eix Y
        if(!hihaValorInicial){
            valorInicial=valor;
            hihaValorInicial=true;
        }
        girNau=(int)(valor-valorInicial)/3;
    }

    /*metodos de gestion de los sensores*/
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    // Manejador d'events de la pantalla tactil per la nau
    private float mX=0, mY=0;
    private boolean dispar=false;

    // GESTIO D'EVENTS DE LA NAU AMB PANTALLA TACTIL
    @Override
    public boolean onTouchEvent(MotionEvent mevent) {
        super.onTouchEvent(mevent);
        float x = mevent.getX();
        float y = mevent.getY();
        switch(mevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dispar = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dy < 6 && dx > 6) {
                    girNau = Math.round((x - mX) / 2);
                    dispar = false;
                } else if (dx < 6 && dy > 6) {
                    acceleracioNau = Math.round((mX - y) / 25);
                    dispar = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                girNau = 0;
                acceleracioNau = 0;
                if (dispar) {

                    ActivaMissil();
                }
                break;
        }
        mX=x; mY=y;
        return true;
    }

    /******************Metodos auxiliares**************************/
    private void destrueixAsteroide(int i) {
        asteroides.remove(i);
        missilActiu = false;
    }
    private void ActivaMissil() {
        missil.setCenX(nau.getCenX());
        missil.setCenY(nau.getCenY());
        missil.setAngle(nau.getAngle());
        missil.setIncX(Math.cos(Math.toRadians(missil.getAngle()))*
                PAS_VELOCITAT_MISSIL);
        tempsMissil= (int)Math.min(
                this.getWidth()/Math.abs(missil.getIncX()),
                this.getHeight()/Math.abs(missil.getIncY()))-2;
        missilActiu=true;

    }


    class ThreadJoc extends Thread {
        private boolean pausa,corrent;

        public synchronized void pausar(){
            pausa=true;
        }
        public synchronized void reanudar(){
            pausa=false;
            notify();
        }
        public synchronized void aturar(){
            corrent=false;
            if (pausa) reanudar();
        }

        public void run() {
            corrent =true;
            while (corrent){
                actualitzaFisica();
                synchronized (this){
                    while (pausa){
                        try {
                            wait();
                        }catch (Exception e){

                        }
                    }
                }
            }
        }
    }
    public ThreadJoc getFil(){
        return fil;
    }

}