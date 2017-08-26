import java.util.*;
import algorithms.*;

public class Simulator {

	/*
	Estrutura para representar os cilindros do disco.
	A lista possui 80 posições.
	*/
	static List<Integer> diskCylinders = new ArrayList<Integer>(80);

	/*
	Estrutura para armazenar as requisições ao disco.
	*/
	static List<Integer> requests = new ArrayList<Integer>();
	static Random rdm = new Random();

	public static void main(String args[]) {
		System.out.println("Bem vindo ao simulador de políticas de escalonamento de I/O!");

		initializeDisk();
		initializeRequestList();

		System.out.println("Requisições: " + requests.toString());

		System.out.println("Executando o First-Come First-Served..");
		FirstComeFirstServed fcfs = new FirstComeFirstServed(diskCylinders, requests);
		fcfs.processAllRequests();

		System.out.println("Número de movimentos do braço: " + fcfs.getNumberArmMotions());

		/*
		initializeRequestList(); //Popula o queue com números randômicos

		System.out.println("Disco: " + requests.toString());
		System.out.println("Tamanho do disco: " + requests.size());

		runAlgorithm(); //Seleciona e executa o algoritmo
		*/
	}

	/*
	Inicializa a lista de requisições com 10 números randômicos.
	*/
	public static void initializeRequestList() {
		for (int i = 0;i < 10; i++) {
			requests.add(rdm.nextInt(80));
		}
	}

	/*
	Inicializa as 80 posições do disco com zeros.
	*/
	public static void initializeDisk() {
		for(int i = 0; i < 80; i++) {
			diskCylinders.add(i, 0);
		}
	}

	/*
	public static void runAlgorithm() {
		Algorithm[] algs = new Algorithm[] {new FirstComeFirstServed(diskCylinders, requests), new ShortestSeekFirst(),
											new Elevator(), new Antecipatory()};
		for (Algorithm alg : algs) {
			System.out.println("\nSimulando " + alg.getClass().getSimpleName());

			Algorithm algorithm = alg; //?

			sequenceOfSeeks();//Imprime a sequência de seeks do algoritmo //?
		}
	}
	*/

	public static void sequenceOfSeeks(){
		//TODO
	}
}