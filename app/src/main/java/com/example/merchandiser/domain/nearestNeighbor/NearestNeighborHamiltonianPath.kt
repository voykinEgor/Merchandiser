package com.example.merchandiser.domain.nearestNeighbor

import com.yandex.mapkit.geometry.Point

data class Edge(val destination:Int, val weight:Double)

class NearestNeighborHamiltonianPath {

    companion object{

        fun buildGraph(points: List<Point>): Map<Int, List<Edge>> {
            val graph = mutableMapOf<Int, MutableList<Edge>>()

            for (i in points.indices) {
                graph[i] = mutableListOf()
                for (j in points.indices) {
                    if (i != j) {
                        val distance = HaversineDistance.haversineDistance(points[i], points[j])
                        graph[i]?.add(Edge(j, distance))
                    }
                }
            }

            return graph
        }

        fun nearestNeighborHamiltonianPath(graph: Map<Int, List<Edge>>, points: List<Point>, start: Point): List<Int> {
            val n = graph.size
            val visited = BooleanArray(n) { false }
            val path = mutableListOf<Int>()
            val startIndex = points.indexOfFirst { it.latitude == start.latitude && it.longitude == start.longitude }
            var currentNode = startIndex

            path.add(currentNode)
            visited[currentNode] = true

            for (i in 1 until n) {
                var minDistance = Double.MAX_VALUE
                var nextNode = -1

                for (edge in graph[currentNode] ?: emptyList()) {
                    if (!visited[edge.destination] && edge.weight < minDistance) {
                        minDistance = edge.weight
                        nextNode = edge.destination
                    }
                }

                if (nextNode == -1) {
                    error("Граф не связный или нет доступных путей")
                }

                path.add(nextNode)
                visited[nextNode] = true
                currentNode = nextNode
            }

            return path
        }
    }


}