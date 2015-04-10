/*
 * Programming Question - Week 2, April 2015.
 * 
 * In this programming problem and the next you'll code up the clustering algorithm 
 * from lecture for computing a max-spacing k-clustering. Download the text file here.
 * This file describes a distance function (equivalently, a complete graph with edge
 * costs). It has the following format:
 *
 * [number_of_nodes]
 * [edge 1 node 1] [edge 1 node 2] [edge 1 cost]
 * [edge 2 node 1] [edge 2 node 2] [edge 2 cost]
 * ...
 * 
 * There is one edge (i,j) for each choice of 1≤i<j≤n, where n is the number of nodes.
 * For example, the third line of the file is "1 3 5250", indicating that the distance
 * between nodes 1 and 3 (equivalently, the cost of the edge (1,3)) is 5250. You can 
 * assume that distances are positive, but you should NOT assume that they are distinct.
 * 
 * Your task in this problem is to run the clustering algorithm from lecture on this 
 * data set, where the target number k of clusters is set to 4. What is the maximum 
 * spacing of a 4-clustering?
 * 
 * ADVICE: If you're not getting the correct answer, try debugging your algorithm using
 * some small test cases. And then post them to the discussion forum!
 */

package com.king.algoanalysisII;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class Distance implements Comparable<Distance> {
	int point1;
	int point2;
	int dist;

	public Distance(int point1, int point2, int dist) {
		this.point1 = point1;
		this.point2 = point2;
		this.dist = dist;
	}

	@Override
	public int compareTo(Distance o) {
		int res;
		if (this.dist < o.dist) {
			res = -1;
		} else if (this.dist > o.dist) {
			res = 1;
		} else
			res = 0;
		return res;
	}

	@Override
	public String toString() {
		return String.valueOf(point1) + "->" + String.valueOf(point2) + " : "
				+ String.valueOf(dist);
	}
}

public class PA2_Q1 {
	int N;
	ArrayList<Distance> dists;
	WeightedQuickUnionUF uf;

	public void readDataFromFile(String fileName) throws FileNotFoundException {
		Scanner scn = new Scanner(new File(fileName));
		N = scn.nextInt();
		dists = new ArrayList<Distance>();
		while (scn.hasNextLine() && scn.hasNextInt()) {
			int x = scn.nextInt() - 1;
			int y = scn.nextInt() - 1;
			int d = scn.nextInt();
			dists.add(new Distance(x, y, d));
		}
		scn.close();
		uf = new WeightedQuickUnionUF(N);
	}

	public int kClustering(int k) {
		Collections.sort(dists);
		int i = 0;
		while (uf.count() > k) {
			Distance d = dists.get(i++);
			uf.union(d.point1, d.point2);
		}
		int max = Integer.MAX_VALUE;
		for (Distance d : dists) {
			if (!uf.connected(d.point1, d.point2))
				max = Math.min(max, d.dist);
		}
		return max;
	}

	public static void main(String[] args) {
		PA2_Q1 sol = new PA2_Q1();
		try {
			sol.readDataFromFile("resources/clustering1.txt");
			System.out.println(sol.kClustering(4));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
