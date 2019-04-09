package com.huawei.exe;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * //使用方法： Dijkstra dijkstra = new Dijkstra(graph); List[][] list =
 * dijkstra.getLists(); //list即我们需要的最短路径
 * 
 * 注意：最短路径数组中有空集 1、i == j 2、i 与j之间没有路
 */
public class Dijkstra {
	private static int INF = Integer.MAX_VALUE;
	private int[][] dist;
	private int[][] path;
	private int[][] graph;
	private List<Integer> result = new ArrayList<Integer>();
	private List[][] lists;

	public Dijkstra(int[][] graph) {
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (graph[i][j] == 0)
					graph[i][j] = INF;
			}
		}
		this.path = new int[graph.length][graph.length];
		this.dist = new int[graph.length][graph.length];
		this.graph = graph;
		this.lists = new List[graph.length][graph.length];
	}

	public void findCheapestPath(int[][] matrix) {

		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix.length; j++) {
				// System.out.println(result.toString());
				// if(matrix[i][j]==0)
				// matrix[i][j]=INF;
				if (i == j)
					continue;
				result.clear();
				floyd(matrix);
				result.add(i);
				findPath(i, j);
				result.add(j);
				List<Integer> result1 = new ArrayList<>(result);
				// System.out.println("["+i+","+j+"]");
				// System.out.println(result1.toString().equals("["+i+", "+j+"]"));
				if (graph[i][j] != INF
						|| result1.toString().equals("[" + i + ", " + j + "]") == false) {
					// System.out.println(result1.toString());
					lists[i][j] = result1;
				}
				// lists[i][j] = result1;

			}
		}

	}

	public void findPath(int i, int j) {
		int k = path[i][j];
		if (k == -1)
			return;
		findPath(i, k);
		result.add(k);
		findPath(k, j);
	}

	public void floyd(int[][] matrix) {
		int size = matrix.length;
		// initialize dist and path
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				path[i][j] = -1;
				dist[i][j] = matrix[i][j];
			}
		}
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (dist[i][k] != INF && dist[k][j] != INF
							&& dist[i][k] + dist[k][j] < dist[i][j]) {// dist[i][k]+dist[k][j]>dist[i][j]-->longestPath
						dist[i][j] = dist[i][k] + dist[k][j];
						path[i][j] = k;
					}
				}
			}
		}
	}

	public List[][] getLists() {
		findCheapestPath(graph);
		return lists;
	}

}
