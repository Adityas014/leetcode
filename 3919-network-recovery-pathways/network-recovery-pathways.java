import java.util.*;

class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        
        // Build adjacency list
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        // Collect all unique edge costs for binary search bounds
        Set<Integer> costSet = new HashSet<>();
        costSet.add(0);
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], cost = edge[2];
            graph[u].add(new int[]{v, cost});
            costSet.add(cost);
        }
        
        List<Integer> costs = new ArrayList<>(costSet);
        Collections.sort(costs);
        
        int lo = 0, hi = costs.size() - 1;
        int ans = -1;
        
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int minCost = costs.get(mid);
            
            if (canReach(n, graph, online, k, minCost)) {
                ans = minCost;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        
        return ans;
    }
    
    private boolean canReach(int n, List<int[]>[] graph, boolean[] online,
                              long k, int minCost) {
        // Dijkstra: shortest path using only edges with cost >= minCost
        // and only through online intermediate nodes
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        
        PriorityQueue<long[]> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a[1], b[1])
        );
        pq.offer(new long[]{0, 0});
        
        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            int u = (int) curr[0];
            long d = curr[1];
            
            if (d != dist[u]) continue;
            if (u == n - 1) return dist[n - 1] <= k;
            
            for (int[] edge : graph[u]) {
                int v = edge[0], cost = edge[1];
                
                // Skip edges below our minimum threshold
                if (cost < minCost) continue;
                
                // Skip offline intermediate nodes
                if (v != n - 1 && !online[v]) continue;
                
                long newDist = d + cost;
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.offer(new long[]{v, newDist});
                }
            }
        }
        
        return dist[n - 1] <= k;
    }
}