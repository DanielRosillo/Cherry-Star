package interfaces;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Determina operaciones entre diferentes tipos de listas
 * 
 * @author DanielRosillo
 * @github DanielRosillo
 */
public interface IO<U>
{
    public void push(U u);

    public U pop();

    public U last();

    public U first();

    public boolean isEmpty();

    public int size();

    public void flush();

    public LinkedList<U> onList();

    public void remove(U u);

    public default String peek()
    {
	return ("Report - " + ZonedDateTime.now().getDayOfMonth() + "/" + ZonedDateTime.now().getMonthValue() + "/"
		+ ZonedDateTime.now().getYear() + "  " + ZonedDateTime.now().getHour() + "" + ":"
		+ ZonedDateTime.now().getMinute() + "" + ":" + ZonedDateTime.now().getSecond() + "\n" + "fist element: "
		+ first().toString() + "last element: " + last().toString() + "size: " + size() + "\n");
    }
    
    /**
     * 
     * @param k objeto que se desea encontrar
     * @return el n objeto que coniside con la definicion pasada de k
     */
    public default Optional<U> find(Object k)
    {
	return onList().stream().filter(x -> x.equals(k)).limit(1).findAny();
    }
    /**
     * 
     * @param u objeto 	que se desea saber si esta contenido en la estructura
     * @return true si existe un n objeto que coninsida con la descriosion de u o false.
     */
    public default boolean contains(U u)
    {
	return (onList().contains(u));
    }
}
