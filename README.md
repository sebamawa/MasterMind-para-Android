MasterMind-para-Android
=======================
Versión del juego de lógica - estrategia MasterMind para Android, realizado con el IDE Eclipse y el plugin ADT.

El objetivo del juego (en modo Adivinador) consiste en decifrar un código numérico de largo n, donde n puede valer 
4, 6 o 9, y cada dígito del código está en el rango [1..n], pudiéndose repetir dichos dígitos. Según el largo del código,
se disponen de 10, 20 o 30 intentos para hallar el código secreto (llamado código del Pensador).
Cada intento del jugador (Adivinador) por hallar el código del Pensador recibe información de lo próximo que está
el código propuesto al del Pensador. Dicha información consiste en dos valores numéricos llamados BUENOS y REGULARES
(notas dadas por el Pensador) que indican respectivamente los dígitos que están exactamente en la misma posición (de los códigos
del Pensador y el propuesto por el Adivinador), y los dígitos que están en ambos códigos pero en posiciones
distintas.

Esta versión es una adaptación del proyecto propuesto en el curso "Aprende a programar desde cero" en la lección:
http://www.foro.lospillaos.es/leccion-100-jmastermind-vt13298.html

El proyecto anterior está propuesto para ser implementado en Java para escritorio, y lo que se ha hecho aquí es la
adaptación del mismo para la plataforma Android.

En el siguiente link se muestra una partida del juego:
http://www.youtube.com/watch?v=E8g5FOz6xBQ&feature=youtu.be

//====================================================================================================================//
MEJORAS A REALIZAR:
* Implementar el modo de juego Pensador
* Implementar la ayuda del botón Ayuda del menú principal
