package algorithms;

import java.util.ArrayList;
import java.util.List;

public class ShortestSeekFirst {

	private List<Integer> requests;
	private List<Integer> seekOrder;
	
	private int numberArmMotions;

	public ShortestSeekFirst(List<Integer> requests) {
		this.requests = new ArrayList<>(requests);
		this.seekOrder = new ArrayList<Integer>();

		this.numberArmMotions = 0;
	}

	/*
	Processa todas as requisições.
	*/
	public void processAllRequests() {
		processRequest(requests.get(0));
	}

	/*
	Processa uma requisição.
	*/
	private void processRequest(Integer request) {
		seekOrder.add(request);
		requests.remove(request);
		
		if(!requests.isEmpty()) {
			Integer next = closest(request);
			processRequest(next);
		}
	}

	/*
	Encontra a requisição mais próxima.
	*/
	public Integer closest(Integer request) {
		Integer distance = Math.abs(requests.get(0) - request);
		Integer closest = requests.get(0);
		for (int i = 1; i < requests.size() ; i++) {
			Integer idistance = Math.abs(requests.get(i) - request);
			if(idistance.compareTo(distance) < 0) {
				closest = requests.get(i);
				distance = idistance;

			}				
		}
		numberArmMotions += distance;
		return closest;
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
