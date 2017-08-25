import java.util.*;
import algorithms.*;

public class Simulator {
	static Queue queue = new LinkedList();
	static Random rdm = new Random();

	public static void main(String args[]) {
		System.out.println("Bem vindo ao simulador de políticas de escalonamento de I/O!");
		populateQueue(); //Popula o queue com números randômicos
		System.out.println("Disco: " + queue.toString());

		runAlgorithm(); //Seleciona e executa o algoritmo
	}

	public static void populateQueue() {
		for (int i = 0;i < 10; i++)
			queue.add(rdm.nextInt(60));
	};

	public static void runAlgorithm() {
		Algorithm[] algs = new Algorithm[] {new FirstComeFirstServed(), new ShortestSeekFirst(),
											new Elevator(), new Antecipatory()};
		for (Algorithm alg : algs) {
			System.out.println("\nSimulando " + alg.getClass().getSimpleName());
			Algorithm algorithm = alg;

			sequenceOfSeeks();//Imprime a sequência de seeks do algoritmo
		}

	};

	public static void sequenceOfSeeks(){
		//TODO
	};
}