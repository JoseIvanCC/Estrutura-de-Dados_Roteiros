package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		
		//Validacao
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
			//procurando o maior elemento do array
			Integer maiorElemento = achaMaior(array, leftIndex, rightIndex);
			
			//criacao do array auxiliar e contagem dos elementos
			int[] aux = new int[maiorElemento+1]; 
			for (int i = leftIndex; i <= rightIndex; i++) {
				aux[array[i]] += 1;
			}
			
			//fazendo o acumulador da quantidade de elementos
			//para saber quantos elementos no array sao menores que o maior
			for (int i = leftIndex + 1; i < aux.length; i++) {
				aux[i] += aux[i-1];
			}
			
			//criacao do array a ser ordenado e sua respectiva ordenacao
			Integer[] newArray = new Integer[array.length];
			for (int i = leftIndex; i < newArray.length; i++) {
				newArray[aux[array[i]]-1] = array[i];
				aux[array[i]]--;
			}
			
			//passando os elementos do array ordenado para o array titular que recebi no metodo
			for (int i = 0; i < newArray.length; i++) {
				array[i] = newArray[i];
			}
		}
		
	}
	
	private Integer achaMaior(Integer[] v, int ini, int fim) {
		int maior = v[ini];
		for (int i = ini + 1; i < v.length; i++) {
			if (v[i] > maior) {
				maior = v[i];
			}
		}
		return maior;
	}

}
