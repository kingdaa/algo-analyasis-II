/*
 * Programming Question - Week 2, April 2015.
 * 
 * In this question your task is again to run the clustering algorithm from lecture,
 * but on a MUCH bigger graph. So big, in fact, that the distances (i.e., edge costs)
 * are only defined implicitly, rather than being provided as an explicit list.
 * 
 * The data set is here. The format is:
 * 
 * [# of nodes] [# of bits for each node's label]
 * [first bit of node 1] ... [last bit of node 1]
 * [first bit of node 2] ... [last bit of node 2]
 * ...
 * 
 * For example, the third line of the file "0 1 1 0 0 1 1 0 0 1 0 1 1 1 1 1 1 0 1 0 1 1 0 1"
 * denotes the 24 bits associated with node #2.
 * 
 * The distance between two nodes u and v in this problem is defined as the Hamming 
 * distance--- the number of differing bits --- between the two nodes' labels. For example,
 * the Hamming distance between the 24-bit label of node #2 above and the label 
 * "0 1 0 0 0 1 0 0 0 1 0 1 1 1 1 1 1 0 1 0 0 1 0 1" is 3 (since they differ in the 3rd, 7th,
 * and 21st bits).
 * 
 * The question is: what is the largest value of k such that there is a k-clustering with 
 * spacing at least 3? That is, how many clusters are needed to ensure that no pair of nodes 
 * with all but 2 bits in common get split into different clusters?
 * 
 * NOTE: The graph implicitly defined by the data file is so big that you probably can't 
 * write it out explicitly, let alone sort the edges by cost. So you will have to be a little
 * creative to complete this part of the question. For example, is there some way you can 
 * identify the smallest distances without explicitly looking at every pair of nodes?
 */

package com.king.algoanalysisII;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PA2_Q2 {
	int N;
	int bits;
	boolean[][] points;
	WeightedQuickUnionUF uf;

	public int getClusteringNumForGivenSpacing(int spacing) {
		int i, j;
		for (i = 0; i < N - 1; i++) {
			System.out.println(i);
			for (j = i + 1; j < N; j++) {
				if (pointDist(points[i], points[j]) < spacing) {
					uf.union(i, j);
				}
			}
		}
		return uf.count();
	}

	private int pointDist(boolean[] point1, boolean[] point2) {
		int count = 0;
		for (int i = 0; i < bits; i++) {
			if (point1[i] != point2[i])
				count++;
		}
		return count;
	}

	public void readDataFromFile(String fileName) throws FileNotFoundException {
		Scanner scn = new Scanner(new File(fileName));
		N = scn.nextInt();
		bits = scn.nextInt();
		points = new boolean[N][bits];
		int i = 0, j = 0;
		scn.nextLine();
		for (; i < N; i++) {
			// while (scn.hasNextLine() && scn.hasNextInt()) {
			// String nl = scn.nextLine();
			String nl = scn.nextLine().replaceAll("\\s+", "");
			// System.out.println(nl);
			for (j = 0; j < bits; j++) {
				points[i][j] = nl.charAt(j) == '1' ? true : false;
			}
//			i++;
		}
		scn.close();
		uf = new WeightedQuickUnionUF(N);
	}

	public static void main(String[] args) {
		PA2_Q2 sol = new PA2_Q2();
		try {
			sol.readDataFromFile("resources/clustering_big.txt");
			System.out.println(sol.getClusteringNumForGivenSpacing(3));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
