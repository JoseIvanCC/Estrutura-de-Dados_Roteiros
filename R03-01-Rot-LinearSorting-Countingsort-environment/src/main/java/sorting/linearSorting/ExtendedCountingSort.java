package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		if (array == null) {
			return;
		}
		if (leftIndex > rightIndex) {
			return;
		}
		if (leftIndex < 0 || rightIndex < 0) {
			return;
		}
		if (array.length < rightIndex) {
			return;
		}
		if (array.length == 0) {
			return;
		}
		
		if (leftIndex < rightIndex) {
			int maior = achaMaior(array, leftIndex, rightIndex);
			int menor = achaMenor(array, leftIndex, rightIndex);
			
			//Achandoo maior e o menor elemento do array, posso economizar memoria,
			//ao criar um array apenas com aquele intervalo que o menor e o maior estão.
			int n = maior - menor + 1;
			
			//Criacao do array auxiliar e contagem dos elementos no array 
			int[] aux = new int[n];
			for (int i = leftIndex; i <= rightIndex; i++) {
				aux[array[i] -menor] += 1;
			}
			
			//Contador para saber quantos elementos no array sao menores que o maior
			for (int i = leftIndex + 1; i < aux.length; i++) {
				aux[i] += aux[i-1];
			}
			
			//Criacao do novo array e ordenacao, dos elemntos do array recebido, nele
			Integer[] newArray = new Integer[array.length];
			for (int i = 0; i < newArray.length; i++) {
				newArray[aux[array[i] -menor] -1] = array[i];
				aux[array[i] -menor]--;
			}
			
			//Passando os elemntos do vetor ordenado para o vetor recebido no parâmetro
			for (int i = 0; i < newArray.length; i++) {
				array[i] = newArray[i];
			}
		}
		
	}
	
	private int achaMaior(Integer[] v, int ini, int fim) {
		Integer maior = v[0];
		for (int i = 1; i < v.length; i++) {
			if (v[i] > maior) {
				maior = v[i];
			}
		}
		return maior;
	}
	
	private Integer achaMenor(Integer[] v, int ini, int fim) {
		int menor = v[0];
		for (int i = 1; i < v.length; i++) {
			if (v[i] < menor) {
				menor = v[i];
			}
		}
		return menor;
	}

}
