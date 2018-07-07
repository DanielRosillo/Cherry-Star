package tests;

import tree.Node;
import tree.Tree;

/**
 * 
 * @author DanielRosillo
 * @github DanielRosillo
 */
public class Test
{

    public static void main(String[] args)
    {
	try
	{
	    // var origin = new Node("A", 0.0);
	    // var tree = new Tree(origin, "N", " TEST");
	    //
	    // Node B = new Node("B", 1.0);
	    // Node C = new Node("C", 5.0);
	    //
	    // Node D = new Node("D", 6.0);
	    // Node E = new Node("E", 9.0);
	    // Node F = new Node("F", 8.0);
	    // Node G = new Node("G", 5.0);
	    // Node H = new Node("H", 1.0);
	    //
	    // Node I = new Node("I", 5.0);
	    // Node J = new Node("J", 6.0);
	    // Node K = new Node("K", 8.0);
	    // Node L = new Node("L", 7.0);
	    // Node M = new Node("M", 2.0);
	    // Node N = new Node("N", 1.0);
	    // Node O = new Node("O", 9.0);
	    // Node P = new Node("P", 8.0);
	    //
	    // tree.flourish(origin, B);
	    // tree.flourish(origin, C);
	    //
	    // tree.flourish(B, D);
	    // tree.flourish(B, E);
	    //
	    // tree.flourish(C, F);
	    // tree.flourish(C, G);
	    // tree.flourish(C, H);
	    //
	    // tree.flourish(D, I);
	    // tree.flourish(D, J);
	    // tree.flourish(E, K);
	    // tree.flourish(E, L);
	    // tree.flourish(E, M);
	    //
	    // tree.flourish(F, N);
	    // tree.flourish(H, O);
	    // tree.flourish(H, P);
	    //
	    // System.out.println("\n Original tree");
	    // tree.onList().forEach(n -> System.out.println(n.state()));
	    // System.out.println("--------------------------------------------------------");
	    // System.out.println("Solution");
	    // Optional<Node> r = tree.IDS(4);
	    // tree.trip(r.get());

	    var node = new Node("A", 0, new Integer[] { 2,8,3,1,6,4,7,0,5});
	    var tree = new Tree(node, "M", "Test", new Integer[] { 1, 2, 3, 8, 0, 4, 7, 6, 5 });

	    var B = new Node("B", 1, new Integer[] { 2, 8, 3, 1, 6, 4, 0, 7, 5 });
	    var C = new Node("C", 1, new Integer[] { 2, 8, 3, 1, 0, 4, 7, 6, 5 });
	    var D = new Node("D", 1, new Integer[] { 2, 8, 3, 1, 6, 4, 7, 5, 0 });

	    var E = new Node("E", 2, new Integer[] { 2, 8, 3, 0, 1, 4, 7, 6, 5 });
	    var F = new Node("F", 2, new Integer[] { 2, 0, 3, 1, 8, 4, 7, 6, 5 });
	    var G = new Node("G", 2, new Integer[] { 2, 8, 3, 1, 4, 0, 7, 6, 5 });

	    var H = new Node("H", 3, new Integer[] { 0, 8, 3, 2, 1, 4, 7, 6, 5 });
	    var I = new Node("I", 3, new Integer[] { 2, 8, 3, 7, 1, 4, 0, 6, 5 });
	    var J = new Node("J", 3, new Integer[] { 0, 2, 3, 1, 8, 4, 7, 6, 5 });
	    var K = new Node("K", 3, new Integer[] { 2, 3, 0, 1, 8, 4, 7, 6, 5 });

	    var L = new Node("L", 4, new Integer[] { 1, 2, 3, 0, 8, 4, 7, 6, 5 });

	    var M = new Node("M", 5, new Integer[] { 1, 2, 3, 8, 0, 4, 7, 6, 5 });
	    var N = new Node("N", 5, new Integer[] { 1, 2, 3, 7, 8, 4, 0, 6, 5 });

	    tree.flourish(node, B);
	    tree.flourish(node, C);
	    tree.flourish(node, D);

	    tree.flourish(C, E);
	    tree.flourish(C, F);
	    tree.flourish(C, G);

	    tree.flourish(E, H);
	    tree.flourish(E, I);

	    tree.flourish(F, J);
	    tree.flourish(F, K);

	    tree.flourish(J, L);

	    tree.flourish(L, M);
	    tree.flourish(L, N);

	    long time_start, time_end;
	    time_start = System.currentTimeMillis();

	    System.out.println("A*");
	    var k = tree.star().get();
	    time_end = System.currentTimeMillis();
	    System.out.println("the task has taken " + (time_end - time_start) + " milliseconds");
	    tree.trip(k);
	    System.out.println("----------------------------");

	    System.out.println("BFS");
	    time_start = System.currentTimeMillis();
	    var g = tree.BFS().get();
	    time_end = System.currentTimeMillis();
	    System.out.println("the task has taken " + (time_end - time_start) + " milliseconds");
	    tree.trip(g);
	    System.out.println("----------------------------");

	    System.out.println("UCS");
	    time_start = System.currentTimeMillis();
	    var V = tree.UCS().get();
	    time_end = System.currentTimeMillis();
	    System.out.println("the task has taken " + (time_end - time_start) + " milliseconds");
	    tree.trip(V);
	    System.out.println("----------------------------");

	    System.out.println("DFS");
	    time_start = System.currentTimeMillis();
	    var R = tree.DFS().get();
	    time_end = System.currentTimeMillis();
	    System.out.println("the task has taken " + (time_end - time_start) + " milliseconds");
	    tree.trip(R);
	    System.out.println("----------------------------");

	    System.out.println("DLS");
	    time_start = System.currentTimeMillis();
	    var Q = tree.DLS(10).get();
	    time_end = System.currentTimeMillis();
	    System.out.println("the task has taken " + (time_end - time_start) + " milliseconds");
	    tree.trip(Q);
	    System.out.println("----------------------------");

	    System.out.println("IDS");
	    time_start = System.currentTimeMillis();
	    var Z = tree.IDS(10).get();
	    time_end = System.currentTimeMillis();
	    System.out.println("the task has taken " + (time_end - time_start) + " milliseconds");
	    tree.trip(Z);
	    System.out.println("----------------------------");
	    
	    System.out.println(node.manhattan(tree.source()));
	}
	catch (Throwable m)
	{
	    m.printStackTrace();
	}

	System.exit(0);
    }
}
