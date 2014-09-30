package external;

import graph.drawing.*;
import graph.implementation.*;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Use this class to load graphs from a file.
 * It is a specific class and loads only specific file structure,
 * the {@link GraphSaver} can create it.
 * To load from a file, it uses {@link DataReader}.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 01.04.2014
 */
public final class GraphLoader {

	// This hash maps the class needs to not create double entries
	private static HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
	private static HashMap<String, Edge> edges = new HashMap<String, Edge>();
	private static HashMap<String, VertexDrawOption> vdos = new HashMap<String, VertexDrawOption>();
	private static HashMap<String, EdgeDrawOption> edos = new HashMap<String, EdgeDrawOption>();
	private static String TEMPFILE = "_graphAnimation.tempani_";
	
	// This is a abstract class, not instanced, no subclasses
	private GraphLoader(){}
	
	
	public static Graph loadGraph(String pathname) {
		// load file
		DataReader.loadData(pathname);
		
		// Create a new file
		File file = new File(pathname);
		
		// Create a new graph
		Graph graph = new Graph();
		graph.setName(file.getName());
		
		// is the graph directed?
		boolean directed = DataReader.lineExists("->");
		
		// load vertex draw options (hash maps)
		loadVDOs();
		
		// load edge draw options (hash maps)
		loadEDOs();
			
		// load single vertices (free or specified)		
		loadSingleVertices(graph);
		
		// load edges and their source and destination vertices
		loadEdgesAndVertices(graph, directed);		
		
		// clear hash maps
		clearAllLists();
		
		return graph;
	}
	
	public static ArrayList<Graph> loadAnimation(String path) {
		// get definition of all graphs
		DataReader.loadData(path);
		String[] content = DataReader.getContentAsStringArray();
		
		// result
		ArrayList<Graph> graphs = new ArrayList<Graph>();
		
		// open temp file
		DataWriter.openFile(TEMPFILE);
		
		// to mark the end of the file
		final int LAST_ROW = content.length;
		int currentRow = 1;
		
		
		for(String row : content) {
			
			// Search for delimiter (more than one graph)
			if(row.equals(GraphSaver.NEXT_GRAPH)) {
				// end writing
				DataWriter.close();
				
				// load the new graph
				graphs.add(loadGraph(TEMPFILE));
				
				// open (and clear) tempfile again
				DataWriter.openFile(TEMPFILE);
			}
			
			// last line
			else if (currentRow == LAST_ROW) {
				// write into tempfile
				DataWriter.writeln(row);
				
				// end writing
				DataWriter.close();
				
				// load the new graph
				graphs.add(loadGraph(TEMPFILE));
			}
			
			else {
				// write into tempfile
				DataWriter.writeln(row);
			}
			
			currentRow++;
		}
		
		return graphs;
	}

	private static void loadVDOs() {	
		// get definitions
		String[][] defVDO = DataReader.getDataAsStringArray("vertextype", GraphSaver.ATTRIBUTE, true);
		
		// each line = one draw option
		for(String[] line : defVDO) {
			String name = line[0];
			
			// its a new draw option
			if(!vdos.containsKey(name)) {
				VertexDrawOption nextVDO = new VDO_StandardText();
				// set attributes
				addVDOProperties(nextVDO, line);
				// add to hash map
				vdos.put(name, nextVDO);
			}		
		}
	}
	
	private static void addVDOProperties(VertexDrawOption vdo, String[] line) {
		// there are attributes
		if(line.length > 1) {
			// for each
			for(int i = 1; i < line.length; i++) {
				// split them
				String[] prop = line[i].split(":|,");			
				addVDOPropertie(vdo, prop);
			}
		}
	}
	
	private static void addVDOPropertie(VertexDrawOption vdo, String[] prop) {
		// first is the name
		String attribute = prop[0];
		
		if(attribute.equalsIgnoreCase("size")) {
			vdo.setSize(Integer.parseInt(prop[1]));
		}
		if(attribute.equalsIgnoreCase("outcolor")) {
			int r = Integer.parseInt(prop[1]);
			int g = Integer.parseInt(prop[2]);
			int b = Integer.parseInt(prop[3]);
			vdo.setOutcolor(new Color(r, g, b));
		}
		if(attribute.equalsIgnoreCase("incolor")) {
			int r = Integer.parseInt(prop[1]);
			int g = Integer.parseInt(prop[2]);
			int b = Integer.parseInt(prop[3]);
			vdo.setIncolor(new Color(r, g, b));
		}
		if(attribute.equalsIgnoreCase("stroke")){
			int size = (int) Double.parseDouble(prop[1]);
			vdo.setStroke(size);
		}
		if(attribute.equalsIgnoreCase("showText")) {
			if(prop[1].equalsIgnoreCase("true")) {
				vdo.setShowText(true);
			}
			else {
				vdo.setShowText(false);
			}
		}
		
		if(attribute.equalsIgnoreCase("shape")){			
			String typename = prop[1];
			
			if(typename.equalsIgnoreCase("Round")) {
				vdo.setShape(new VS_Round());
			}
			if(typename.equalsIgnoreCase("Rect")) {
				vdo.setShape(new VS_Rect());
			}
			if(typename.equalsIgnoreCase("StartState")) {
				vdo.setShape(new VS_StartState());
			}
			if(typename.equalsIgnoreCase("EndState")) {
				vdo.setShape(new VS_EndState());
			}
		}
	}
	
	private static void loadEDOs() {
		// get definitions
		String[][] defEDO = DataReader.getDataAsStringArray("edgetype", GraphSaver.ATTRIBUTE, true);
		
		// each line = one draw option
		for(String[] line : defEDO) {
			String name = line[0];
			
			if(!edos.containsKey(name)) {
				EdgeDrawOption nextEDO = new EDO_Normal();
				// set attributes
				addEDOProperties(nextEDO, line);
				// add to hash map
				edos.put(name, nextEDO);
			}		
		}
	}
	
	private static void addEDOProperties(EdgeDrawOption edo, String[] line) {
		// there are attributes
		if(line.length > 1) {
			// for each
			for(int i = 1; i < line.length; i++) {
				// split
				String[] prop = line[i].split(":|,");
				addEDOPropertie(edo, prop);
			}
		}
	}
	
	private static void addEDOPropertie(EdgeDrawOption edo, String[] prop){
		// first is the name of the attribute
		String attribute = prop[0];
		
		if(attribute.equalsIgnoreCase("color")) {
			int r = Integer.parseInt(prop[1]);
			int g = Integer.parseInt(prop[2]);
			int b = Integer.parseInt(prop[3]);
			edo.setColor(new Color(r,g,b));
		}
		if(attribute.equalsIgnoreCase("showText")) {
			if(prop[1].equalsIgnoreCase("true")) {
				edo.setShowText(true);
			}
			else {
				edo.setShowText(false);
			}
		}
		if(attribute.equalsIgnoreCase("stroke")){
			int size = (int) Double.parseDouble(prop[1]);
			edo.setStroke(size);
		}
		if(attribute.equalsIgnoreCase("arrowsize")){
			int size = (int) Double.parseDouble(prop[1]);
			edo.setArrowsize(size);
		}
	}
	
	
	
	
	private static void loadSingleVertices(Graph graph) {
		// get definitions
		String[][] defVertices = DataReader.getDataAsStringArray("vertices", GraphSaver.ATTRIBUTE, true);
		
		// each line = one vertex 
		for(String[] vLine : defVertices) {
			String name = vLine[0];
			
			// Does not exist yet
			if(!vertices.containsKey(name)) {
				Vertex vertex = new Vertex(name);
				// add attributes
				addVertexProperties(vertex, vLine);
				// add to graph
				graph.addVertex(vertex);
				// add to hash map
				vertices.put(name, vertex);
			}
		}
	}
	
	private static void addVertexProperties(Vertex vertex, String[] line) {
		// there are attributes
		if(line.length > 1) {	
			
			for(int i = 1; i < line.length; i++) {
				// split
				String[] prop = line[i].split(":|,");	
				addVertexPropertie(vertex, prop);
			}
		}
	}
	
	private static void addVertexPropertie(Vertex vertex, String[] prop){
		String attribute = prop[0];
		
		if(attribute.equalsIgnoreCase("pos")) {
			if(prop[2].endsWith("!")){
				vertex.setFixed(true);
			}
			int x = Integer.parseInt(prop[1]);
			int y = Integer.parseInt(prop[2].replace("!", ""));
			vertex.setLocation(new Point(x, y));
		}
		
		if(attribute.equalsIgnoreCase("weight")){
			int weight = Integer.parseInt(prop[1]);
			vertex.setWeight(weight);
		}
		
		if(attribute.equalsIgnoreCase("type")) {
			String typename = prop[1];
			vertex.setDrawOption(new VertexDrawOption(vdos.get(typename)));
		}
	}
	
	
	private static void loadEdgesAndVertices(Graph graph, boolean directed) {
		
		// get definitions
		String[][] defEdges = DataReader.getDataAsStringArray("edges", GraphSaver.ATTRIBUTE, true);
		
		// each line = one vertex 
		for(String[] edgeRow : defEdges) {
			// extract start and end vertex
			String[] startend = edgeRow[0].split(",");
			String startName = startend[0];
			String endName = startend[1]; 

			// the start vertex does not exist in the hash map -> add
			if(!vertices.containsKey(startName)) {
				Vertex a = new Vertex(startName);
				vertices.put(startName, a);
			}
			
			// the start vertex does not exist in the hash map -> add
			if(!vertices.containsKey(endName)) {
				Vertex b = new Vertex(endName);
				vertices.put(endName, b);
			}

			// create edge
			Edge edge;
			edge = new Edge(vertices.get(startName), vertices.get(endName), directed);
			
			// add attributes
			addEdgeProperties(edge, edgeRow);
			// add edge to graph
			graph.addEdge(edge);
			// add edge to hash map
			edges.put(edgeRow[0], edge);			
		}	
	}
	

	private static void addEdgeProperties(Edge edge, String[] line) {
		// there are attributes
		if(line.length > 1) {	
			
			for(int i = 1; i < line.length; i++) {
				// split them
				String[] prop = line[i].split(":|,");		
				addEdgePropertie(edge, prop);
			}
		}
	}
	
	private static void addEdgePropertie(Edge edge, String[] prop) {
		String attribute = prop[0];
		
		if(attribute.equalsIgnoreCase("type")) {
			String typename = prop[1];
			EdgeDrawOption edo = edos.get(typename);
			edge.setDrawOption(new EdgeDrawOption(edo));
		}
		
		if(attribute.equalsIgnoreCase("weight")) {
			
			int weight = Integer.parseInt(prop[1]);	

			edge.setWeight(weight);
		}
		
		if(attribute.equalsIgnoreCase("curvePoint")) {
			int x = Integer.parseInt(prop[1]);
			int y = Integer.parseInt(prop[2]);
			edge.setCurvePoint(new Point(x,y));
		}
		
		if(attribute.equalsIgnoreCase("name")) {
			edge.setName(prop[1]);
		}
	}
	
	private static void clearAllLists() {
		vertices.clear();
		edges.clear();
		vdos.clear();
		edos.clear();
	}
}
