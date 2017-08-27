package algorithms;

import java.util.*;

public class Elevator {

	private int numberArmMotions;
	private int currentPosition;
	private int direction;

	private List<Integer> requests;
	private List<Integer> diskCylinders;
	private List<Integer> seekOrder;

	public Elevator(List<Integer> diskCylinders, List<Integer> requests) {
		this.requests = requests;
		this.diskCylinders = diskCylinders;
		this.seekOrder = new ArrayList<Integer>();

		this.numberArmMotions = 0;
		this.currentPosition = 0;
		this.direction = 1;
	}

	/*
	Processa todas as requisições.
	*/
	public void processAllRequests() {
		processRequest(requests.get(requests.size()/2));
	}

	/*
	Processa uma requisição.
	*/
	public void processRequest(Integer request) {
		if(request > 0 && request < diskCylinders.size()) {
		}
	}

	/*
	Retorna a ordem em que as requisições foram acessadas.
	*/
	public List<Integer> getSeekOrder() {
		return seekOrder;
	}

	/*
	Retorna a quantidade de vezes que o braço do disco precisou se
	mover para atender a todas as requisições.
	*/
	public int getNumberArmMotions() {
		return numberArmMotions;
	}
}
