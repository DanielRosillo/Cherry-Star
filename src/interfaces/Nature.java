package interfaces;

import java.util.LinkedList;

/**
 * Ofrece una base bioinspirada para la creacion de una estructura de soluciones
 * 
 * @author DanielRosillo
 * @github DanielRosillo
 */
public interface Nature<L>
{
    /**
     * Florece(agrega) un nuevo estado en la estructura de estados
     * 
     * @param father
     *            Estado Origen
     * @param o
     *            Nuevo estado
     */
    public void flourish(L father, L l);

    /**
     * Elimina un estado de la estructura esto incluye sus acciones
     * asociadas(estados)
     * 
     * @param leaf
     *            estado a eliminar
     */
    public void wither(L leaf);

    /*
     * Numero de estados en la estructura
     */

    public int size();

    /**
     * Enjuaga(elimina) todos los estados de la estructura
     * 
     */
    public void flush();

    /**
     * 
     * @return la estructura en forma de una lista enlazada
     */
    public LinkedList<L> onList();

}
