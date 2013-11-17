/*
 * Extiende un EditText para personalizar las entradas del usuario
 * (código del adivinador) según la dificultad. Limita el largo del código,
 * y los dígitos a ingresar (según el rango del código)
 */

package com.sebamawa.amastermind;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextAdivinador extends EditText{

	public EditTextAdivinador(Context context) {
		super(context);
		init();
	}
	
	public EditTextAdivinador(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	public EditTextAdivinador(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
        //InputFilter para establecer el largo (LARGO_CODIGO) a introducir en el campo de texto
        InputFilter filtroLargo = new InputFilter.LengthFilter(MainActivity.LARGO_CODIGO);
        
        
        //InputFilter para establecer sólo los dígitos permitidos en el rango para el campo de texto
        InputFilter filtroEntrada = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				
				//según el nivel de dificultad establecemos lo aceptado en el campo
				switch(MainActivity.dificultad){
					case PRINCIPIANTE: 
						if (source.toString().matches("[1-4]")){
							return source;
						}
						
						//prueba para validar sólo letras y números (cuando el teclado
						//desplegado tiene letras)
//						for (int i=start; i<end; i++){
//							if (!Character.isLetterOrDigit(source.charAt(i))){
//								return "";
//							}
					break;
					case MODERADO:
						if (source.toString().matches("[1-6]")){
							return source;
						}
					break;
					case EXPERTO:
						if (source.toString().matches("[1-9]")){
							return source;
						}
					break;
				}	
				
				return "";  //return null si se quiere devolver lo ingresado
				//return null;
			}
		};
        
		//asigna filtros al campo de texto (se pueden asignar otros filtros)
		this.setFilters(new InputFilter[]{filtroLargo, filtroEntrada});
		
    }  //de init()
	
}
