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

		System.out.println("\nExecutando o First-Come First-Served..");
		FirstComeFirstServed fcfs = new FirstComeFirstServed(diskCylinders, requests);
		fcfs.processAllRequests();
		System.out.println("Número de movimentos do braço: " + fcfs.getNumberArmMotions());

		System.out.println("\nExecutando o Shortest Seek First..");
		ShortestSeekFirst ssf = new ShortestSeekFirst(requests);
		ssf.processAllRequests();
		System.out.println("Ordem de seek: " + ssf.getSeekOrder());
		System.out.println("Número de movimentos do braço: " + ssf.getNumberArmMotions());

		System.out.println("\nExecutando o Elevator..");
		Elevator evt = new Elevator(requests);
		evt.processAllRequests();
		System.out.println("Ordem de seek: " + evt.getSeekOrder());
		System.out.println("Número de movimentos do braço: " + evt.getNumberArmMotions());
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
}