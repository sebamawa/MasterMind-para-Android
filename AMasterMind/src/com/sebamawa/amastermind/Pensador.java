package com.sebamawa.amastermind;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Pensador extends Activity{
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pensador);
        
        //Prueba de creación de Views dinámicamente (sin inflar layouts)
        TextView tv = new TextView(this);
        setContentView(tv);
        tv.setText("Hello Seba");
        
        TextView tv2 = new TextView(this);
        setContentView(tv2);
        tv2.setText("Hello Seba 2");
        
//        EditText et = new EditText(this);
//        et.setHeight(5);
//        setContentView(et);
        
        
    }
}
