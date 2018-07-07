package objects;

import java.util.LinkedList;

import interfaces.IO;

/**
 * Implementacion artesanal de una lista First in first out se basa en una lista
 * enlazada
 * 
 * @author DanielRosillo
 * @github DanielRosillo
 */
public class Fifo<U> implements IO<U>
{
    private LinkedList<U> strack;

    /*
     * crea una lista fifo vacia
     */
    public Fifo()
    {
	strack = new LinkedList<>();
    }

    @Override
    public void push(U u)
    {
	strack.add((U) u);
    }

    @Override
    public U pop()
    {
	U i = (U) strack.getFirst();
	strack.removeFirst();
	return i;
    }

    @Override
    public U last()
    {
	return (!strack.isEmpty()) ? strack.get(size()) : null;
    }

    @Override
    public U first()
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
    public LinkedList<U> onList()
    {
	return strack;
    }

    @Override
    public void remove(U u)
    {
	strack.remove(u);
    }
}
