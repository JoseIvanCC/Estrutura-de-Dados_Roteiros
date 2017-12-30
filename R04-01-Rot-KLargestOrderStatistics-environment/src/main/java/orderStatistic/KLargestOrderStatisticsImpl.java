package orderStatistic;

import java.util.Arrays;

import util.Util;

/**
 * Uma implementacao da interface KLargest que usa estatisticas de ordem para
 * retornar um array com os k maiores elementos de um conjunto de dados/array.
 * 
 * A k-esima estatistica de ordem de um conjunto de dados eh o k-esimo menor
 * elemento desse conjunto. Por exemplo, considere o array [4,8,6,9,12,1]. A 3a
 * estatistica de ordem eh 6, a 6a estatistica de ordem eh 12.
 * 
 * Note que, para selecionar os k maiores elementos, pode-se pegar todas as
 * estatisticas de ordem maiores que k.
 * 
 * Requisitos do algoritmo: - DEVE ser in-place. Voce pode modificar o array
 * original - Voce DEVE usar alguma ideia de algoritmo de ordenacao visto em
 * sala para calcular estatisticas de ordem.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class KLargestOrderStatisticsImpl<T extends Comparable<T>> implements KLargest<T> {
	public static void main(String[] args) {
		Integer[] array = new Integer[] { };
		KLargestOrderStatisticsImpl<Integer> largest = new KLargestOrderStatisticsImpl<Integer>();
		System.out.println(Arrays.toString(largest.getKLargest(array, 2)));
	}

	@Override
	public T[] getKLargest(T[] array, int k) {
		
		if (array == null || array.length == 0 || k <= 0 || k > array.length) {
			return null;
		}
		
		T[] maioresEstatisticas = (T[]) new Comparable[k];
		
		int i = array.length;
		while (k > 0) {
			maioresEstatisticas[k-1] = orderStatistics(array, i);
			k--;
			i--;
		}
		return maioresEstatisticas;
	}

	/**
	 * Metodo que retorna a k-esima estatistica de ordem de um array, usando a
	 * ideia de algum algoritmo de ordenacao visto em sala. Caso nao exista a
	 * k-esima estatistica de ordem, entao retorna null.
	 * 
	 * Obs: o valor de k deve ser entre 1 e N.
	 * 
	 * @param array
	 * @param k
	 * @return
	 */
	public T orderStatistics(T[] array, int k) {
		// validacao
		if (k <= 0 || k > array.length) {
			return null;
		}
		
		
		// Ordenando o array para achar a estatistica de ordem
		ordenaArray(array);
		
		//Estatistica de ordem
		return array[k-1];

	}
	
	//Ordenacao usando o Selection Sort
	private void ordenaArray(T[] array) {
		for (int i = 0; i < array.length -1; i++) {
			int ind_Menor = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[j].compareTo(array[ind_Menor]) < 0) {
					ind_Menor = j;
				}
			}
			Util.swap(array, ind_Menor, i);
		}

	}
}
