package com.game.magody.hilospersistenciasonido;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.WindowManager;
import android.support.v7.widget.AppCompatImageView;


public class Partida extends AppCompatImageView {

    private int acel;
    private Bitmap pelota, fondo;
    private int tam_pantX, tam_pantY, posX, posY, velX, velY;
    private int tamPelota;
    boolean pelota_sube;

    public Partida(Context contexto, int nivel_dificultad){
        // INFO en la API de Android
        super(contexto);

        // rescata datos del display del dispositivo
        WindowManager manejador_ventana=(WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE);
        Display pantalla=manejador_ventana.getDefaultDisplay();

        // integra dos coordenadas x,y
        Point maneja_coord=new Point();

        pantalla.getSize(maneja_coord); // determina el tamaño de la pantalla: horizontal y vertical

        tam_pantX=maneja_coord.x; // obtiene el horizontal tamaño

        tam_pantY=maneja_coord.y; // obtiene el vertical tamaño


        // Construye un Layout programatico, interfaz mediante codigo
        // contextCompat permite acceder a caracteristicas del contexto
        BitmapDrawable dibujo_fondo = (BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.paisaje_1);

        fondo=dibujo_fondo.getBitmap();// mirar en api getBitmap en clase BitmapDrawable. esto nos lleva a la siguiente instr.

        // aplica un escalado al fondo
        fondo=Bitmap.createScaledBitmap(fondo, tam_pantX, tam_pantY, false);//mirar en clase Bitmap

        // lo mismo para la pelota
        BitmapDrawable objetoPelota=(BitmapDrawable)ContextCompat.getDrawable(contexto, R.drawable.pelota_2);

        pelota=objetoPelota.getBitmap();

        // determina la dificultad
        tamPelota=tam_pantY/3;

        pelota=Bitmap.createScaledBitmap(pelota, tamPelota, tamPelota, false);

        // en el centro horizontal
        posX=tam_pantX/2-tamPelota/2;
        // mas arriba de la pantalla
        posY=0-tamPelota;


        // aceleración dada por la dificultad
        acel=nivel_dificultad*(maneja_coord.y/400);


    }

    public boolean toque(int x, int y){
        // cuando se toca la pantalla, recibe el lugar dodne toco con el dedo

        //invalida el toque en la primera tercera parte del eje y
        if(y<tam_pantY/3)
            return false;

        //si la velocidad en y es menor o igual a 0, no debe funcionar el metodo
        if(velY<=0)
            return false;

        // si no acertamos al tocar la pelota, no hace nada
        if(x<posX || x> posX+tamPelota)
            return false;

        if(y<posY || y>posY+tamPelota)
            return false;

        // invierte la velocidaden y
        velY=-velY;

        // impulso en X
        double desplX=x-(posX+tamPelota/2);

        desplX=desplX/(tamPelota/2)*velY/2;

        velX+=(int)desplX;

        return true;
    }


    public boolean movimientoBola(){
        // devuelve true cuando el juego ha terminado

        if(posX<0-tamPelota){
            // se va a la izquierda y se repone arriba
            posY=0-tamPelota;

            velY=acel;
        }

        posX+=velX;

        posY+=velY;

        // cuando topa el suelo
        if(posY>=tam_pantY)
            return true;

        //se fue a la izquierda o derecha pierde
        if(posX+tamPelota<0 || posX>tam_pantX)
            return true;

        if(velY<0)
            pelota_sube=true;

        if(velY>0 && pelota_sube){

            pelota_sube=false;

        }

        velY+=acel;

        return false;
    }

    protected void onDraw(Canvas lienzo){

        lienzo.drawBitmap(fondo, 0,0, null);

        lienzo.drawBitmap(pelota, posX, posY, null);


    }
}

