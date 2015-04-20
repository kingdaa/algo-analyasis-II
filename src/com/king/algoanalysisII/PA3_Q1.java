package com.king.algoanalysisII;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PA3_Q1 {
	int[] values;
	int[] weights;
	int n;
	int sack;

	public int knapSackTopDown() {
		int[][] dp = new int[n + 1][sack + 1];
		// Initialize dp array
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= sack; j++) {
				dp[i][j] = -1;
			}
		}
		knapSackTopDownHelper(dp, sack, n);
		return dp[n][sack];
	}

	private int knapSackTopDownHelper(int[][] dp, int sack, int n) {
		if (n == 0)
			return 0;
		if (dp[n][sack] != -1)
			return dp[n][sack];
		int result;
		if (weights[n - 1] > sack)
			result = knapSackTopDownHelper(dp, sack, n - 1);
		else
			result = Math.max(
					knapSackTopDownHelper(dp, sack, n - 1),
					values[n - 1]
							+ knapSackTopDownHelper(dp, sack - weights[n - 1],
									n - 1));
		dp[n][sack] = result;
		return result;
	}

	public void readJobsFromFile(String fileName) {
		try {
			Scanner scn = new Scanner(new File(fileName));
			sack = scn.nextInt();
			n = scn.nextInt();
			values = new int[n];
			weights = new int[n];
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
		PA3_Q1 sol = new PA3_Q1();
		System.out.println(sol.solve("resources/knapsack1.txt"));
	}
}
