package objects;

import java.util.LinkedList;

import interfaces.IO;

/**
 * Implementacion artesanal de una lista last in first out se basa en una lista
 * enlazada
 * 
 * @author DanielRosillo
 * @github DanielRosillo
 */
public class Lifo<L> implements IO<L>
{
    private LinkedList<L> strack;

    /*
     * crea una lista lifo vacia
     */
    public Lifo()
    {
	strack = new LinkedList<>();
    }

    @Override
    public void push(L o)
    {
	strack.add((L) o);
    }

    @Override
    public L pop()
    {
	L i = (L) strack.getLast();
	strack.removeLast();
	return i;
    }

    @Override
    public L last()
    {
	return (!strack.isEmpty()) ? strack.get(size()) : null;
    }

    @Override
    public L first()
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
    public LinkedList<L> onList()
    {
	return strack;
    }

    @Override
    public void remove(L o)
    {
	strack.remove(o);
    }

}
