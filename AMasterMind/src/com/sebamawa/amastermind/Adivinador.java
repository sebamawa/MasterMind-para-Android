/* 
 * Clase que representa el modo de juego "Adivinador"
 * No se almacena en el BackStack pues termina lanzando una nueva Activity de Fin de Juego
 */

package com.sebamawa.amastermind;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Adivinador extends Activity {
	
	//componentes
	TextView tvDatosPartida;
	TextView tvDatosIntento;
	EditText etCodigoAdivinador;
	Button btnConfirmarCodigoAdivinador;
	LinearLayout layoutPrincipal;
	
	//private int MAX_INTENTOS;  //número máximo de intentos de la partida
	
	private String codigoPensadorString; //arreglo para almacenar el código del pensador
	private String codigoAdivinadorString;  //para el Adivinador
	
	private int cuentaBuenos, cuentaRegulares;
	private int intento = 0;  //número de intento del usuario
	private String datosIntento ="";  //para mostrar al usuario número de intento,
									  //buenos y regulares (en un TextView)

		
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adivinador);
        
        //referencia componentes
        referenciarComponentes();
        
//        //establece la configuración (dificultad) del juego
//        establecerDificultad(); 
        
        //genera el código a adivinar (del pensador) según la dificultad elegida
        codigoPensadorString = generarCodigo(MainActivity.dificultad);  
        
        //muestra los datos de la partida en un TextView
        String datosPartida="";
        switch (MainActivity.dificultad) {
			case PRINCIPIANTE: datosPartida+="PRINCIPIANTE"; break;
			case MODERADO: datosPartida+="MODERADO"; break;
			case EXPERTO: datosPartida+="EXPERTO"; break;
		}
        tvDatosPartida.setText("ModoJuego: "+datosPartida+"; Rango: [1 ; "+MainActivity.LARGO_CODIGO+"]");

        
        //si se presiona el botón se lee el código introducido en el campo
        //y muestra resultados (número de intento y número de buenos y regulares)
        btnConfirmarCodigoAdivinador.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	    		intento++;  //aumentamos en 1 el número de intento
	    		
		    	//sólo si el código introducido tiene el largo adecuado comparamos
		    	//códigos del pensador y adivinador
		    	if (etCodigoAdivinador.getText().length() == MainActivity.LARGO_CODIGO){
		    		
		    		codigoAdivinadorString = leerDesdeCampo();  //leemos el código introducido en el campo
		    		  			    		
		    		cuentaBuenos = 0;
		    		//String codigoPens = codigoPensadorString;  //auxiliar
		    		//String codigoAd = codigoAdivinadorString;  //auxiliar
		    		
		    		//convertimos los códigos del pensador y adivinador a arreglos de char para
		    		//facilitar el cálculo de buenos y regulares.
		    		char[] codigoPensArr = codigoPensadorString.toCharArray();
		    		char[] codigoAdArr = codigoAdivinadorString.toCharArray();
		    		for (int i=0; i<=codigoPensadorString.length()-1; i++){
		    			if (codigoPensArr[i] == codigoAdArr[i]){
		    				cuentaBuenos++;
		    				codigoAdArr[i] = '*';
		    				codigoPensArr[i] = '#';
		    			}
		    		}
		    		
		    		//calculo de regulares
		    		cuentaRegulares = 0;
		    		for (int i=0; i<=codigoPensadorString.length()-1; i++){
		    			for (int j=0; j<=codigoPensadorString.length()-1; j++){
		    				if (j!=i && codigoAdArr[j] == codigoPensArr[i]){
		    					cuentaRegulares++;
			    				codigoAdArr[i] = '*';
			    				codigoPensArr[i] = '#';	
		    				}
		    			}//de for interno
		    		}  //del for externo
		    			
		    		
		    		//mostramos datos (número de intento y números de buenos y regulares)
		    		mostrarDatosIntento();
		    		
		    		
		    		//mostramos resultados
		    		if (intento == MainActivity.MAX_INTENTOS && cuentaBuenos<MainActivity.LARGO_CODIGO){
//		    			mostrarMensaje("Lo siento has perdido, el código era "+codigoPensadorString);
		    			layoutPrincipal.setVisibility(View.INVISIBLE);
		    			mostrarMensaje("Lo siento has perdido, el código era "+codigoPensadorString);
		    			//delay de 4 segundos
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								//Luego de 4 segundos se hace lo que sigue
								finish();
							}
						}, 4000);
		    			
		    		}else
		    			if (cuentaBuenos== MainActivity.LARGO_CODIGO && intento<=MainActivity.MAX_INTENTOS){
		    				//layoutPrincipal.setVisibility(View.INVISIBLE);
		    				
		    				//lanzamos la Activity de fin de juego
		    				Intent intentFinJuego = new Intent(Adivinador.this, FinDeJuego.class);
		    				startActivity(intentFinJuego);
			    			mostrarMensaje("Felicitaciones, has ganado. El código era "+codigoPensadorString);
			    			
		    			}
		    			    		
		    		//borramos el texto del campo
		    		etCodigoAdivinador.setText("");
	
				}else{
					mostrarMensaje("El código no tiene "+MainActivity.LARGO_CODIGO+" dígitos");
				}
	    		
			}
		});
    }
    
    //====================================================================//
    
    private void referenciarComponentes(){
    	
    	tvDatosPartida = (TextView) findViewById(R.id.tvDatosPartida);
    	tvDatosIntento = (TextView) findViewById(R.id.tvDatosIntento);
    	etCodigoAdivinador = (EditText) findViewById(R.id.etCodigoAdivinador);
    	btnConfirmarCodigoAdivinador = (Button) findViewById(R.id.btnConfirmarCodigo);
    	layoutPrincipal = (LinearLayout) findViewById(R.id.layoutPrincipal);
    }
    
    //======================================================================//
    
    //genera el código a adivinar según el nivel de dificultad
    private String generarCodigo(MainActivity.DIFICULTAD dif){
    	int[] codigoArr;
    	Random ran = new Random();
    	
//    	//según la dificultad establecemos el largo de código adecuado
//    	switch (dif){
//    		case PRINCIPIANTE: MainActivity.LARGO_CODIGO = 4; break;
//    		case MODERADO: MainActivity.LARGO_CODIGO = 6; break;
//    		case EXPERTO: MainActivity.LARGO_CODIGO = 9; break;
//    	}
    	
    	//inicializamos el arreglo del código con el tamaño apropiado de celdas
    	codigoArr = new int[MainActivity.LARGO_CODIGO];
    	
    	
    	//generamos el código aleatorio (puede tener números repetidos (caso sencillo))
    	for (int i=0; i<=MainActivity.LARGO_CODIGO-1; i++){
    		codigoArr[i] = ran.nextInt(MainActivity.LARGO_CODIGO) + 1;
    	}
    	
    	//transformamos el código a String por comodidad(usando for each)
    	String codigoString ="";
    	for (int n: codigoArr){
//    		codigoString += n +" ";
    		codigoString += n;
    	}
    	
    	return codigoString;
    }//de generarCodigo()
        
    //============================================================================//
    
    private String leerDesdeCampo(){
    	//FALTA CONTROLAR LA ENTRADA (ENTEROS EN EL RANGO Y LARGO ADECUADO)

    		return etCodigoAdivinador.getText().toString();
    }
    
    //===============================================================================//
    
    private void mostrarDatosIntento(){
    	datosIntento += "Intento "+intento+" ; CóDIGO: "+
    			etCodigoAdivinador.getText().toString()+"\n"+
    			"Buenos = "+cuentaBuenos+" ; Regulares = "+cuentaRegulares+"\n";
    	tvDatosIntento.setText(datosIntento);
    }
        
    //================================================================================//
    
    //auxiliar para mostrar mensajes en cuadro Toast
    private void mostrarMensaje(String mensaje){
    	Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
    	toast.show();
    }
    
    //==================================================================================//
    
    //muestra cuadros de alerta
    private void mostrarAlerta(String mensaje){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(mensaje)
    		   .setTitle("Atención!!")
    		   .setCancelable(false)
    		   .setPositiveButton("Si", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
    	AlertDialog cuadro = builder.create();
    	cuadro.show();
    }
    
    //===================================================================================//
    
    @Override
    public void onBackPressed() {
    	mostrarAlerta("Está seguro que desea volver al Menú Principal?");
    }
}  //de la clase Adivinador
