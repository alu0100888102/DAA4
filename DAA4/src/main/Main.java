/**
 * PRACTICA 4 DAA: Max-Mean
 * 
 * En esta practica se nos pide crear un programa que utilice distintos algoritmos para encontrar el subgrafo de valor máximo dentro de un grafo.
 * 
 * @author alu0100888102
 * @version 1.0
 * Ángel Hamilton Lopez
 * alu0100888102@ull.es
 */

package main;

import java.io.*;
import algoritmia.*;

public class Main {
	public static void main(String args[]){
		File input = new File(args[0]);
		Algoritmos test = new Algoritmos(input);
		for(int i=0; i<4; i++){
			int k = 10 + i*5;
			input = new File("max-mean-div-"+k+".txt");
			test = new Algoritmos(input);
			System.out.print("\n\n\n\nmax-mean-div-"+k+".txt\n\n");
			System.out.println("Algoritmo propuesto en la práctica.");
			test.vorazClase();
			System.out.println("Nodos elegidos: "+test.getNodosSolucion());
			System.out.println(test.printSol());
			
			System.out.println("\n\nAlgoritmo propio.");
			test.vorazPropio();
			System.out.println("Nodos elegidos: "+test.getNodosSolucion());
			System.out.println(test.printSol());
			
			System.out.println("\n\nAlgoritmo grasp.");
			test.grasp();
			System.out.println("Nodos elegidos: "+test.getNodosSolucion());
			System.out.println(test.printSol());
			
			System.out.println("\n\nAlgoritmo de busqueda por entorno variable.");
			test.entornoVariable();
			System.out.println("Nodos elegidos: "+test.getNodosSolucion());
			System.out.println(test.printSol());
			
			int j = i*100+100;
			System.out.println("\n\nAlgoritmo multiarranque con "+j+" iteraciones.");
			test.multiarranque(j);
			System.out.println("Nodos elegidos: "+test.getNodosSolucion());
			System.out.println(test.printSol());
		}
	}
}