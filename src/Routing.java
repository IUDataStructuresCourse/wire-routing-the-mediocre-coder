import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class Routing {

    static ArrayList<Coord> checkedVertices = new ArrayList<>();

    static ArrayList<ArrayList<Coord>>
    findPaths(Board board, ArrayList<Coord[]> points) {
        ArrayList<ArrayList<Coord>> output = new ArrayList<ArrayList<Coord>>();

        Map<Coord, Integer> originalGrid = new HashMap<Coord, Integer>();
        originalGrid.putAll(board.getGrid());
        Board SDboard = new Board(originalGrid, board.getHeight(), board.getWidth());

        output = runBFS(board, points);

        if (output.size() != points.size()) {
            return null;
        }

        return output;
    }

    /*
        markPaths method is what calls BFS for each individual pair of points
        It will take a Board as a parameter and a list of Coord Pairs
        For each Coordinate pair it will run the BFS method with the first(start) and other(finish) Coord

        BFS produces a list of paths for each pair
        For each path given:
            Find the path with the ending as the finish Node
            For each Coord in that path:
                If the coord is just a 0 on the board, change its value to be the same as the start value
            Finally add that path to the output and return it
     */

    static ArrayList<ArrayList<Coord>> markPaths(Board board, ArrayList<Coord[]> points) {
        ArrayList<ArrayList<Coord>> output = new ArrayList<ArrayList<Coord>>();
        for (Coord[] route : points) {
            Coord start = route[0];
            Coord finish = route[1];
            ArrayList<ArrayList<Coord>> paths = bfs(board, start, finish);
            for (ArrayList<Coord> p : paths) {
                if (p.get(p.size() - 1).equals(finish)) {
                    //mark this path
                    for (Coord c : p) {
                        if (c.equals(start) || c.equals(finish)) {
                        } else {
                            board.putValue(c, board.getValue(start));
                        }
                    }
                    //add to output
                    output.add(p);
                }
                ;
            }
        }
        return output;
    }

    /*
        runBFS is a null checker for markPaths
        It checks to see if there is an output at all.
        Instead of returning an empty array in markPaths, null will be returned
     */

    static ArrayList<ArrayList<Coord>> runBFS(Board board, ArrayList<Coord[]> points) {
        ArrayList<ArrayList<Coord>> output = new ArrayList<ArrayList<Coord>>();

        output = markPaths(board, points);
        //board.pretty_print_grid();
        if (output.size() == 0) {
            return null;
        }
        return output;
    }


    /*
        Basic BFS search method
        This method runs the basic breadth first search on the board given
        There will be a start Coord where the search begins from
        There will also be a finish Coord where the search should end on
        The method returns an ArrayList of ArrayLists of Coords
        Each ArrayList within the returned ArrayList represents additions to the BFS
        This Method is the base BFS to be called later in class
     */

    static ArrayList<ArrayList<Coord>> bfs(Board board, Coord start, Coord finish) {
        // BFS uses Queue data structure
        HashMap<Coord, Boolean> visited = new HashMap<>();
        ArrayList<ArrayList<Coord>> paths = new ArrayList<ArrayList<Coord>>();
        Queue queue = new LinkedList();
        queue.add(start);
        ArrayList<Coord> tmp = new ArrayList<Coord>();
        tmp.add(start);
        paths.add(tmp);
        //printNode(start);
        visited.put(start, true);
        while (!queue.isEmpty()) {
            Coord node = (Coord) queue.remove();
            //Coord child = null;
            for (Coord c : board.adj(node)) {
                if (visited.get(c) == null) {
                    if (board.getValue(c) == 0)//Open coordinate
                    {
                        ArrayList<Coord> tmp2 = new ArrayList<Coord>(getPath(paths, node));
                        tmp2.add(c);
                        paths.add(tmp2);
                        //paths.addAll()
                        queue.add(c);
                    } else if (c.equals(finish))//Destination reached
                    {
                        ArrayList<Coord> tmp2 = new ArrayList<Coord>(getPath(paths, node));
                        tmp2.add(c);
                        paths.add(tmp2);
                        //paths.addAll()
                        queue.add(c);
                        return paths;
                    } else//Obstacle hit
                    {

                    }
                    visited.put(c, true);
                    //printNode(child);
                }
            }
        }
        return paths;
    }

    static ArrayList<Coord> getPath(ArrayList<ArrayList<Coord>> paths, Coord node) {
        for (ArrayList<Coord> p : paths) {
            if (node.equals(p.get(p.size() - 1))) {
                return p;
            }
        }
        return null;
    }

    static double distanceTo(Coord co1, Coord co2) {
        return Math.sqrt(Math.pow(co2.x - co1.x, 2) + Math.pow(co2.y - co1.y, 2));
    }

}