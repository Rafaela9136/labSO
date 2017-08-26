package algorithms;

import java.util.*;

public class FirstComeFirstServed implements Algorithm{

	private int numberArmMotions;
	private int currentPosition;

	private List<Integer> requests;
	private List<Integer> diskCylinders;

	public FirstComeFirstServed(List<Integer> diskCylinders, List<Integer> requests) {
		this.requests = requests;
		this.diskCylinders = diskCylinders;

		this.numberArmMotions = 0;
		this.currentPosition = 0;
	}

	/*
	Processa todas as requisições.
	*/
	public void processAllRequests() {
		for(Integer request : requests) {
			processRequest(request);
		}
	}

	/*
	Processa uma requisição.
	*/
	public void processRequest(Integer request) {
		if(request > 0 && request < diskCylinders.size()) {
			if(request >= currentPosition) {
				for(int i = currentPosition + 1; i <= request; i++) {
					numberArmMotions++;
				}
			} else {
				for(int i = currentPosition - 1; i >= request; i--) {
					numberArmMotions++;
				}
			}
			currentPosition = request;
		}
	}

	/*
	Retorna a quantidade de vezes que o braço do disco precisou se
	mover para atender a todas as requisições.
	*/
	public int getNumberArmMotions() {
		return numberArmMotions;
	}

}