/**
 * PRACTICA 4 DAA: Max-Mean
 * 
 * Esta es la clase que ejecuta los algoritmos
 * 
 * @author alu0100888102
 * @version 1.0
 * Ángel Hamilton Lopez
 * alu0100888102@ull.es
 */

package algoritmia;

import estructuraDatos.*;
import java.io.*;
import java.util.*;

public class Algoritmos {
	private Grafo problema;
	private Grafo solucion; 
	private ArrayList<Integer> nodosSolucion;
	
	
	/**
	 * Constructores
	 */
	
	public Algoritmos (){
		problema = new Grafo();
		solucion = new Grafo();
		nodosSolucion = new ArrayList<Integer>();
	}
	public Algoritmos (File in){
		problema = new Grafo(in);
		solucion = new Grafo();
		nodosSolucion = new ArrayList<Integer>();
	}
	
	
	/**
	 * Getters y setters 
	 */
	
	public Grafo getProblema() {
		return problema;
	}
	public void setProblema(Grafo problema) {
		this.problema = problema;
	}
	public Grafo getSolucion() {
		return solucion;
	}
	public void setSolucion(Grafo solucion) {
		this.solucion = solucion;
	}
	public ArrayList<Integer> getNodosSolucion() {
		return nodosSolucion;
	}
	public void setNodosSolucion(ArrayList<Integer> nodosSolucion) {
		this.nodosSolucion = nodosSolucion;
	}
	
	
	/**
	 * Método que reinicia la solución.
	 */
	public void resetSolucion(){
		setNodosSolucion(new ArrayList<Integer>());
		setSolucion(new Grafo(getProblema().getNumNodos()));
	}
	
	
	/**
	 * Método público que ejecuta el algoritmo que viene explicado en la práctica
	 */
	public void vorazClase(){
		resetSolucion();
		long startTime = System.nanoTime();
		setSolucion(vorazStep());
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Duracion: " + duration);
	}
	/**
	 * 1º Cojemos una de las aristas de mayor valor dentro del grafo y añadimos ambos nodos a la solución
	 * 2º Por cada nodo no presente en la solución
	 * 		1º Sumamos el valor de sus aristas hacia los nodos ya presentes en la solución
	 * 		2º Elegimos aquel nodo con un valor máximo
	 * 3º Si añadir este nodo a la solución aumenta su valor, lo añadimos
	 * 4º Repetir hasta que el nodo elegido no mejore la solución
	 */
	private Grafo vorazStep(){
		/** Crea el conjunto inicial de la solucion*/
		Grafo graph = new Grafo(getProblema().getNumNodos());
		Grafo aux;
		
		int maxvalue = Integer.MIN_VALUE;
		int imax =-1;
		int jmax =-1;
		
		for(int i =0; i < getProblema().getNumNodos(); i++){
			for(int j = i+1; j < getProblema().getNumNodos(); j++){
				if(getProblema().getDistance(i, j) > maxvalue){
					imax = i;
					jmax = j;
					maxvalue = getProblema().getDistance(i, j);
				}
			}
		}
		
		getNodosSolucion().add(imax);
		getNodosSolucion().add(jmax);
		graph.setDistance(imax, jmax, maxvalue);

		do{
			/**Busca el nodo que aumente lo máximo el valor*/
			maxvalue = Integer.MIN_VALUE;
			imax = -1;
			aux = new Grafo(graph);
			
			for(int i=0; i < graph.getNumNodos(); i++){
				if(!getNodosSolucion().contains(i)){
					int acc=0;
					for(Integer temp : getNodosSolucion()){
						acc += problema.getDistance(i, temp);
					}
					if(acc > maxvalue){
						maxvalue = acc;
						imax = i;
					}
				}
			}
			
			if(imax == -1)
				break;
			for(Integer temp : getNodosSolucion()){
				aux.setDistance(imax, temp, problema.getDistance(imax, temp));
			}
			if(aux.getValor() > graph.getValor()){
				getNodosSolucion().add(imax);
				graph = aux;
			}

		}while( aux == graph);
		return graph;
	}
	
	
	/**
	 * Método público que ejecuta el algorimo voraz de diseño propio
	 */
	public void vorazPropio(){
		resetSolucion();
		long startTime = System.nanoTime();
		setSolucion(vorazPropioStep());
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Duracion: " + duration);
		
	}
	/**
	 * El algoritmo es similar, pero en vez de empezar por la pareja de nodos con la arista máxima,
	 * empieza por el nodo cuyo valor total de aristas sea máximo.
	 */
	private Grafo vorazPropioStep(){
		
		Grafo graph = new Grafo(getProblema().getNumNodos());
		Grafo aux;
		
		int maxvalue = Integer.MIN_VALUE;
		int max =-1;
		
		/** Elije el nodo con mayor valor total y lo añade a la solución*/
		for(int i =0; i < getProblema().getNumNodos(); i++){
			int acc =0;
			for(int j = 0; j < getProblema().getNumNodos(); j++){
				acc += getProblema().getDistance(i, j);
			}
			if(acc >= maxvalue){
				maxvalue = acc;
				max = i;
			}
		}
		
		getNodosSolucion().add(max);

		do{
			/**Busca el nodo que aumente lo máximo el valor*/
			maxvalue = Integer.MIN_VALUE;
			max = -1;
			aux = new Grafo(graph);
			
			for(int i=0; i < graph.getNumNodos(); i++){
				if(!getNodosSolucion().contains(i)){
					int acc=0;
					for(Integer temp : getNodosSolucion()){
						acc += problema.getDistance(i, temp);
					}
					if(acc > maxvalue){
						maxvalue = acc;
						max = i;
					}
				}
			}
			
			if(max == -1)
				break;
			for(Integer temp : getNodosSolucion()){
				aux.setDistance(max, temp, problema.getDistance(max, temp));
			}
			if(aux.getValor() > graph.getValor()){
				getNodosSolucion().add(max);
				graph = aux;
			}

		}while( aux ==  graph);
		return graph;
	}
	
	
	/**
	 * Método público que ejecuta el algoritmo GRASP 
	 */
	public void grasp(){
		resetSolucion();
		long startTime = System.nanoTime();
		setSolucion(graspStep());
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Duracion: " + duration);
	}
	/**
	 * Construcción:
	 * 		1º Elegimos un nodo al azar y lo añadimos a la solución
	 * 		2º Elegimos un nodo al azar del grupo de nodos que tengan un valor positivo si fueran añadidos a la solución y lo añadimos
	 * 		3º Repetimos hasta que no haya nodos que fueran a incrementar el valor de la solución
	 * Buscar mejoras locales:
	 * 		1º Por cada nodo no presente en la solución
	 * 			1º Creamos una grafo temporal igual a la solución
	 * 			2º Eliminamos todos los nodos que hagan que este tuviera un valor negativo en el grafo temporal
	 * 			3º Añadimos este nodo al grafo temporal
	 * 		2º Elegimos conservar el grafo temporal que tenga un valor superior a la solución actual
	 * 		3º Repetimos hasta que ninguno de los grafos temporales mejore la solución
	 * @return
	 */
	private Grafo graspStep(){
		/** Construcción*/
		Grafo graph = new Grafo(getProblema().getNumNodos());
		Grafo aux;
		Grafo maxG;
		do{
			/** busca nodos cuyo valor junto con los nodos ya añadido a la solucion no sea negativo*/
			aux = new Grafo(graph);
			ArrayList<Integer> rcl = new ArrayList<Integer>();
			for(int i =0; i < getProblema().getNumNodos(); i++){
				if(!getNodosSolucion().contains(i)){
					int acc =0;
					for(Integer in : getNodosSolucion()){
						acc += getProblema().getDistance(in, i);
					}
					if(acc >= 0)
						rcl.add(i);
				}
			}
			if(rcl.size() == 0)
				break;
			
			/** Se añade al azar uno de los posibles nodos al conjunto*/
			Random randomGenerator = new Random();
			int chosenNode = rcl.get(randomGenerator.nextInt(rcl.size()));
			getNodosSolucion().add(chosenNode);
			for(Integer temp : getNodosSolucion()){
				aux.setDistance(chosenNode, temp, problema.getDistance(chosenNode, temp));
			}
			graph = aux;
		}while(aux == graph);

		/** Busqueda Local*/
		do{
			ArrayList<Integer> notinSol = new ArrayList<Integer>();
			maxG = new Grafo(graph);
			int valuemax = Integer.MIN_VALUE;
			for(int i=0; i < graph.getNumNodos(); i++)
				if(!getNodosSolucion().contains(i))
					notinSol.add(i);
			
			/** Sustituye nodos en la red */
			for(Integer node : notinSol){
				aux = new Grafo(graph);
				for(Integer node2 : getNodosSolucion()){
					if(getProblema().getDistance(node, node2) <0){
						aux.remove(node2);
					}
				}
				ArrayList<Integer> auxnodos = aux.getNodos();
				for(Integer temp : auxnodos){
					aux.setDistance(node, temp, problema.getDistance(node, temp));
				}
				if(aux.getValor() > valuemax){
					valuemax = aux.getValor();
					maxG = aux;
				}
			}
			
			/** Comprueba el resultado*/
			if(valuemax == Integer.MIN_VALUE)
				break;
			if(maxG.getValor() > graph.getValor()){
				setNodosSolucion(maxG.getNodos());
				graph = maxG;
			}


		}while( maxG ==  graph);
		return graph;
	}
	
	
	/**
	 * Método que ejecuta el algoritmo de búsqueda por entorno variable
	 */
	public void entornoVariable(){
		resetSolucion();
		long startTime = System.nanoTime();
		setSolucion(eVariableStep());
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Duracion: " + duration);
	}
	/**
	 * 1º Genera una solución aleatoria
	 * 2º Comprueba si se puede eliminar algún nodo para mejorar la solución, si es así lo elimina y repetimos
	 * 3º Comprueba si se puede añadir algún nodo para mejorar la solución, si es así lo elimina y volvemos a 2
	 * 4º Si no es posible añadir o eliminar un nodo para mejorar la solución, devuelve la solución actual
	 * 
	 */
	private Grafo eVariableStep(){
		Grafo graph = new Grafo(getProblema().getNumNodos());
		Grafo aux;
		
		/** Construye una conjunto aleatorio de nodos*/
		Random randomGenerator = new Random();
		int randomNumber = 1 + randomGenerator.nextInt(getProblema().getNumNodos()-1);
		for(int i =0; i < randomNumber; i++){
			int rtemp = randomGenerator.nextInt(getProblema().getNumNodos());
			if(!getNodosSolucion().contains(rtemp)){
				getNodosSolucion().add(rtemp);
				for(Integer in : getNodosSolucion()){
					graph.setDistance(rtemp, in, getProblema().getDistance(rtemp, in));
				}
			}
			else
				i--;/** Si el nodo elegido al azar ya está en el grupo, repetimos*/
		}

		do{
			aux = new Grafo(graph);
			Grafo temp;
			
			/** Comprueba si se puede eliminar nodos*/
			for(Integer in : getNodosSolucion()){
				temp = new Grafo(graph);
				temp.remove(in);
				if(temp.getValor() > aux.getValor()){
					aux = temp;
				}
			}
			
			ArrayList<Integer> notinSol = new ArrayList<Integer>();
			for(int i=0; i < graph.getNumNodos(); i++)
				if(!getNodosSolucion().contains(i))
					notinSol.add(i);
			
			/** Comprueba si se pueden añadir nodos*/
			for(Integer in : notinSol){
				temp = new Grafo(graph);
				for(Integer in2 : getNodosSolucion()){
					temp.setDistance(in, in2, problema.getDistance(in, in2));
				}
				if(temp.getValor() > aux.getValor()){
					aux = new Grafo(temp);
				}
			}
			
			if(aux.getValor() > graph.getValor()){
				setNodosSolucion(aux.getNodos());
				graph = aux;
			}


		}while( aux ==  graph);
		setNodosSolucion(graph.getNodos());
		return graph;
	}
	
	
	/**
	 * Método que ejecuta el algoritmo multiarranque
	 */
	public void multiarranque(int steps){
		resetSolucion();
		long startTime = System.nanoTime();
		setSolucion(multiarranqueStep(steps));
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Duracion: " + duration);
	}
	/**
	 * Utiliza la busqueda por entorno variable para buscarsoluciones. cuando se superan "steps" iteraciones sin mejora, se devuelve la solucion máxima en el momento
	 */
	private Grafo multiarranqueStep(int steps){
		Grafo graph = graspStep();
		Grafo aux;
		
		int iterator=0;

		while (iterator < steps){
			resetSolucion();
			aux = graspStep();
			if(aux.getValor() > graph.getValor()){
				graph = aux;
				iterator =0;
			}
			else
				iterator++;

		}
		setNodosSolucion(graph.getNodos());
		return graph;
	}
	
	
	public String printSol(){
		return getSolucion().printSol(getNodosSolucion());
	}
}