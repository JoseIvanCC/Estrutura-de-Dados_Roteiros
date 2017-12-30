package orderStatistic;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class test {

	private Integer[] vetorTamPar;
	private Integer[] vetor;
	private Integer[] vetorTamImpar;
	private Integer[] vetorVazio;
	private Integer[] vetorValoresRepetidos;
	private Integer[] vetorValoresIguais;

	public KLargest<Integer> implementation;

	// // MÉTODOS AUXILIARES DA INICIALIZAÇÃO
	/**
	 * Método que inicializa a implementação a ser testada com a implementação
	 * do aluno
	 */
	private void getImplementation() {
		// TODO O aluno deve instanciar sua implementação abaixo ao invés de
		// null
		this.implementation = new KLargestOrderStatisticsImpl();
		//Assert.fail("Implementation not provided");
	}


	// MÉTODOS DE TESTE
@	Test
	public void test() {
		Integer[] resultado = new Integer[] {8, 9, 12};
		Integer[] array = {4, 8, 1, 12, 0, 9};
		Assert.assertArrayEquals(resultado, implementation.getKLargest(array, 3));
	}

	@Test
	public void testVazio() {
		Integer[] array = {};
		Assert.assertArrayEquals(null, implementation.getKLargest(array, 2));
	}

	@Test
	public void testTamPar() {
		Integer[] resultado = new Integer[] {29, 30, 31};
		Integer[] array = {4, 2, 6, 29, 8, 30, 5, 31};
		Assert.assertArrayEquals(resultado, implementation.getKLargest(array, 3));
	}

}
