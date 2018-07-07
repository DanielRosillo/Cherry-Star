package interfaces;

//No soy mas listo que tu, solo pienso diferente.
import java.util.Optional;

import objects.Fifo;
import objects.Sort;
import tree.Node;
import tree.Tree;

/**
 * Ofrece una base tecnica para trabajar con busquedas NO INFORMADAS, en alguna
 * estructura que implemente la clase Nodo como modelador de estados.
 * 
 * @author DanielRosillo
 * @github DanielRosillo
 * @version 2.0 06/07/2018
 */
public interface Context
{
    public String goal();// Objetivo del arbol

    public String name();// Nombre del arbol

    public boolean isGoal(String value);// Verifica si el estado es objetivo

    public Node last();// Es un estado cualquiera cuya profundidad tambien es la maxima dentro del
		       // arbol

    public Node first();// Nodo origen

    public int deep();// Profundidad del arbol

    public String history();// Historial de operaciones dentro del arbol

    public Integer[] source();

    /**
     * Breadth First Search (BFS) - Busqueda en Anchura
     * 
     * @return Optional con el nodo solucion si existe
     */
    public default Optional<Node> BFS()
    {
	// Verificamos si el estado inicial es solucion o no
	if (isGoal(first().state())) return Optional.ofNullable(first());

	var border = new Fifo<Node>();// Creamos la frontera para la busqueda
	var explored = new Fifo<Node>();// Creamos una lista para almacenar estados explorados
	border.push(first());// Agregamos el estado inicial a la frontera para que sea explorado

	while (!false)
	{
	    if (border.isEmpty()) return Optional.empty();// Si la frontera esta vacia, NO EXISTE SOLUCION EN EL MODELO
	    var node = border.pop();// Se extrae un elemento de la frontera
	    explored.push(node);// Se marca como explorado puesto que no es solucion
	    // Como el estado actual no es solucion se recorre a sus estados asociados
	    // Para cada accion en el estado actual
	    for (Node n : node.actions().onList())
	    {
		// si el estado no esta explorado
		if (!explored.contains(n) && !border.contains(n))
		    // Verificamos si entonces el estado es solucion
		    if (this.isGoal(n.state())) return Optional.ofNullable(n);

		// Si no es solucion se agrega a la frontera para explorar a sus acciones
		// posteriormente
		border.push(n);
	    }
	}
    }

    /**
     * Uniform Cost Search (UCS) - Busqueda de Costo Uniforme
     * 
     * @return Optional con el nodo solucion si existe
     */
    public default Optional<Node> UCS()
    {
	var border = new Sort();// Creamos una lista de prioridad
	var explored = new Fifo<Node>();// Creamos una lista para almacenar estados explorados
	border.push(first());// Agregamos el nodo raiz a la lista

	for (;;)// Ciclo Infinito
	{
	    if (border.isEmpty()) return Optional.empty();// Si la frontera esta vacia NO EXISTE SOLUCION EN EL MODELO
	    var node = border.pop();// Obtenemos el estado con menor costo
	    if (this.isGoal(node.state())) return Optional.ofNullable(node);// Si el estado es solucion lo devolvemos.
	    explored.push(node);// Como el estado no es solucion lo marcamos como explorado
	    // Recorremos las acciones asociadas al estado
	    node.actions().onList().forEach(n ->
	    {
		// Si el estado no esta explorado y no esta en la frontera lo agregamos para
		// explorarlo en otra iteracion
		if (!explored.contains(n) && !border.contains(n)) border.push(n);

		else
		{
		    var k = border.find(n.state()).get();// obtenemos el estado repetido
		    if (n.getCost() < k.getCost())// si el costo del estado que ya tenemos es menor al
						  // explorado
		    {
			border.remove(k);// Eliminamos el estado puesto que existe uno mejor
			border.push(n);// Agregamos el nuevo estado
		    }
		}
	    });
	}
    }

    /**
     * Depth First Search (DFS) - Busqueda en Profundidad
     * 
     * @return Optional con el nodo solucion si existe
     */
    public default Optional<Node> DFS()
    {
	return DFS(first(), new Fifo<Node>());
    }

    public default Optional<Node> DFS(Node node, Fifo<Node> explored)
    {
	if (isGoal(node.state())) return Optional.ofNullable(node);// Si el estado inicial es objetivo lo devolvemos

	else
	{
	    explored.push(node);// Marcamos el estado como explorado puesto que no es solucion
	    for (var n : node.actions().onList())// Por cada accion posible en el estado
	    {
		Optional<Node> e = null;
		if (!explored.contains(n)) // Si el estado no esta explorado
		{
		    e = DFS(n, explored);// exploramos el estado recursivamente.
		    if (e.isPresent()) return e;// si el estado esta presente sicnifica que la solucion se encontro y lo
		    // retornamos
		}
	    }
	}
	// Si se llega a este paso, no existe solucion en el modelo
	return Optional.empty();
    }

    /**
     * Depth Limited Search (DLS) - Busqueda en Profundidad Limitada
     * 
     * @param eu
     *            es el limite de busqueda
     * 
     * @return Optional con el nodo solucion si existe
     */
    public default Optional<Node> DLS(int eu)
    {
	return DLS(first(), eu, new Fifo<Node>());
    }

    public default Optional<Node> DLS(Node node, int eu, Fifo<Node> explored)
    {
	if (eu == 0) return Optional.empty();// Retorna un optional vacio si no encontro la solucion en los niveles
					     // propuestos, mas no
	// sicnifica que no exista en el modelo como tal.
	if (isGoal(node.state())) return Optional.ofNullable(node);// Si el estado inicial es solucion lo devuelve
	else
	{
	    eu--;// Disminuimos un nivel
	    explored.push(node);// Marcamos el estado como explorado
	    for (var n : node.actions().onList())// Para cada accion posible en el estado
	    {
		Optional<Node> e = null;
		if (!explored.contains(n))// Si el estado no esta explorado
		{
		    e = DLS(n, eu, explored);// Exploramos el estado recusivamente
		    if (e.isPresent()) return e;// Si existe un estado al final diferente de null, sicnifica que es el
		    // estado solucion y lo devolvemos
		}
	    }
	}
	// Si se llega a este paso, no existe solucion en el modelo
	return Optional.empty();
    }

    /**
     * Iterative Deepening Search (IDS) - Busqueda en Profundidad Iterativa
     * 
     * @param eu
     *            es el limite de busqueda
     * @return Optional con el nodo solucion si existe
     */
    public default Optional<Node> IDS(int eu)
    {
	var i = 1;
	Optional<Node> n;
	while (i <= eu)
	{
	    n = DLS(i);// Buscamos el estado solucion implementando DLS
	    if (n.isPresent()) if (isGoal(n.get().state())) return n;// Si el resultado es solucion lo retornamos

	    i++;
	}
	// Si se llega a este paso, no existe solucion en el modelo
	return Optional.empty();
    }

    /**
     * Bidirectional Search (BS) - Busqueda Bidireccional
     * 
     * Esta Implementacion libre del BS utiliza busqueda en anchura por ambos lados,
     * mientras forma otro arbol solucion, NO ALTERA EL ESTADO ORIGINAL DEL ARBOL
     * 
     * 
     * @param goal
     *            Es el estado meta
     * @return un Optional con la solucion en forma de arbol de un solo camino.
     */
    public default Optional<Tree> BS(Node goal)
    {
	var n1 = new Node(goal.state(), goal.getCost(), null);// Se crea el estado padre de la solucion.
	Tree copy = new Tree(n1, "N", "Tree Solution");// Creamos el arbol con el nodo final del original como origen.
	var explored = new Fifo<Node>();// Frontera utilizada por la primera busqueda
	var border = new Fifo<Node>();// Frontera utilizada por la segunda busqueda

	/*
	 * Iniciamos las dos fronteras para la busqueda, una desde el estado inicial y
	 * otra desde el estado final
	 **/
	border.push(goal.getOrigin());
	explored.push(first());

	while (!false)
	{
	    // Si la frontera esta vacia, no existe solucion en el modelo
	    if (explored.isEmpty() || border.isEmpty()) return Optional.empty();
	    var node = border.pop();// Obtenemos el primer estado de la busqueda

	    border.push(node.getOrigin());// Agregamos el estado origen a la frontera
	    node = new Node(node.state(), node.getCost(), null);// Creamos el nuevo estado apartir del actual
	    copy.flourish(n1, node);// Florecemos el estado en el arbol

	    if (explored.contains(node))// Si el nuevo estado existe en la frontera de la otra busqueda, hay una
					// interseccion y por tanto hay solucion
	    // y solo falta asociar los dos caminos
	    {
		Optional<Node> op = explored.find(node);// Obtenemos el estado interseccion de la otra frontera
		n1 = op.get().getOrigin();// Asignamos el padre de ese estado a otro temporal

		while (n1 != null)// Mientras exista algun estado asociado, lo recuperamos y lo agregamos al arbol
				  // solucion sin afectar el original
		{
		    var aux = new Node(n1.state(), n1.getCost());// Creamos el estado copia
		    copy.flourish(node, aux);// Florecemos ese estado en el arbol solucion
		    node = aux;// como en la siguiente vuelta el nuevo estado sera ahora el padre, lo guardamos
		    n1 = n1.getOrigin();// Pasamos al siguiente estado
		}

		return Optional.of(copy);// Retornamos el arbol solucion
	    }
	    // Como no hay estados interseccion avanzamos entre estados
	    n1 = node;

	    // Agregamos los estados que fatan por eplorar a la busqueda principal
	    for (var n2 : explored.pop().actions().onList())
		if (!explored.contains(n2)) explored.push(n2);
	    // Extraemos el primer estado que en este punto sabemos no sirve para nada
	    explored.pop();
	}
    }

    /**
     * A* Search (A*) - Busqueda A Estrella
     * 
     * Caracteristicas:
     * 
     * - Completo
     * - Optimo
     * 
     * Tiempo: exponencial si la heuristica es mala, lineal si es consistente
     * Espacio: exponencial
     * 
     * La calidad de este metodo depende de la heuristica del modelo
     * recordar que si....
     * 
     *  - h(x) es una estimacion perfecta, A* converge al objetivo sin equivocarce
     *  - h(x) = 0, g(x) controla la estimacion heuristica
     *  - h(x) = 0 && g(x) = 0 -> la busqueda es estocastica
     *  - h(x) = 0 && g(x) = 1 -> la busqueda sera BFS 
     *  - h(x) no es consistente, se garantiza solucion solo en un arbol 
     *  - h(x) es consistente, es admisible automaticamente.
     * 
     * @return Un optional con que puede contener el camino solucion
     */
    public default Optional<Node> star()
    {
	var border = new Sort();// Creamos una lista de prioridad
	border.heuristicMode();// Activamos el ordenamiento por valor heuristico en la frontera
	border.setSource(source());// Asignamos el estado final como referencia heuristica
	var explored = new Fifo<Node>();// Creamos una lista para almacenar estados explorados
	border.push(first());// Agregamos el estado inicial a la frontera para que sea explorado
	while (!false)
	{
	    if (border.isEmpty()) return Optional.empty();// Si la frontera esta vacia NO EXISTE SOLUCION EN EL MODELO
	    Node n = border.pop();// Sacamos un estado de la frontera

	    if (isGoal(n.state())) return Optional.ofNullable(n);// Si ese estado es objetivo lo retornamos

	    explored.push(n);// Como no es objetivo lo marcamos como explorado

	    n.actions().onList().forEach(k -> // Recorremos las posibles acciones del estado que sabemos no es objetivo
	    {
		if (!explored.contains(k) && !border.contains(k)) border.push(k);// si no esta explorado ni en la
										 // frontera, lo agregamos a la frontera
		else// Si no ese estado ya es conocido entonces verificamos si el estado tiene una
		    // heuristica diferente
		{
		    var c = border.find(n.state()).get();// Obtenemos el estado repetido
		    if (n.heuristic(source()) < c.heuristic(source()))// Si el costo del estado que ya
								      // tenemos es menor al
		    // explorado
		    {
			border.remove(c);// Eliminamos el estado puesto que existe uno mejor
			border.push(n);// Agregamos el nuevo estado
		    }
		}
	    });
	}
    }

    /**
     * Obteniene una lista con todos los estados sin acciones (hoja), se basa en la
     * busqueda en anchura
     * 
     * @return una lista Fifo con estados de ti po Node
     */
    public default Fifo<Node> mapBorder()
    {
	var border = new Fifo<Node>();
	var help = new Fifo<Node>();
	help.push(first());

	while (!help.isEmpty())
	{
	    var node = help.pop();
	    for (var n : node.actions().onList())
	    {
		if (n.isFinal()) border.push(n);// Si el estado no tiene acciones lo agregamos
		else help.push(n);
	    }
	}
	return border;
    }

    /**
     * Muestra en pantalla el camino de un estado hasta su origen
     * 
     * @param ex
     *            estado del cual se necesita el camino
     */
    public default void trip(Node ex)
    {
	if (ex == null) return;// Se termina la exploracion

	else
	{
	    trip(ex.getOrigin());// Obtenemos el camino recursivamente
	    System.out.println("State: " + ex.state() + " ---- Cost: " + ex.getCost());// Imprimimos el camino
	}
    }
}
