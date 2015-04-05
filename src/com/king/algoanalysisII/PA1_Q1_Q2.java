package com.king.algoanalysisII;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/*
 * Programming Question - Week 1, September 2013.
 * 
 * In this programming problem and the next you'll code up the greedy algorithms 
 * from lecture for minimizing the weighted sum of completion times.. Download the
 * text file here. This file describes a set of jobs with positive and integral 
 * weights and lengths. It has the format
 * 
 * [number_of_jobs]
 * [job_1_weight] [job_1_length]
 * [job_2_weight] [job_2_length]
 * ...
 * 
 * For example, the third line of the file is "74 59", indicating that the second 
 * job has weight 74 and length 59. You should NOT assume that edge weights or lengths
 * are distinct.
 * 
 * Your task in this problem is to run the greedy algorithm that schedules jobs in
 * decreasing order of the difference (weight - length). Recall from lecture that 
 * this algorithm is not always optimal. IMPORTANT: if two jobs have equal difference
 * (weight - length), you should schedule the job with higher weight first. Beware:
 * if you break ties in a different way, you are likely to get the wrong answer. 
 * You should report the sum of weighted completion times of the resulting schedule
 * --- a positive integer --- in the box below.
 * 
 * ADVICE: If you get the wrong answer, try out some small test cases to debug your
 * algorithm (and post your test cases to the discussion forum)!
 */

class Job {
	private int weight;
	private int length;

	public Job(int weight, int length) {
		super();
		this.weight = weight;
		this.length = length;
	}

	public int getWeight() {
		return weight;
	}

	public int getLength() {
		return length;
	}

	public int getWeightLengthDiff() {
		return weight - length;
	}

	public float getRatio() {
		if (this.length == 0)
			throw new NumberFormatException();
		return (float) weight / length;
	}
}

class JCom1 implements Comparator<Job> {
	@Override
	public int compare(Job job1, Job job2) {
		int result = 0;
		if (job1.getWeight() - job1.getLength() == job2.getWeight()
				- job2.getLength()) {
			result = job1.getWeight() - job2.getWeight();
		} else {
			result = (job1.getWeight() - job1.getLength())
					- (job2.getWeight() - job2.getLength());
		}

		return -result;
	}
}

class JCom2 implements Comparator<Job> {
	@Override
	public int compare(Job job1, Job job2) {
		int result = 0;
		if (job1.getRatio() < job2.getRatio()) {
			result = 1;
		} else if (job1.getRatio() > job2.getRatio()) {
			result = -1;
		}
		return result;
	}
}

public class PA1_Q1_Q2 {
	ArrayList<Job> jobs;

	public void readJobsFromFile(String fileName) {
		try {
			Scanner scn = new Scanner(new File(fileName));
			int n = scn.nextInt();
			jobs = new ArrayList<Job>(n);
			for (int i = 0; i < n; i++) {
				jobs.add(new Job(scn.nextInt(), scn.nextInt()));
			}
			scn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public long q1Sol() {
		Collections.sort(jobs, new JCom1());
		long aggTime = 0;
		long aggWeightedTime = 0;
		for (Job job : jobs) {
			aggTime += job.getLength();
			aggWeightedTime += job.getWeight() * aggTime;
		}
		return aggWeightedTime;
	}

	public long q2Sol() {
		Collections.sort(jobs, new JCom2());
		long aggTime = 0;
		long aggWeightedTime = 0;
		for (Job job : jobs) {
			aggTime += job.getLength();
			aggWeightedTime += job.getWeight() * aggTime;
		}
		return aggWeightedTime;
	}

	public static void main(String[] args) {
		PA1_Q1_Q2 sol = new PA1_Q1_Q2();
		sol.readJobsFromFile("resources/jobs.txt");
		System.out.println(sol.q1Sol());
		System.out.println(sol.q2Sol());
	}
}