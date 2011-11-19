package book;

import java.util.Arrays;

public class QuickUW {
	
	public static void brokenVersion() {
		System.out.println("Broken");
		int N = 10;
		int id[] = new int[N], sz[] = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
		for (In.init(); !In.empty();) {
			System.out.println(Arrays.toString(id));
			System.out.println(Arrays.toString(sz));
			int i, j, p = In.getInt(), q = In.getInt();
			for (i = p; i != id[i]; i = id[i])
				;
			for (j = q; j != id[j]; j = id[j])
				;
			if (i == j)
				continue;
			if (sz[i] < sz[j]) {
				id[i] = j;
				sz[j] += sz[i];
			} else {
				id[j] = i;
				sz[i] += sz[j];
			}
			System.out.println(" " + p + " " + q);
		}		
	}
	
	public static void fixedVersion() {
		System.out.println("Fixed");
		int N = 10;
		int id[] = new int[N], sz[] = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
		for (In.init(); !In.empty();) {
			System.out.println(Arrays.toString(id));
			System.out.println(Arrays.toString(sz));
			int i, j, p = In.getInt(), q = In.getInt();
			for (i = p; i != id[i]; i = id[i])
				;
			for (j = q; j != id[j]; j = id[j])
				;
			if (i == j)
				continue;
			if (sz[i] < sz[j]) {
				id[j] = i;
				sz[j] += sz[i];
			} else {
				id[i] = j;
				sz[i] += sz[j];
			}
			System.out.println(" " + p + " " + q);
		}		
	}
	
	public static void main(String[] args) {
		brokenVersion();
		fixedVersion();
	}
	
	private static class In {
		
		static int[] values = new int[0];
		
		static void init() {
			values = new int[] {3, 4, 4, 9, 8, 0, 2, 3, 5, 6, 2, 9, 5, 9, 7, 3, 4, 8, 5, 6, 0, 2, 6, 1, 5, 8};
		}
		
		static boolean empty() {
			return values.length == 0;
		}
		
		static int getInt() {
			int value = values[0];
			int[] updatedValues = new int[values.length - 1];
			System.arraycopy(values, 1, updatedValues, 0, updatedValues.length);
			values = updatedValues;
			return value;
		}
	}
}
