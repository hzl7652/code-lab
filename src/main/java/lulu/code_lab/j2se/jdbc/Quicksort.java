package lulu.code_lab.j2se.jdbc;

import java.util.Arrays;

public class Quicksort {

	public int partition(int[] array, int begin, int end) {
		int pivotIndex = array.length / 2;
		int pivot = array[pivotIndex];
		for (int i = pivotIndex = begin; i < end; ++i) {
			if (array[i] >= pivot) {
				swap(array, pivotIndex++, i);
			}
		}
		return pivotIndex;
	}

	public void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public void qsort(int[] array, int begin, int end) {
		if (end > begin) {
			int index = partition(array, begin, end);
			qsort(array, begin, index - 1);
			qsort(array, index + 1, end);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int[] array = { 85, 24, 63, 45, 17, 31, 96, 50 };
		Quicksort qs = new Quicksort();
		qs.qsort(array, 0, array.length - 1);
		System.out.println(Arrays.toString(array));
	}

}
