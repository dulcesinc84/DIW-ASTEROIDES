package com.example.janneth.aplicacio1;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
/*El juego que estamos desarrollando va a desplazar muchos tipos de gráficos por pantalla:asteroides,
nave, misiles, Dado que el comportamiento de todos estos elementos es muy similar, con el fin de reutilizar
el código y mejorar su comprensión, vamos a crear una clase que represente un gráfico a desplazar por pantalla.
A esta nueva clase le llamaremos Grafico y presentará las siguientes características. El elemento a dibujar
será representado en un objeto Drawable. Como hemos visto, esta clase presenta una gran versatilidad,
lo que nos permitirá trabajar con gráficos en bitmap (BitmapDrawable), vectoriales (ShapeDrawable),
gráficos con diferentes representaciones (StateListDrawable), gráficos animados (AnimationDrawable),…
Además un Grafico dispondrá de posición, velocidad de desplazamiento, ángulo de rotación y velocidad de
rotación. Para finalizar, un gráfico que salga por uno de los extremos de la pantalla reaparecerá por el
extremo contrario, tal y como ocurría en el juego original de Asteroides. */
public class Grafic {

    private Drawable drawable; // Imatge que dibuixarem
    private int cenX, cenY; // Posició del centre del gráfic
    private int amplada, altura; // Dimensions de la imatge
    private double incX, incY; // Velocitat desplaçament
    private double angle, rotacio; // Angle i velocitat de rotacio
    private int radiColisio; // Per determinar colisio
    private int xAnterior, yAnterior; // Posició anterior
    private int radiInval; // Radi emprat en invalidate()
    private View view; // On dibuixem el gráfic ( utilitzat en view.invalidate )

    public Grafic(View view, Drawable drawable) {
        this.view=view;
        this.drawable=drawable;
        amplada=drawable.getIntrinsicWidth();
        altura=drawable.getIntrinsicHeight();
        radiColisio=(altura+amplada)/4;
        radiInval=(int)Math.hypot(amplada/2,altura/2);
    }

    /**se encarga de dibujar el drawable del grafico en un canvas, empieza indicando los limitites
     * donde se situara el drawable con setBounds y lo guarda
     * @param canvas
     */
    public void dibuixaGrafic(Canvas canvas) {
        int x=cenX-amplada/2;
        int y=cenY-altura/2;
        drawable.setBounds(x, y, x+amplada, y+altura);
        canvas.save();
        canvas.rotate((float)angle, cenX, cenY);
        drawable.draw(canvas);
        canvas.restore();
        view.invalidate(cenX-radiInval,cenY-radiInval,cenX+radiInval,cenY+radiInval);
        view.invalidate(xAnterior-radiInval,yAnterior-radiInval,xAnterior+radiInval,yAnterior+radiInval);
        xAnterior=cenX;
        yAnterior=cenY;
    }
    public void incrementaPos(double factor) {
        cenX+=incX*factor;
        cenY+=incY*factor;
        angle+=rotacio*factor; // Actualizam angle
        // Si sortim de la pantalla corregim posició
        if(cenX<0){ cenX=view.getWidth();}
        if(cenX>view.getWidth())
        { cenX=0;}
        if(cenY<0)
        {  cenY=view.getHeight();}
        if(cenY>view.getHeight()){
            cenY=0;}
    }

    public double distancia(Grafic g) {
        return Math.hypot(cenX-g.cenX, cenY-g.cenY);
    }

    public boolean verificaColisio(Grafic g) {
        return (distancia(g) < (radiColisio+g.radiColisio));
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public int getCenX() {
        return cenX;
    }

    public void setCenX(int cenX) {
        this.cenX = cenX;
    }

    public int getCenY() {
        return cenY;
    }

    public void setCenY(int cenY) {
        this.cenY = cenY;
    }

    public int getAmplada() {
        return amplada;
    }

    public void setAmplada(int amplada) {
        this.amplada = amplada;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public double getIncX() {
        return incX;
    }

    public void setIncX(double incX) {
        this.incX = incX;
    }

    public double getIncY() {
        return incY;
    }

    public void setIncY(double incY) {
        this.incY = incY;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRotacio() {
        return rotacio;
    }

    public void setRotacio(double rotacio) {
        this.rotacio = rotacio;
    }

    public int getRadiColisio() {
        return radiColisio;
    }

    public void setRadiColisio(int radiColisio) {
        this.radiColisio = radiColisio;
    }

    public int getxAnterior() {
        return xAnterior;
    }

    public void setxAnterior(int xAnterior) {
        this.xAnterior = xAnterior;
    }

    public int getyAnterior() {
        return yAnterior;
    }

    public void setyAnterior(int yAnterior) {
        this.yAnterior = yAnterior;
    }

    public int getRadiInval() {
        return radiInval;
    }

    public void setRadiInval(int radiInval) {
        this.radiInval = radiInval;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
