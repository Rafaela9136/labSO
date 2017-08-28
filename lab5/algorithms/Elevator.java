package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Elevator {

	private int currentPosition;
	private int direction;

	private List<Integer> requests;
	private List<Integer> seekOrder;

	public Elevator(List<Integer> requests) {
		this.requests = new ArrayList<Integer>(requests);
		this.seekOrder = new ArrayList<Integer>();

		this.currentPosition = requests.size()/2;
		this.direction = 1;
	}

	/*
	Processa todas as requisições.
	*/
	public void processAllRequests() {
		Collections.sort(requests);

		List<Integer> before = requests.subList(0, currentPosition);
		List<Integer> after = requests.subList(currentPosition, requests.size());

		Collections.reverse(before);

		for (int i = 0; i < requests.size(); i++) {
			if(i < after.size()) {
				direction = 1;
				seekOrder.add(after.get(i));
			} else {
				direction = -1;
				seekOrder.add(before.get(i-after.size()));
			}
			
		}
	}

	/*
	Indica a direção do disco que o algoritmo está indo.
	*/
	public int getDirection() {
		return direction;
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
		int numberArmMotions = 0;

		for (int i = 0; i < requests.size()-1; i++) {
			numberArmMotions += Math.abs(seekOrder.get(i)-seekOrder.get(i+1));
		}

		return numberArmMotions;
	}
} 
