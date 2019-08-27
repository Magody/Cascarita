package com.game.magody.hilospersistenciasonido;

import android.app.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

public class Gestion extends Activity {

    private  Partida partida;
    private int dificultad;
    private int fps;
    private Handler temporizador;
    private int botes;

    MediaPlayer golpe,game_over;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // comunicacion entre actividades
        Bundle extras = getIntent().getExtras();

        dificultad = extras.getInt("DIFICULTAD");

        partida = new Partida(this,dificultad);

        fps = 30;
        botes = 0;
        setContentView(partida);

        temporizador = new Handler();
        temporizador.postDelayed(hilo,2000);

        golpe = MediaPlayer.create(this, R.raw.hit_ball);
        game_over = MediaPlayer.create(this,R.raw.game_over);

    }

    private Runnable hilo = new Runnable(){

        @Override
        public void run() {

            if(!partida.movimientoBola()){
                //no si se acaba el juego
                partida.invalidate(); // elimina el contenido de ImageView y llama de nuevo a onDraw
                //pausa de borrar y pintar
                temporizador.postDelayed(hilo, 1000/fps);
            }
            else{
                fin();
            }



        }
    };

    public boolean onTouchEvent(MotionEvent event){
        //evento que reacciona cuando se topa la pantalla
        int x = (int)event.getX(), y = (int)event.getY();

        if(partida.toque(x, y))
        {
            botes++;
            golpe.start();
        }


        return false;
    }

    private void fin(){
        temporizador.removeCallbacks(hilo); // limpia el buffer que pudo quedar

        Intent intent = new Intent();
        intent.putExtra("score",this.botes*dificultad);
        setResult(RESULT_OK,intent);


        game_over.start();

        finish(); // destruye la actividad actual
    }




}
