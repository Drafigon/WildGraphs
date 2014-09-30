package external;

import graph.drawing.*;
import graph.implementation.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Use this class to save graphs into a file.
 * It is a specific class and creates a file structure,
 * the {@link GraphLoader} can read.
 * To write into a file, it uses {@link DataWriter}.
 * 
 * @author J. Wilde
 * @version 2.0
 * @since 23.05.2014
 *
 */
public final class GraphSaver {

	/**
	 * <pre>
	 * This is a special string to show	 
	 * where the next graph definition begins. 
	 * 
	 * The {@link GraphLoader} uses this string too.
	 * Thats why its public.
	 * </pre>
	 */
	public static final String NEXT_GRAPH = ".:|:.";
	public static final String ATTRIBUTE = "--";
	
	// This hash maps the class needs to not create double entries
	private static HashMap<Vertex, String> vertexWithVDO  = new HashMap<Vertex, String>();
	private static HashMap<VertexDrawOption, String> vdoName = new HashMap<VertexDrawOption, String>();
	private static HashMap<Edge, String> edgeWithEDO = new HashMap<Edge, String>();
	private static HashMap<EdgeDrawOption, String> edoName = new HashMap<EdgeDrawOption, String>();
	
	// This is a abstract class, not instanced, no subclasses
	private GraphSaver() {}
	
	/**
	 * <pre>
	 * Saves a single graph.
	 * Write the graph definition into the file.
	 * 
	 * Use {@link #saveAnimation} for more than one graph.
	 * {@link GraphLoader} can read the file structure to load the graph again.
	 * </pre>
	 * 
	 * @param graph : the graph to save
	 * @param path : the pathname to the file
	 */
	public static void saveGraph(
			Graph graph, 
			String path) {		
		
		// get current graph definition as strings
		ArrayList<String> definition = createGraphDefinition(graph);
		
		// write into file
		writeInFile(definition, path);		
	}
	
	/**
	 * <pre>
	 * Saves one or more graphs into a file as animation.
	 * {@link GraphLoader} can read the file structure to load the graph(s) again.
	 * </pre>
	 * @param graphs : the graphs to save
	 * @param path : the pathname to the file
	 */
	public static void saveAnimation(ArrayList<Graph> graphs, String path) {
		
		// the result of all graphs
		ArrayList<String> completeDefinition = new ArrayList<String>();
		
		for(Graph graph : graphs) {				
			// get graph definition
			ArrayList<String> definition = createGraphDefinition(graph);
			
			// add definition
			completeDefinition.addAll(definition);
				
			// add delimiter			
			completeDefinition.add(NEXT_GRAPH);			
		}
		
		// write into file
		writeInFile(completeDefinition, path);	
	}
	
	/**
	 * Creates a text which defines the given graph.
	 * 
	 * @param graph : the graph to make a text for
	 * @return ArrayList of String
	 */
	private static ArrayList<String> createGraphDefinition(Graph graph) {
		// Fill lists
		loadMaps(graph);
				
		// Run through the lists and create strings as graph definition 
		ArrayList<String> completeDefinition = getFormatedDefinition(graph.isDirected());
		
		// clear all lists (for the next graph)
		clearAllLists();
		
		return completeDefinition;
	}
	
	/**
	 * Initiate the hash maps for the given graph.
	 * 
	 * @param graph
	 */
	private  static void loadMaps(Graph graph) {
		// load all edges and all edge draw options into the hash maps
		loadEdgesAndEDOs(graph);
		
		// load all vertices and all vertex draw options into the hash maps
		loadVerticesAndVDOs(graph);
	}
	
	
	/**
	 * Returns the complete definition of a graph.
	 * 
	 * @param directed : is the graph directed?
	 * @return ArrayList of String
	 */
	private  static ArrayList<String> getFormatedDefinition(boolean directed) {
		// result
		ArrayList<String> completeDefinition = new ArrayList<String>();
		
		// ***
		// Create structure
		// ***
		
		// *** 1: directed?
		// if the graph is directed, add this line: "->"
		if(directed) {
			completeDefinition.add("->");
			completeDefinition.add("");
		}
		
		// *** 2: Add Edges
		ArrayList<String> edgeDef = getAllEdgeDef();
		completeDefinition.addAll(edgeDef);
		completeDefinition.add("");
			
		// *** 3: Add Vertices
		ArrayList<String> vertexDef = getAllVertexDef();
		completeDefinition.addAll(vertexDef);
		completeDefinition.add("");
			
		// *** 4: Add edge draw options
		ArrayList<String> edoDef = getAllEDODef();
		completeDefinition.addAll(edoDef);
		completeDefinition.add("");
		
		// *** 5: Add vertex draw options
		ArrayList<String> vdoDef = getAllVDODef();
		completeDefinition.addAll(vdoDef);
		completeDefinition.add("");
		
		return completeDefinition;
	}
	
	
	/**
	 * <pre>
	 * Write the strings of the arraylist into a file.
	 * 
	 * <b>Attention!: The file will be opened and closed 
	 * each time the function is called.</b>
	 * <pre>
	 * @param definition
	 * @param path
	 */
	private  static void writeInFile(ArrayList<String> definition, String path) {
		DataWriter.openFile(path);
		
		for(String line : definition) {
			DataWriter.writeln(line);
		}
		
		DataWriter.close();
	}
	

	/**
	 * Clear all the hash maps
	 */
	private  static void clearAllLists() {
		vertexWithVDO.clear();
		vdoName.clear();
		edgeWithEDO.clear();
		edoName.clear();
	}
	
	private  static void loadEdgesAndEDOs(Graph graph) {
		// count the different edge draw options
		int edoCount = 1;
		
		// for each edge
		for(Edge edge : graph.getEdges()) {
			// draw option of the current edge
			EdgeDrawOption currentEDO = edge.getDrawOption();
			
			// look, if there is already a draw option
			// with the same settings
			EdgeDrawOption edoInMap = null;
			for(EdgeDrawOption otherEDO : edoName.keySet()){
				if(currentEDO.hasSameOptionsAs(otherEDO)) {
					// -> select it
					edoInMap = otherEDO;
				}
			}
			
			// no draw option found, add the current one -> select it
			if(edoInMap == null) {
				edoInMap = currentEDO;
				edoName.put(edoInMap, "edgetype_" + edoCount);					
				edoCount++;			
			}
			
			// now add the selected draw option to the edge definition
			edgeWithEDO.put(edge, edoName.get(edoInMap));		
		}
	}

	private  static void loadVerticesAndVDOs(Graph graph) {
		// count the different vertex draw options
		int vdoCount = 1;
		
		// for each vertex
		for(Vertex vertex : graph.getVertices()) {
			// draw option of the current vertex
			VertexDrawOption currentVDO = vertex.getDrawOption();
			
			// look, if there is already a draw option
			// with the same settings
			VertexDrawOption vdoInMap = null;
			for(VertexDrawOption otherVDO : vdoName.keySet()){
				if(currentVDO.hasSameAttributesAs(otherVDO)) {
					// -> select it
					vdoInMap = otherVDO;
				}
			}
			
			// no draw option found, add the current one -> select it
			if(vdoInMap == null) {
				vdoInMap = currentVDO;
				vdoName.put(vdoInMap, "vertextype_" + vdoCount);
				vdoCount++;
			}
			
			// now add the selected draw option to the vertex definition
			vertexWithVDO.put(vertex, vdoName.get(vdoInMap));			
		}
	}
	
	private  static ArrayList<String> getAllEDODef() {
		// result
		ArrayList<String> allDef = new ArrayList<String>();
		
		// only add the section edgetype, if there is any type
		if(edoName.size() > 0) {
			allDef.add("edgetype");
		
			// add definition of all edge draw options
			for(EdgeDrawOption edo : edoName.keySet()) {
				String def = getEDODef(edo);
				allDef.add(def);
			}
		}
		return allDef;
	}
	
	private static String getEDODef(EdgeDrawOption edo) {
		String def = edoName.get(edo);
		
		Color color = edo.getColor();
		
		def += " " + ATTRIBUTE + " color: " + color.getRed()+", "+color.getGreen()+", "+color.getBlue();
		def += " " + ATTRIBUTE + " showText: " + edo.isShowText();
		def += " " + ATTRIBUTE + " shape: " + edo.getShape().toString();
		def += " " + ATTRIBUTE + " stroke: " + edo.getStroke().getLineWidth();
		def += " " + ATTRIBUTE + " arrowsize: " + edo.getArrowsize();
			
		return def;
	}
	
	private static ArrayList<String> getAllVDODef() {
		// result
		ArrayList<String> allDef = new ArrayList<String>();
		
		// only add the section vertextype, if there is any type
		if(vdoName.size() > 0) {
			allDef.add("vertextype");		
		
			// add definition of all vertex draw options
			for(VertexDrawOption vdo : vdoName.keySet()) {
				String def = getVDODef(vdo);
				allDef.add(def);
			}
		}
		return allDef;
	}
	
	
	private static String getVDODef(VertexDrawOption vdo) {
		String def = vdoName.get(vdo);
		
		Color incolor = vdo.getIncolor();
		Color outcolor = vdo.getOutcolor();
		
		def += " " + ATTRIBUTE + " size: " + vdo.getSize();
		def += " " + ATTRIBUTE + " incolor: " + incolor.getRed()+", "+incolor.getGreen()+", "+incolor.getBlue();
		def += " " + ATTRIBUTE + " outcolor: " + outcolor.getRed()+", "+outcolor.getGreen()+", "+outcolor.getBlue();
		def += " " + ATTRIBUTE + " showText: " + vdo.isShowText();
		def += " " + ATTRIBUTE + " stroke: " + vdo.getStroke().getLineWidth();
		def += " " + ATTRIBUTE + " shape: " + vdo.getShape().toString();
		
		return def;
	}
	
	private static ArrayList<String> getAllEdgeDef() {
		// result
		ArrayList<String> edgesDef = new ArrayList<String>();
		
		// only add this section, if there are any edges in this graph
		if(edgeWithEDO.size() > 0) {
			edgesDef.add("edges");
			
			for(Edge edge : edgeWithEDO.keySet()) {				
				String def = getEdgeDef(edge);
				edgesDef.add(def);
			}
		}
		return edgesDef;
	}
	
	private static String getEdgeDef(Edge edge) {
		// Name
		String def = "";
		def += edge.getSource().getName();
		def += ", ";
		def += edge.getDestination().getName();
		
		// Name
		if(!edge.getName().equals("")) {
			def += " " + ATTRIBUTE + " name: ";
			def += edge.getName(); 
		}
		
		// Weight
		def += " " + ATTRIBUTE + " weight: ";
		def += edge.getWeight();
				
		// Type
		def += " " + ATTRIBUTE + " type: ";
		def += edgeWithEDO.get(edge);
	
		// Curve Point
		def += " " + ATTRIBUTE + " curvePoint: ";
		def += edge.getCurvePoint().x + ", ";
		def += edge.getCurvePoint().y;
		
		return def;
	}
	
	private static ArrayList<String> getAllVertexDef() {
		// result
		ArrayList<String> verticesDef = new ArrayList<String>();
		
		// only add this section, if there are any free or specified vertices 
		if(vertexWithVDO.size() > 0) {
			verticesDef.add("vertices");
		
			// get definition for each vertex
			for(Vertex vertex : vertexWithVDO.keySet()) {
				String def = getVertexDef(vertex);
				verticesDef.add(def);
			}
		}
		
		return verticesDef;
	}
	
	private static String getVertexDef(Vertex vertex) {
		// Name
		String def = vertex.getName();
		
		// Position
		def += " " + ATTRIBUTE + " pos: ";
		def += vertex.getLocation().x;
		def += ", ";
		def += vertex.getLocation().y;
		
		if(vertex.isFixed()) {
			def += "!";
		}
			
		// Weight
		def += " " + ATTRIBUTE + " weight: ";
		def += vertex.getWeight();
			
		// Type
		def += " " + ATTRIBUTE + " type: ";
		def += vertexWithVDO.get(vertex);
				
		return def;
	}
}
