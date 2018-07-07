package tree;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.LinkedList;

import interfaces.Context;
import interfaces.Nature;
import objects.Fifo;

/**
 * Modela un arbol de soluciones
 * 
 * @author DanielRosillo
 * @github DanielRosillo
 */
public class Tree implements Nature<Node>, Context
{
    protected Node root;// Estado Inicial
    protected Node last;// Es un estado cualquiera cuya profundidad tambien es la maxima dentro del
    // arbol
    protected int size;// estados en el arbol
    protected String name;// Nombre del arbol
    protected String goal;// Objetivo del arbol
    private Node aux;// Nodo multiusos
    protected StringBuilder history;// Registro de actividades
    protected Integer[] source;// Representacion matricial del objetivo

    /**
     * 
     * @param root
     *            Estado Inicial
     * @param goal
     *            Meta del arbol
     * @param name
     *            Nombre del Arbol
     */
    public Tree(Node root, String goal, String name)
    {
	this.root = root;
	last = root;
	this.name = name;
	this.goal = goal;
	root.root = true;
	size = 1;
	history = new StringBuilder();
	history.append("\n[" + LocalDate.now() + "-" + ZonedDateTime.now().getDayOfMonth() + "/"
		+ ZonedDateTime.now().getMonthValue() + "/" + ZonedDateTime.now().getYear() + "  "
		+ ZonedDateTime.now().getHour() + "" + ":" + ZonedDateTime.now().getMinute() + "" + ":"
		+ ZonedDateTime.now().getSecond() + " " + " Create Tree\n");
	history.append(root.toString());
	history.append(
		"\n----------------------------------------------------------------------------------------------------");
    }

    /**
     * 
     * @param root
     *            Estado Inicial
     * @param goal
     *            Meta del arbol
     * @param name
     *            Nombre del Arbol
     */
    public Tree(Node root, String goal, String name, Integer[] source)
    {
	this.root = root;
	last = root;
	this.name = name;
	this.goal = goal;
	root.root = true;
	size = 1;
	this.source = source;
	history = new StringBuilder();
	history.append("\n[" + LocalDate.now() + "-" + ZonedDateTime.now().getDayOfMonth() + "/"
		+ ZonedDateTime.now().getMonthValue() + "/" + ZonedDateTime.now().getYear() + "  "
		+ ZonedDateTime.now().getHour() + "" + ":" + ZonedDateTime.now().getMinute() + "" + ":"
		+ ZonedDateTime.now().getSecond() + " " + " Create Tree\n");
	history.append(root.toString());
	history.append(
		"\n----------------------------------------------------------------------------------------------------");
    }

    @Override
    public void flourish(Node father, Node o)
    {
	father.flourish(o);// Agregamos "Florecemos" el nuevo estado con su accion asociada en el arbol
	o.setOrigin(father);// Asociamos los estados
	o.setLevel(father.level() + 1);// Aumentamos el nivel de profundidad del nuevo estado basandonos en el estado
	// anterior
	last = o;// si el nuevo estado es mas profundo que el maximo se asigna como nuevo
	// nodo maximo
	size++;// Aumentamos el numero de estados
	history.append("\n[" + LocalDate.now() + "-" + ZonedDateTime.now().getDayOfMonth() + "/"
		+ ZonedDateTime.now().getMonthValue() + "/" + ZonedDateTime.now().getYear() + "  "
		+ ZonedDateTime.now().getHour() + "" + ":" + ZonedDateTime.now().getMinute() + "" + ":"
		+ ZonedDateTime.now().getSecond() + " " + " Flourish Tree\n");
	history.append(o.toString());
	history.append(
		"\n----------------------------------------------------------------------------------------------------");
    }

    @Override
    public void wither(Node leaf)
    {
	if (leaf.isRoot())// Si el nodo a eliminar es el origen del arbol
	{
	    root = null;
	    last = null;
	    return;
	}

	else if (leaf.state.equals(last.state))// Si el nodo es usado para representar el nivel mas profundo del arbol
	{
	    aux = new Node("", 0, null);
	    // Buscamos otro estado para susituirlo
	    mapBorder().onList().forEach(n ->
	    {
		if (n.level() == leaf.level() && !n.state.equals(leaf.state)) last = n;
		if (n.level() > aux.level() && !n.state.equals(leaf.state)) aux = n;
	    });
	    last = aux;// Asignamos el nuevo estado arbitrario
	    aux = null;
	}

	leaf.getOrigin().actions.remove(leaf);// Desvinculamos el estado con su origen
	leaf.setOrigin(null);
	size--;
    }

    @Override
    public int size()
    {
	return size;
    }

    @Override
    public void flush()
    {
	root = null;
	last = null;
	goal = "";
	size = 0;
	history = new StringBuilder();

    }

    @Override
    public LinkedList<Node> onList()
    {
	var help = new Fifo<Node>();
	var list = new LinkedList<Node>();
	help.push(first());

	while (!help.isEmpty())
	{
	    var node1 = help.pop();
	    list.add(node1);
	    for (Node n : node1.actions.onList())
		help.push(n);
	}
	return list;
    }

    @Override
    public String name()
    {
	return name;
    }

    @Override
    public boolean isGoal(String value)
    {
	return (value.equals(goal)) ? true : false;
    }

    @Override
    public Node last()
    {
	return last;
    }

    @Override
    public Node first()
    {
	return root;
    }

    @Override
    public int deep()
    {
	return last.level;
    }

    @Override
    public String history()
    {
	return history.toString();
    }

    @Override
    public String toString()
    {
	return ("Report - " + ZonedDateTime.now().getDayOfMonth() + "/" + ZonedDateTime.now().getMonthValue() + "/"
		+ ZonedDateTime.now().getYear() + "  " + ZonedDateTime.now().getHour() + "" + ":"
		+ ZonedDateTime.now().getMinute() + "" + ":" + ZonedDateTime.now().getSecond() + "\n" + "Tree " + name
		+ "\n" + "size: " + size() + "\n" + "objetive: " + goal + "\n" + "deep: " + deep() + "\n");
    }

    @Override
    public String goal()
    {
	return goal;
    }

    @Override
    public Integer[] source()
    {
	return source;
    }

}
