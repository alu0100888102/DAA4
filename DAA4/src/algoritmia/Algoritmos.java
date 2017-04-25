/**
 * PRACTICA 4 DAA: Max-Mean
 * 
 * Esta es la clase que ejecuta los algoritmos
 * 
 * @author alu0100888102
 * @version 1.0
 * �ngel Hamilton Lopez
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
	 * M�todo que reinicia la soluci�n.
	 */
	public void resetSolucion(){
		setNodosSolucion(new ArrayList<Integer>());
		setSolucion(new Grafo(getProblema().getNumNodos()));
	}
	
	
	/**
	 * M�todo p�blico que ejecuta el algoritmo que viene explicado en la pr�ctica
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
	 * 1� Cojemos una de las aristas de mayor valor dentro del grafo y a�adimos ambos nodos a la soluci�n
	 * 2� Por cada nodo no presente en la soluci�n
	 * 		1� Sumamos el valor de sus aristas hacia los nodos ya presentes en la soluci�n
	 * 		2� Elegimos aquel nodo con un valor m�ximo
	 * 3� Si a�adir este nodo a la soluci�n aumenta su valor, lo a�adimos
	 * 4� Repetir hasta que el nodo elegido no mejore la soluci�n
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
			/**Busca el nodo que aumente lo m�ximo el valor*/
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
	 * M�todo p�blico que ejecuta el algorimo voraz de dise�o propio
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
	 * El algoritmo es similar, pero en vez de empezar por la pareja de nodos con la arista m�xima,
	 * empieza por el nodo cuyo valor total de aristas sea m�ximo.
	 */
	private Grafo vorazPropioStep(){
		
		Grafo graph = new Grafo(getProblema().getNumNodos());
		Grafo aux;
		
		int maxvalue = Integer.MIN_VALUE;
		int max =-1;
		
		/** Elije el nodo con mayor valor total y lo a�ade a la soluci�n*/
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
			/**Busca el nodo que aumente lo m�ximo el valor*/
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
	 * M�todo p�blico que ejecuta el algoritmo GRASP 
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
	 * Construcci�n:
	 * 		1� Elegimos un nodo al azar y lo a�adimos a la soluci�n
	 * 		2� Elegimos un nodo al azar del grupo de nodos que tengan un valor positivo si fueran a�adidos a la soluci�n y lo a�adimos
	 * 		3� Repetimos hasta que no haya nodos que fueran a incrementar el valor de la soluci�n
	 * Buscar mejoras locales:
	 * 		1� Por cada nodo no presente en la soluci�n
	 * 			1� Creamos una grafo temporal igual a la soluci�n
	 * 			2� Eliminamos todos los nodos que hagan que este tuviera un valor negativo en el grafo temporal
	 * 			3� A�adimos este nodo al grafo temporal
	 * 		2� Elegimos conservar el grafo temporal que tenga un valor superior a la soluci�n actual
	 * 		3� Repetimos hasta que ninguno de los grafos temporales mejore la soluci�n
	 * @return
	 */
	private Grafo graspStep(){
		/** Construcci�n*/
		Grafo graph = new Grafo(getProblema().getNumNodos());
		Grafo aux;
		Grafo maxG;
		do{
			/** busca nodos cuyo valor junto con los nodos ya a�adido a la solucion no sea negativo*/
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
			
			/** Se a�ade al azar uno de los posibles nodos al conjunto*/
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
	 * M�todo que ejecuta el algoritmo de b�squeda por entorno variable
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
	 * 1� Genera una soluci�n aleatoria
	 * 2� Comprueba si se puede eliminar alg�n nodo para mejorar la soluci�n, si es as� lo elimina y repetimos
	 * 3� Comprueba si se puede a�adir alg�n nodo para mejorar la soluci�n, si es as� lo elimina y volvemos a 2
	 * 4� Si no es posible a�adir o eliminar un nodo para mejorar la soluci�n, devuelve la soluci�n actual
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
				i--;/** Si el nodo elegido al azar ya est� en el grupo, repetimos*/
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
			
			/** Comprueba si se pueden a�adir nodos*/
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
	 * M�todo que ejecuta el algoritmo multiarranque
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
	 * Utiliza la busqueda por entorno variable para buscarsoluciones. cuando se superan "steps" iteraciones sin mejora, se devuelve la solucion m�xima en el momento
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