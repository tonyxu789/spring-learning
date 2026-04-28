package com.xhc.spring.testbasic.labuladong;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 有向加权图（邻接表实现）
 */
public class WeightedDigraph1 implements Graph{

    // 邻接表，存储所有的目标节点和对应的权重
    // 数组的每个元素是一个List<Edge>
    // 索引代表图的顶点，每个顶点有一个邻接表（即该顶点所连接的边的列表）
    private List<Edge>[] graph;

    public WeightedDigraph1(int n) {
        graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
    }

    // 增，添加一条带权重的有向边，复杂度 O(1)
    public void addEdge(int from, int to, int weight) {
        graph[from].add(new Edge(to, weight));
    }

    // 删，删除一条有向边，复杂度 O(V)
    public void removeEdge(int from, int to) {
        for (int i = 0; i < graph[from].size(); i++) {
            // graph[from].get(i) == 一条边
            if (graph[from].get(i).to == to) {
                graph[from].remove(i);
                break;
            }
        }
    }

    // 查，判断两个节点是否相邻，复杂度 O(V)
    public boolean hasEdge(int from, int to) {
        for (Edge e : graph[from]) {
            if (e.to == to) {
                return true;
            }
        }
        return false;
    }

    // 查，返回一条边的权重，复杂度为 O(V)
    public int weight(int from, int to) {
        for (Edge e : graph[from]) {
            if (e.to == to) {
                return e.weight;
            }
        }
        throw new IllegalArgumentException("No such edge");
    }

    // 返回某个节点 v 所有的邻接点，复杂度为 O(V)
    public List<Edge> neighbors(int v) {
        return graph[v];
    }

    // 返回节点数
    public int size() {
        return graph.length;
    }


    // DFS遍历所有节点
    public void traverse(Graph graph, int s, boolean[] visited) {
        // base case
        if (s < 0 || s >= graph.size()) {
            return;
        }
        if (visited[s]) {
            // 防止死循环
            return;
        }
        // 前序位置
        visited[s] = true;
        System.out.println("visit " + s);
        for (Edge e : graph.neighbors(s)) {
            traverse(graph, e.to, visited);
        }
        // 后序位置
    }

    // DFS从起点 s 开始遍历图的所有边
    public void traverseEdges(Graph graph, int s, boolean[][] visited) {
        // base case
        if (s < 0 || s >= graph.size()) {
            return;
        }
        for (Edge e : graph.neighbors(s)) {
            // 如果边已经被遍历过，则跳过
            if (visited[s][e.to]) {
                continue;
            }
            // 标记并访问边
            visited[s][e.to] = true;
            System.out.println("visit edge: " + s + " -> " + e.to);
            traverseEdges(graph, e.to, visited);
        }
    }


    // 遍历所有路径（onPath 数组）
    // 防止重复访问同一节点，但允许不同路径重复访问同一节点
    public void traverseAllPaths(Graph graph, int s, int t, List<List<Integer>> result, List<Integer> path, boolean[] onPath) {
        if (s < 0 || s >= graph.size()) {
            return;
        }
        // 如果当前节点已在路径中，说明检测到环，直接返回
        if (onPath[s]) {
            return;
        }

        onPath[s] = true;
        path.add(s);
        if (s == t) {
            // 找到一条路径
            result.add(new ArrayList<>(path));
        } else {
            // 继续遍历邻居
            for (Edge e : graph.neighbors(s)) {
                traverseAllPaths(graph, e.to, t, result, path, onPath);
            }
        }
        path.remove(path.size() - 1);
        onPath[s] = false;
    }


    // 图结构的 BFS 遍历，从节点 s 开始进行 BFS 不记录遍历步数
    public void traverseBFS1(Graph graph, int s) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        visited[s] = true;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            System.out.println("visit " + cur);
            for (Edge e : graph.neighbors(cur)) {
                if (!visited[e.to]) {
                    queue.offer(e.to);
                    visited[e.to] = true;
                }
            }
        }
    }

    // 图结构的 BFS 遍历，从节点 s 开始进行 BFS 记录遍历步数
    public void traverseBFS2(Graph graph, int s) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        visited[s] = true;
        int step = 0;
        while (!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                int cur = queue.poll();
                System.out.println("visit " + cur + ", step: " + step);
                for (Edge e : graph.neighbors(cur)) {
                    if (!visited[e.to]) {
                        queue.offer(e.to);
                        visited[e.to] = true;
                    }
                }
            }
            step ++;
        }
    }


    // 图结构的 BFS 遍历，从节点 s 开始进行 BFS，且记录路径的权重和
    // 每个节点自行维护 State 类，记录从 s 走来的权重和
    public static class State {
        // 当前节点 ID
        int node;
        // 从起点 s 到当前节点的权重和
        int weight;
        public State(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    public void traverseBFS3(Graph graph, int s) {
        boolean[] visited = new boolean[graph.size()];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(s, 0));
        visited[s] = true;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            int cur = state.node;
            int weight = state.weight;
            System.out.println("visit " + cur + ", weight: " + weight);
            for (Edge e : graph.neighbors(cur)) {
                if (!visited[e.to]) {
                    queue.offer(new State(e.to, weight + e.weight));
                    visited[e.to] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        WeightedDigraph1 graph = new WeightedDigraph1(5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 0, 3);
        graph.addEdge(2, 1, 4);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 5);
        graph.addEdge(4, 3, 6);
        graph.addEdge(4, 2, 7);
        graph.addEdge(4, 1, 8);

        System.out.println(graph.hasEdge(0, 1)); // true
        System.out.println(graph.hasEdge(1, 0)); // false

        graph.neighbors(2).forEach(edge -> {
            System.out.println(2 + " -> " + edge.to + ", wight: " + edge.weight);
        });
        // 2 -> 0, wight: 3
        // 2 -> 1, wight: 4

        System.out.println("----------");
        graph.traverse(graph, 0, new boolean[graph.size()]);

        System.out.println("----------");
        graph.traverseEdges(graph, 0, new boolean[graph.size()][graph.size()]);

        System.out.println("----------");
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        graph.traverseAllPaths(graph, 0, 4, result, path, new boolean[graph.size()]);
        System.out.println(result);

        System.out.println("----------");
        graph.traverseBFS3(graph, 0);


//        graph.removeEdge(0, 1);
//        System.out.println(graph.hasEdge(0, 1)); // false
    }

    // 有向无权图 在 addEdge 方法中，将 weight 置为 1 就可以了。
}
