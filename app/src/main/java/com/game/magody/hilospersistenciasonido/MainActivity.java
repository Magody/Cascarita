package com.game.magody.hilospersistenciasonido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        record = 0;
    }


    public void ayuda(View view){

        Intent intent = new Intent(this,HelpActivity.class);
        startActivity(intent);

    }

    public void dificultad(View view){

        // obtiene el texto del boton pulsado
        String dific = (String) ((Button) view).getText();
        int dif = 1;

        if(dific.compareToIgnoreCase(getString(R.string.medio)) == 0){
            dif = 2;
        }
        else if(dific.compareToIgnoreCase(getString(R.string.dificil)) == 0){
            dif = 3;
        }

        Intent intent = new Intent(this,Gestion.class);
        intent.putExtra("DIFICULTAD",dif);

        startActivityForResult(intent,1);

    }

    protected  void onActivityResult(int peticion,int codigo,Intent score){

        if(peticion==1 || codigo==RESULT_OK){
            int resultado = score.getIntExtra("score",0);
            Toast toast = Toast.makeText(this,"El score es: " + resultado,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            if(resultado > record){

                record = resultado;
                guardarRecord();


            }
        }


    }

    public void onResume(){
        super.onResume();
        leeRecord();

    }

    public void guardarRecord(){
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = datos.edit();
        editor.putInt("record",record);
        editor.apply();
    }

    public void leeRecord(){
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        record = datos.getInt("record",0);

        TextView result = (TextView) findViewById(R.id.score);
        result.setText("Record: " + record);

    }


    public void onBackPressed(){

        Toast toast = Toast.makeText(this,"Buen dia",0);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        super.onBackPressed();
    }


}
