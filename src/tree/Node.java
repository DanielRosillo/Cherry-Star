package tree;

import java.util.Optional;

import objects.Fifo;

/**
 * Modela un estado arbitrario para alguna estructura de busqueda de soluciones
 * Se puede contruir de dos formas(constructor sobrecargado), uno para el uso de heuristicas y otro para trabajar a priori.
 * @author DanielRosillo
 * @github DanielRosillo
 */
public class Node
{
    protected Fifo<Node> actions;// Acciones disponibles desde el estado
    protected String state;// Estado que representa en la estructura
    protected int level;// Nivel dentro de la estructura
    protected Node origin;// Estado origen
    protected boolean explored;// Semaforo multiusos
    protected boolean status;// semaforo multiusos
    protected Integer cost;// Costo del estado
    protected boolean root;// indica si el estado es el estado inicial
    protected Integer[] source;// Representacion Matricial del problema

    /**
     * Crea un estado
     * 
     * @param value
     *            estado a representar
     * @param cost
     *            costo del estado a crear
     * 
     * @param source
     *            Representacion matricial del problema
     */
    public Node(String value, int cost, Integer[] source)
    {
	actions = new Fifo<>();
	this.state = value;
	this.cost = cost;
	this.source = source;
    }

    /**
     * Crea un estado
     * 
     * @param value
     *            estado a representar
     * @param cost
     *            costo del estado a crear
     **/
    public Node(String value, int cost)
    {
	actions = new Fifo<>();
	this.state = value;
	this.cost = cost;
    }

    public void setState(String value)
    {
	this.state = value;
    }

    public void setLevel(int level)
    {
	this.level = level;
    }

    public boolean isExplored()
    {
	return explored;
    }

    public void setExplored(boolean explored)
    {
	this.explored = explored;
    }

    public boolean isStatus()
    {
	return status;
    }

    public void setStatus(boolean status)
    {
	this.status = status;
    }

    public Integer getCost()
    {
	return cost;
    }

    public void setCost(int cost)
    {
	this.cost = cost;
    }

    public Node getOrigin()
    {
	return origin;
    }

    public void setOrigin(Node origin)
    {
	this.origin = origin;
    }

    public boolean isRoot()
    {
	return root;
    }

    public void setRoot(boolean root)
    {
	this.root = root;
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((origin == null) ? 0 : origin.hashCode());
	long temp;
	temp = Double.doubleToLongBits(cost);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + (explored ? 1231 : 1237);
	result = prime * result + level;
	result = prime * result + ((state == null) ? 0 : state.hashCode());
	result = prime * result + (status ? 1231 : 1237);
	return result;
    }

    @Override
    public boolean equals(Object obj)
    {
	return this.state.equals(((Node) obj).state);
    }

    /**
     * 
     * @return true si el estado no contiene acciones (Nodo hoja)
     */
    public boolean isFinal()
    {
	return (actions.isEmpty()) ? true : false;
    }

    /**
     * 
     * @return el nivel que ocupa dentro de la estructura de soluciones
     */
    public int level()
    {
	return level;
    }

    /**
     * 
     * @return los estados posibles desde un estado cualquiera
     */
    public Fifo<Node> actions()
    {
	return actions;
    }

    /**
     * 
     * @param e
     *            Accion a asociar con el estado actual.
     */
    public void flourish(Node e)
    {
	actions.push(e);
    }

    /**
     * 
     * @return devuelve la primera accion disponible de ese estado
     */
    public Node pop()
    {
	return actions.pop();
    }

    /**
     * 
     * @return el estado que representa
     */
    public String state()
    {
	return state;
    }

    @Override
    public String toString()
    {
	return "NODE - " + "state: " + state + ",   level: " + level + ",   cost: " + cost + ",   actions: "
		+ actions.size() + "\n" + "status: " + status + ",   explored: " + explored + ",   root:" + isRoot()
		+ "]";
    }

    /**
     * 
     * @return la representacion matricial del estado
     */
    public Integer[] source()
    {
	return source;
    }

    /**
     * 
     * @param source la nueva representacion matricial del estado
     */
    public void setSource(Integer[] source)
    {
	this.source = source;
    }

    /**
     * La Heuristica de manhattan, es un tipo de geometria donde las distancias son los valores absolutos de la relatividad de su representacion en horizontal y vertical.
     * 
     * Ejemplo
     * 
     * 
     * 7,2,4            0,1,2
     * 5,0,6            3,4,5
     * 8,3,1            6,7,8
     *  
     * state  k          goal r
     * 
     * Numeros fuera de lugar en k respecto de r, : 8 (sin incluir 0 puesto que representa el espacio vacio) (7,2,4,5,6,8,3,1)
     * Distancia  en k del 7 partiendo desde la esquina superior derecha como origen  = (1,1)
     * Distancia  en goal del 7 partiendo desde la esquina superior derecha como origen  = (2,3)
     * 
     * distancia de manhattan = |(1-2)|+|(1-3)| = 3
     * 
     *  MANHATTAN = |(X1-X2)|+|(Y1-Y2)|
     * 
     * @param goal
     *            es el estado de cual se desea adquirir la heuristica respecto al
     *            estado actual.
     * @return la heuristica de manhattan respecto al estado actual y el estado
     *         futuro
     */
    public Optional<Integer> manhattan(Integer[] goal)
    {
	if (source() == null) return Optional.empty();
	int l = 3;// esta variable define la dimension del array de entrada
	int x = 1, y = 1;// cordenadas de un elemento en la representacion matricial del problema
	Integer n1 = 0;// sumador de heuristicas, el valor de una heuristica que no existe por defecto
		       // es 0

	// Recorremos la matriz de entrada
	for (int i = 0; i < l * l; i++)
	{
	    // si el valor de la matriz del estado actual es diferente a la obtenida en la
	    // misma posicion pero del estado comparado
	    // Obtenemos la heuristica de ese dato
	    if (source[i] != goal[i] && source[i] != 0)
	    {
		Integer s[] = find(source[i], goal).orElse(new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 });// Obtenemos la
													// posciones de
													// el elemento
													// en el nuevo
													// estado
		// Calculamos la heuristica de manhattan
		Integer sum = Math.abs((x - s[0])) + Math.abs((y - s[1]));
		n1 += sum;
		
	    }

	    if (x >= l)// si x no sigue siendo menor a la dimension de la matriz, lo que supone que x
		       // es un contador local de la posicion relativa del elemento selecionado en la
		       // matriz original
	    {
		x = 1;// restablecemos el contador de la posicion horizontal relativa a la matriz
		y++;// aumentamos el contador de posicion vertical relativa a la matriz
	    }
	    else x++;// si x sigue siendo menor solo lo aumentamos
	}
	//No existe solucion en el Modelo
	return Optional.ofNullable(n1);
    }

    public Integer heuristic(Integer[] goal)
    {
	return ((int) cost + manhattan(goal).orElse(0));
    }

    /**
     * Este metodo nos permite obtener la posicion de un elemnto en un array que es
     * basicamente las distancias verticales y horizontales en la matriz, estas
     * posiciones son necesarias para realizar la distancia de manhattan
     * 
     * @param number
     *            numero que se desea encontrar
     * @param list
     *            contenedor del valor
     * @return un Optional vacio o las cordenadas del dato en la matriz
     */
    private Optional<Integer[]> find(Integer number, Integer[] list)
    {
	int x = 1, y = 1;// cordenadas de un elemento en la representacion matricial del problema
	int l = 3;// esta variable define la dimension del array de entrada

	for (Integer n : list)
	{
	    if (n == number) return Optional.ofNullable(new Integer[] { x, y });// si el numero actual es igual al
										// buscado retornamos un
										// optionalofnullable
	    if (x >= l)// si x no sigue siendo menor a la dimension de la matriz, lo que supone que x
		       // es un contador local de la posicion relativa del elemento selecionado en la
		       // matriz original
	    {
		x = 1;// restablecemos el contador de la posicion horizontal relativa a la matriz
		y++;// aumentamos el contador de posicion vertical relativa a la matriz
	    }
	    else x++; // si x sigue siendo menor solo lo aumentamos
	}
	return Optional.empty();// Si se llega a este punto el elemento buscado no existe en el estado
				// seleccionado
    }

}
