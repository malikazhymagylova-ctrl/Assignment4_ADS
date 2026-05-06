import java.util.*;

    class GraphAlgorithms {

    // Task 3
    private Map<String, List<String>> graph;

    // Task 5
    private Map<String, Map<String, Integer>> roadMap;

    public GraphAlgorithms() {

        // Task 3 graph
        graph = new HashMap<>();

        graph.put("A", Arrays.asList("C", "B", "D"));
        graph.put("B", Arrays.asList("A", "C", "E", "G"));
        graph.put("C", Arrays.asList("A", "B", "D"));
        graph.put("D", Arrays.asList("C", "A"));
        graph.put("E", Arrays.asList("G", "F", "B"));
        graph.put("F", Arrays.asList("G", "E"));
        graph.put("G", Arrays.asList("F", "B"));

        // Task 5 road map
        roadMap = new HashMap<>();

        addRoad("Glasgow", "Stirling", 50);
        addRoad("Glasgow", "Edinburgh", 70);
        addRoad("Stirling", "Perth", 40);
        addRoad("Stirling", "Edinburgh", 50);
        addRoad("Perth", "Dundee", 60);
        addRoad("Perth", "Edinburgh", 100);
    }

    // Task 5
    public void addRoad(String firstCity,
                        String secondCity,
                        int distance) {

        roadMap.putIfAbsent(firstCity, new HashMap<>());
        roadMap.putIfAbsent(secondCity, new HashMap<>());

        roadMap.get(firstCity).put(secondCity, distance);
        roadMap.get(secondCity).put(firstCity, distance);
    }

    // Task 3 DFS
    public void performDepthFirstSearch(String startVertex) {

        Set<String> visitedVertices = new HashSet<>();

        System.out.println("\nDFS Order:");

        exploreDepth(startVertex, visitedVertices);
    }

    public void exploreDepth(String currentVertex,
                             Set<String> visitedVertices) {

        visitedVertices.add(currentVertex);

        System.out.print(currentVertex + " ");

        for (String neighbor : graph.get(currentVertex)) {

            if (!visitedVertices.contains(neighbor)) {

                exploreDepth(neighbor, visitedVertices);
            }
        }
    }

    // Task 3 BFS
    public void performBreadthFirstSearch(String startVertex) {

        Set<String> visitedVertices = new HashSet<>();

        Queue<String> waitingQueue = new LinkedList<>();

        visitedVertices.add(startVertex);

        waitingQueue.add(startVertex);

        System.out.println("\nBFS Order:");

        while (!waitingQueue.isEmpty()) {

            String currentVertex = waitingQueue.poll();

            System.out.print(currentVertex + " ");

            for (String neighbor : graph.get(currentVertex)) {

                if (!visitedVertices.contains(neighbor)) {

                    visitedVertices.add(neighbor);

                    waitingQueue.add(neighbor);
                }
            }
        }
    }

    // Task 5 Dijkstra algorithm
    public void findShortestRoad(String startCity,
                                 String destinationCity) {

        Map<String, Integer> minimumDistance = new HashMap<>();

        Map<String, String> previousCity = new HashMap<>();

        PriorityQueue<String> cityQueue =
                new PriorityQueue<>(
                        Comparator.comparingInt(minimumDistance::get)
                );

        for (String city : roadMap.keySet()) {

            minimumDistance.put(city, Integer.MAX_VALUE);
        }

        minimumDistance.put(startCity, 0);

        cityQueue.add(startCity);

        while (!cityQueue.isEmpty()) {

            String currentCity = cityQueue.poll();

            for (String neighbor
                    : roadMap.get(currentCity).keySet()) {

                int newDistance =
                        minimumDistance.get(currentCity)
                                + roadMap.get(currentCity).get(neighbor);

                if (newDistance
                        < minimumDistance.get(neighbor)) {

                    minimumDistance.put(neighbor, newDistance);

                    previousCity.put(neighbor, currentCity);

                    cityQueue.add(neighbor);
                }
            }
        }

        List<String> shortestPath = new ArrayList<>();

        String currentCity = destinationCity;

        while (currentCity != null) {

            shortestPath.add(currentCity);

            currentCity = previousCity.get(currentCity);
        }

        Collections.reverse(shortestPath);

        System.out.println("\nShortest path:");

        System.out.println(
                String.join(" -> ", shortestPath)
        );

        System.out.println(
                "Total distance: "
                        + minimumDistance.get(destinationCity)
        );
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        GraphAlgorithms algorithms =
                new GraphAlgorithms();

        // Task 3
        System.out.print(
                "Enter start vertex for DFS and BFS: "
        );

        String startVertex = scanner.nextLine();

        algorithms.performDepthFirstSearch(startVertex);

        algorithms.performBreadthFirstSearch(startVertex);

        // Task 5
        System.out.print(
                "\nEnter start city: "
        );

        String startCity = scanner.nextLine();

        System.out.print(
                "Enter destination city: "
        );

        String destinationCity = scanner.nextLine();

        algorithms.findShortestRoad(
                startCity,
                destinationCity
        );
    }
}