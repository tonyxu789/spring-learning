package com.xhc.spring.testbasic.labuladong;

import java.util.ArrayList;
import java.util.List;

/**
 * 有向加权图（邻接矩阵实现）
 */
public class WeightedDigraph2 {

    // 邻接矩阵：matrix[from][to] 存储从节点 from 到节点 to 边的权重
    // 0 表示没有连接
    public int[][] matrix;
    // 构造一个邻接矩阵
    public WeightedDigraph2(int n) {
        matrix = new int[n][n];
    }

    // 增，添加一条带权重的有向边，复杂度 O(1)
    public void addEdge(int from, int to, int weight) {
        matrix[from][to] = weight;
    }

    // 删，删除一条有向边，复杂度为 O(1)
    public void removeEdge(int from, int to) {
        matrix[from][to] = 0; // 置为空，就可以看作是删除了
    }

    // 判断二者是否是否是相邻的，复杂度为 O(1)
    public boolean hasEdge(int from, int to) {
        return matrix[from][to] != 0;
    }

    // 查，返回一条边的权重，复杂度 O(1)
    public int weight(int from, int to) {
        return matrix[from][to];
    }

    // 查，返回某个节点的所有邻居节点，复杂度 O(V)
    public List<Edge> neighbors(int v) {
        List<Edge> res = new ArrayList<>();
        for (int i = 0; i < matrix[v].length; i++) {
            // i 是目标节点的编号
            if (matrix[v][i] != 0) {
                res.add(new Edge(i, matrix[v][i]));
            }
        }
        return res;
    }


    public static void main(String[] args) {
        WeightedDigraph2 graph = new WeightedDigraph2(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 0, 3);
        graph.addEdge(2, 1, 4);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println(graph.matrix[i][j]);
            }
        }

        System.out.println(graph.hasEdge(0, 1)); // true
        System.out.println(graph.hasEdge(1, 0)); // false

        graph.neighbors(2).forEach(edge -> {
            System.out.println(2 + " -> " + edge.to + ", wight: " + edge.weight);
        });
        // 2 -> 0, wight: 3
        // 2 -> 1, wight: 4
    }

}
