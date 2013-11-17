package com.sebamawa.amastermind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//views (componentes)
	private RadioGroup rgModoJuego;
	private RadioGroup rgDificultad;
	
	private TextView tvLargoCodigo, tvIntentos, tvRango;
	
	private Button btnJugar, btnAyuda, btnSalir;
	private ScrollView scrollViewPrincipal;
	
	//enumerado para el modo de juego
	protected static enum MODO_JUEGO {ADIVINADOR, PENSADOR};
	private MODO_JUEGO modoJuego;
	//enumerado para la dificultad
	protected static enum DIFICULTAD {PRINCIPIANTE, MODERADO, EXPERTO};	
	protected static DIFICULTAD dificultad;
	
	//entero para pasar la dificultad a la activity correspondiente
	//("Adivinador" o "Pensador")
	//0 = PRINCIPIANTE
	//1 = MODERADO
	//2 = EXPERTO
	//private int dificultadJuegoInt;
	
	protected static int LARGO_CODIGO;  //indica el largo del código según la dificultad
	                                    //(para usar en otras clases)
	protected static int MAX_INTENTOS;  //según la dificultad
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //refernciamos los controles
        referenciarControles();
        
        //asigna escuchas a los RadioGroups
        asignarEscuchasRadioGroups();
        
        //asigna escuchas a los botones
        asignarEscuchasBotones();      
        
        //por defecto el modo de juego es "ADIVINADOR"  y la dificultad "PRINCIPIANTE"
        modoJuego = MODO_JUEGO.ADIVINADOR;
        dificultad = DIFICULTAD.PRINCIPIANTE;
        LARGO_CODIGO = 4;
        MAX_INTENTOS = 10;
        
//        //dejamos el ScrollView arriba
//        scrollViewPrincipal.fullScroll(View.FOCUS_UP);
    }

    //======================================================================//
    
    private void referenciarControles(){
    	rgModoJuego = (RadioGroup) findViewById(R.id.RGModoJuego);
    	rgDificultad = (RadioGroup) findViewById(R.id.RGDificultad);
    	
    	tvLargoCodigo = (TextView) findViewById(R.id.tvLargoCodigo);
    	tvIntentos = (TextView) findViewById(R.id.tvDatosIntento);
    	tvRango = (TextView) findViewById(R.id.tvRango);
    	
    	btnJugar = (Button) findViewById(R.id.btnJugar);
    	btnAyuda = (Button) findViewById(R.id.btnAyuda);
    	btnSalir = (Button) findViewById(R.id.btnSalir);
    	scrollViewPrincipal = (ScrollView) findViewById(R.id.scrollViewPrincipal);
    }
    
    //========================================================================//
    
    private void asignarEscuchasRadioGroups(){
    	EscuchaRadioGroups escuchaDificultad_ModoJuego = new EscuchaRadioGroups();
    	rgDificultad.setOnCheckedChangeListener(escuchaDificultad_ModoJuego);
    	
//    	EscuchaRadioGroups escuchaModoJuego = new EscuchaRadioGroups();
    	rgModoJuego.setOnCheckedChangeListener(escuchaDificultad_ModoJuego);
    }
    
    //=========================================================================//
    
    private void asignarEscuchasBotones(){
    	EscuchaButtons escuchaBotones = new EscuchaButtons();
    	btnJugar.setOnClickListener(escuchaBotones);
    	btnAyuda.setOnClickListener(escuchaBotones);
    	btnSalir.setOnClickListener(escuchaBotones);
    }
    
    //===========================================================================//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //===========================================================================//
    
    //método auxiliar para mostrar mensajes
    private void mostrarMensaje(String mensaje){
    	Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
    	toast.show();
    }
    
    //===========================================================================//
    
    //método para mostrar cuadro de alerta
    private void mostrarAlerta(String mensaje){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(mensaje)
    	      .setTitle("Atención!!")
    	      .setCancelable(false)
    		  .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			 .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
    	
    	AlertDialog cuadro = builder.create();
    	cuadro.show();
    }
    
    //============================================================================//
    
    @Override 
    public void onBackPressed() {
    	mostrarAlerta("Está seguro que desea salir de la aplicación?");
    }
    
    //==============================================================================//
    
    //Clase interna para escucha de eventos de los RadioGroups
    private class EscuchaRadioGroups implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
			//si se seleciono opción del RadioGroup de "Dificultad"
			if (group.getId() == R.id.RGDificultad){
				switch (checkedId){  //verificamos el RadioButton seleccionado
					case R.id.rbtnPrincipiante:
						tvLargoCodigo.setText("Largo de código: 4");
						tvIntentos.setText("Intentos: 10");
						tvRango.setText("Rango: [1 , 4]");
						dificultad = DIFICULTAD.PRINCIPIANTE;
						LARGO_CODIGO = 4;
						MAX_INTENTOS = 10;
					break;
					case R.id.rbtnModerado:
						tvLargoCodigo.setText("Largo de código: 6");
						tvIntentos.setText("Intentos: 20");
						tvRango.setText("Rango: [1 , 8]");
						dificultad = DIFICULTAD.MODERADO;
						LARGO_CODIGO = 6;
						MAX_INTENTOS = 20;
					break;
					case R.id.rbtnExperto:
						tvLargoCodigo.setText("Largo de código: 10");
						tvIntentos.setText("Intentos: 30");
						tvRango.setText("Rango: [1 , 9]");
						dificultad = DIFICULTAD.EXPERTO;
						LARGO_CODIGO = 9;
						MAX_INTENTOS = 30;
					break;
				}
			}else{  //se seleccionó opción del RadioGroup de "Modo de Juego"
				switch (checkedId){  //verificamos el RadioButton seleccionado
					case R.id.rbtnAdivinador:
						modoJuego = MODO_JUEGO.ADIVINADOR;
						mostrarMensaje("Modo de juego ADIVINADOR");
					break;
					case R.id.rbtnPensador:
						modoJuego = MODO_JUEGO.PENSADOR;
						mostrarMensaje("Modo de juego PENSADOR");		
					break;
				}
			}
			
		}  //de on CheckedChanged
    }// de la clase interna EscuchaRadioGrpups
    
    //================================================================================//
    
    //Clase interna para escucha de eventos de botones
    private class EscuchaButtons implements OnClickListener{

		@Override
		public void onClick(View v) {
	        //dejamos el ScrollView arriba
//	        scrollViewPrincipal.fullScroll(View.FOCUS_UP);
//			scrollViewPrincipal.scrollTo(0, 0);
			
			//switch para distinguir que botón se presionó (Jugar, Salir o Ayuda)
			switch (v.getId()){
				case R.id.btnJugar:  //si presionó el botón de jugar se lanza la 
					                 //Activity correspondiente ("Adivinador" o "Pensador")
					
			        //dejamos el ScrollView arriba
					scrollViewPrincipal.scrollTo(0, 0);
					
					//está seleccionado el modo de juego "Adivinador"
					if (modoJuego == MODO_JUEGO.ADIVINADOR){
						//creamos el intent y lo cargamos
						Intent intentAdivinador = new Intent(MainActivity.this, Adivinador.class);
						

						//añadimos la info. al intent
//						intentAdivinador.putExtras(bundle);
						//iniciamos la Activity correspondiente ("Adivinador")
						
						startActivity(intentAdivinador);
					}else{  //está seleccionado el modo de juego "Pensador"
						    //modoJuego == MODO_JUEGO.PENSADOR
						//creamos el intent y lo cargamos
						Intent intentPensador = new Intent(MainActivity.this, Pensador.class);
						
						//añadimos la info. al intent
//						intentPensador.putExtras(bundle);
						//iniciamos la Activity correspondiente ("Pensador")
						startActivity(intentPensador);				
					}

				break;
				case R.id.btnSalir: 
					 mostrarAlerta("Está seguro que desea salir de la aplicación?");
				break;
				case R.id.btnAyuda:  break;
			}//de switch (v.getId() de botones)
			
		}  //de onClick()
    	
    }  //de clase interna EscuchaButtons()
    
}  //de la clase MainActivity
