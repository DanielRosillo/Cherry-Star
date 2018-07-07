package objects;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.IO;
import tree.Node;

/**
 * Implementacion artesanal de una lista de prioridad se basa en una lista
 * enlazada
 * 
 * @author DanielRosillo
 * @github DanielRosillo
 */
public class Sort implements IO<Node>
{
    private LinkedList<Node> strack;
    private boolean heuristic = false;
    private Integer[] source;

    // Crea una lista de prioridad vacia
    public Sort()
    {
	strack = new LinkedList<>();
    }

    @Override
    public void push(Node o)
    {
	strack.add(o);

	if (heuristic) sort(source);
	else sort();
    }

    @Override
    public Node pop()
    {
	Node i = strack.getFirst();
	strack.removeFirst();
	return i;
    }

    @Override
    public Node last()
    {
	return (!strack.isEmpty()) ? strack.get(size()) : null;
    }

    @Override
    public Node first()
    {
	return (!strack.isEmpty()) ? strack.get(0) : null;
    }

    @Override
    public boolean isEmpty()
    {
	return strack.isEmpty();
    }

    @Override
    public int size()
    {
	return strack.size();
    }

    @Override
    public void flush()
    {
	while (this.size() >= 0)
	    pop();
    }

    @Override
    public LinkedList<Node> onList()
    {
	return strack;
    }

    @Override
    public void remove(Node o)
    {
	strack.remove(o);
    }

    /**
     * 
     * @return la lista de estados como una lista enlazada
     */
    public List<String> states()
    {
	return strack.stream().map(n -> n.state()).collect(Collectors.toList());
    }

    /*
     * Ordena la lista segun los costes asociados
     */
    public void sort()
    {
	strack.sort((Node n1, Node n2) ->
	{
	    if (n1.getCost() < n2.getCost()) return -1;
	    if (n1.getCost() > n2.getCost()) return 1;
	    return 0;
	});
    }

    public void sort(Integer[] goal)
    {
	strack.sort((Node n1, Node n2) ->
	{
	    if (n1.heuristic(goal) < n2.heuristic(goal)) return -1;
	    if (n1.heuristic(goal) > n2.heuristic(goal)) return 1;
	    return 0;
	});
    }

    public void heuristicMode()
    {
	heuristic = !heuristic;
    }

    public void setSource(Integer[] source)
    {
	this.source = source;
    }
}
