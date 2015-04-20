package com.king.algoanalysisII;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PA3_Q2 {
	int[] values;
	int[] weights;
	int n;
	int sack;
	private static HashMap<String, Integer> cache = new HashMap<String, Integer>();

	public int knapSackTopDown() {
		knapSackTopDownHelper(sack, n);
		return cache.get("i" + n + ":" + sack);
	}

	private int getCachedResult(int sack, int n) {
		String key = "i" + n + ":" + sack;
		return cache.containsKey(key) ? cache.get(key) : -1;
	}

	private int knapSackTopDownHelper(int sack, int n) {
		if (n == 0)
			return 0;
		if (getCachedResult(sack, n) != -1)
			return getCachedResult(sack, n);
		int result;
		if (weights[n - 1] > sack)
			result = knapSackTopDownHelper(sack, n - 1);
		else
			result = Math.max(
					knapSackTopDownHelper(sack, n - 1),
					values[n - 1]
							+ knapSackTopDownHelper(sack
									- weights[n - 1], n - 1));
		cache.put("i" + n + ":" + sack, result);
		return result;
	}

	public void readJobsFromFile(String fileName) {
		try {
			Scanner scn = new Scanner(new File(fileName));
			sack = scn.nextInt();
			n = scn.nextInt();
			values = new int[n];
			weights = new int[n];
//			cache = new HashMap<String, Integer>(n);
			for (int i = 0; i < n; i++) {
				if (scn.hasNext())
					values[i] = scn.nextInt();
				if (scn.hasNext())
					weights[i] = scn.nextInt();
			}
			scn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int solve(String fileName) {
		readJobsFromFile(fileName);
		return knapSackTopDown();
	}

	public static void main(String[] args) {
		PA3_Q2 sol = new PA3_Q2();
		System.out.println(sol.solve("resources/knapsack_big.txt"));
	}
}
