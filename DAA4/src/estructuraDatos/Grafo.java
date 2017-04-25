/**
 * PRACTICA 4 DAA: Max-Mean
 * 
 * Esta clase representa un grafo. Contiene una lista de nodos.
 * 
 * @author alu0100888102
 * @version 1.0
 * Ángel Hamilton Lopez
 * alu0100888102@ull.es
 */

package estructuraDatos;

import java.util.*;
import java.io.*;

public class Grafo {
	private ArrayList<ArrayList<Integer>> grafo;
	private int numNodos;
	
	
	/**
	 * Constructores
	 */
	
	public Grafo(){
		setGrafo(new ArrayList<ArrayList<Integer>>());
		setNumNodos(0);
	}
	public Grafo(int size){
		setGrafo(new ArrayList<ArrayList<Integer>>());
		setNumNodos(size);
		for(int i =0; i < getNumNodos(); i++){
			ArrayList<Integer> temp =new ArrayList<Integer>(getNumNodos());
			for(int j =0; j < getNumNodos(); j++){
				temp.add(0);
			}
			getGrafo().add(temp);
		}
		
	}
	public Grafo(Grafo original){
		setGrafo(new ArrayList<ArrayList<Integer>>());
		setNumNodos(original.getNumNodos());
		for(ArrayList<Integer> node : original.getGrafo()){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(Integer in : node){
				temp.add(in);
			}
			getGrafo().add(temp);
		}
	}
	/**
	 * Construye un grafo a partir de un fichero
	 * @param input
	 */
	public Grafo(File input){
		setGrafo(new ArrayList<ArrayList<Integer>>());
		try{
			FileInputStream istream = new FileInputStream(input);
			BufferedReader bufferreader = new BufferedReader(new InputStreamReader(istream));
			String line = null;
			line = bufferreader.readLine();
			
			setNumNodos(Integer.parseInt(line));
			for(int i =0; i < getNumNodos(); i++){
				ArrayList<Integer> temp =new ArrayList<Integer>(getNumNodos());
				for(int j =0; j < getNumNodos(); j++){
					temp.add(0);
				}
				getGrafo().add(temp);
			}
			
			int linkCount =0;
			int actualNode =0;
			while ((line = bufferreader.readLine()) != null) {
				linkCount++;
				
				if(linkCount < getNumNodos()){
					setDistance(actualNode, linkCount, Integer.parseInt(line));
				}
				
				if(linkCount == getNumNodos()-1){
					actualNode++;
					linkCount = actualNode;
				}
			}
			bufferreader.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Error en el fichero: no se encuentra " + e);
			System.exit(1);
		}
		catch(IOException e){
			System.out.println("Error en el fichero: error de entrada/salida " + e);
			System.exit(1);
		}
	}
	
	
	/**
	 * Getters y setters
	 */
	
	public ArrayList<ArrayList<Integer>> getGrafo() {
		return grafo;
	}
	public void setGrafo(ArrayList<ArrayList<Integer>> grafo) {
		this.grafo = grafo;
	}
	public int getNumNodos() {
		return numNodos;
	}
	public void setNumNodos(int numNodos) {
		this.numNodos = numNodos;
	}
	public int getDistance(int nodoFrom, int nodoTo){
		return getGrafo().get(nodoFrom).get(nodoTo);
	}
	public void setDistance(int nodoFrom, int nodoTo, int distance){
		getGrafo().get(nodoFrom).set(nodoTo, distance);
		getGrafo().get(nodoTo).set(nodoFrom, distance);
	}
	

	public String toString(){
		String out = new String();
		out += "\t\t";
		for(int i = 0; i < getGrafo().size(); i++){
			out += i + "\t";
		}
		out +="\n\n";
		for(int i = 0; i < getGrafo().size(); i++){
			out += "Nodo "+ i + ":  ";
			for(int j = 0; j < getGrafo().size(); j++){
				out += "\t" + getGrafo().get(i).get(j);
			}
			out +="\n";
		}
		out += "Valor total de la red: " +getValor();
		return out;
	}
	
	/**
	 * Método que imprime sólo los nodos indicados
	 * @param sol
	 * @return
	 */
	public String printSol(ArrayList<Integer> sol){
		String out = new String();
		out += "\t\t";
		for(Integer num : sol){
			out += num + "\t";
		}
		out +="\n\n";
		for(Integer num : sol){
			out += "Nodo "+ num + ":  ";
			for(Integer num2 : sol){
				out += "\t" + getGrafo().get(num).get(num2);
			}
			out +="\n";
		}
		out += "Valor total de la red: " +getValor();
		return out;
	}
	
	/**
	 * Método que devuelve el valor total de las aristas del grafo
	 * @return
	 */
	public int getValor(){
		int out=0;
		for(int i =0; i< getNumNodos(); i++){
			for(int j = i+1; j< getNumNodos(); j++){
				out += getDistance(i, j);
			}
		}
		return out;
	}
	
	/**
	 * Metodo que elimina un nodo del grafo
	 */
	public void remove(int node){
		for(int i =0; i < getGrafo().get(node).size(); i++)
			getGrafo().get(node).set(i, 0);
		for(ArrayList<Integer> in : getGrafo())
			in.set(node, 0);
	}
	
	/**
	 * Metodo que devuelve todos los nodos actualmente en el grafo
	 */
	public ArrayList<Integer> getNodos(){
		ArrayList<Integer> out = new ArrayList<Integer>();
		for(int i =0; i < getNumNodos(); i++){
			for(int j =0; j < getNumNodos(); j++){
				if(getDistance(i, j) != 0 && !out.contains(i))
					out.add(i);
			}
		}
		return out;
	}
}