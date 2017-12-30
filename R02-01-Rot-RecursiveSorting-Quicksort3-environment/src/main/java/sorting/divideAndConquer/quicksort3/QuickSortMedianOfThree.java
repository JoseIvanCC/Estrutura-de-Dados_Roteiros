package sorting.divideAndConquer.quicksort3;

import sorting.AbstractSorting;
import util.Util;

/**
 * A classe QuickSortMedianOfThree representa uma variação do QuickSort que
 * funciona de forma ligeiramente diferente. Relembre que quando o pivô
 * escolhido divide o array aproximadamente na metade, o QuickSort tem um
 * desempenho perto do ótimo. Para aproximar a entrada do caso ótimo, diversas
 * abordagens podem ser utilizadas. Uma delas é usar a mediana de 3 para achar o
 * pivô. Essa técnica consiste no seguinte: 1. Comparar o elemento mais a
 * esquerda, o central e o mais a direita do intervalo. 2. Ordenar os elemento,
 * tal que: A[left] < A[center] < A[right]. 3. Adotar o A[center] como pivô. 4.
 * Colocar o pivô na penúltima posição A[right-1]. 5. Aplicar o particionamento
 * considerando o vetor menor, de A[left+1] até A[right-1]. 6. Aplicar o
 * algoritmo na metade a esquerda e na metade a direita do pivô.
 */
public class QuickSortMedianOfThree<T extends Comparable<T>> extends AbstractSorting<T> {

	public void sort(T[] array, int leftIndex, int rightIndex) {
		
		if (leftIndex < rightIndex) {
			int centro = mediana(array, leftIndex, rightIndex);
			Util.swap(array, centro, rightIndex - 1);

			int pos_piv = partition(array, leftIndex, rightIndex);
			sort(array, leftIndex, pos_piv - 1);
			sort(array, pos_piv + 1, rightIndex);
		}
	}

	private int partition(T[] array, int leftIndex, int rightIndex) {
		T pivot = array[leftIndex];
		int i = leftIndex + 1;
		int j = rightIndex;

		while (i <= j) {
			if (pivot.compareTo(array[i]) == 1) {
				i++;
			} else if (pivot.compareTo(array[j]) == -1) {
				j--;
			} else {
				Util.swap(array, i, j);
				i++;
				j--;
			}
		}
		array[leftIndex] = array[j];
		array[j] = pivot;
		return j;

	}


	private int mediana(T[] array, int leftIndex, int rightIndex) {
		int centro = leftIndex + (rightIndex - leftIndex) / 2;

		if (array[leftIndex].compareTo(array[centro]) == -1 && array[centro].compareTo(array[rightIndex]) == 1) {
			Util.swap(array, centro, rightIndex);
			
		} else if (array[leftIndex].compareTo(array[centro]) == 1 && array[leftIndex].compareTo(array[rightIndex]) == -1) {
			Util.swap(array, leftIndex, centro);
			
		} else if (array[leftIndex].compareTo(array[centro]) == -1 && array[leftIndex].compareTo(array[rightIndex]) == 1) {
			Util.swap(array, leftIndex, centro);
			Util.swap(array, leftIndex, rightIndex);
			
		} else if (array[leftIndex].compareTo(array[centro]) == 1 && array[leftIndex].compareTo(array[rightIndex]) == 1
				&& array[centro].compareTo(array[rightIndex]) == -1) {
			Util.swap(array, leftIndex, centro);
			Util.swap(array, centro, rightIndex);
			
		} else if (array[leftIndex].compareTo(array[centro]) == 1 && array[leftIndex].compareTo(array[rightIndex]) == 1
				&& array[centro].compareTo(array[rightIndex]) == 1) {
			Util.swap(array, leftIndex, centro);
			Util.swap(array, centro, rightIndex);
		}
		
		return centro;

	}

}
