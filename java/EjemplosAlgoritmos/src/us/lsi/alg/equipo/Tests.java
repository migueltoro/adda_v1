package us.lsi.alg.equipo;

import java.util.Locale;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Tests {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		tests("ficheros/DatosEquipo1.txt");
		tests("ficheros/DatosEquipo2.txt");
		tests("ficheros/DatosEquipo3.txt");
	}

	private static void tests(String fichero) {
		DatosEquipo.iniDatos(fichero);
		
		SimpleVirtualGraph<EquipoVertex, EquipoEdge> grafoPDR = 
		Graphs2.simpleVirtualGraphSum(EquipoVertex.first(),EquipoVertex::goal, null,v->true, e-> e.weight());
		
		SimpleVirtualGraph<EquipoVertex, EquipoEdge> grafoBT = 
				Graphs2.simpleVirtualGraphSum(EquipoVertex.first(),EquipoVertex::goal, null,v->true, e-> e.weight());
		
		SimpleVirtualGraph<EquipoVertex, EquipoEdge> grafoAStar = 
		Graphs2.simpleVirtualGraphSum(EquipoVertex.first(), EquipoVertex::goal, null,v->true, e-> -e.weight());
		
		testPDR(grafoPDR);
		testBT(grafoBT);
		testAStar(grafoAStar);
	}

	
	private static void testPDR(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== PDR ======================== ");
		DynamicProgrammingReduction<EquipoVertex, EquipoEdge> alg_pdr = DPR.dynamicProgrammingReduction( 
			grafo,
		EquipoHeuristic::heuristica2,
//			(v1,p,v2)->100.,
			PDType.Max);
		System.out.println(EquipoVertex.getSolucion(alg_pdr.search().get()));
	}
	
	private static void testBT(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== BT ======================== ");
		BackTracking<EquipoVertex, EquipoEdge,SolucionEquipo> alg_pdr = BT.backTracking(
			grafo,
			EquipoHeuristic::heuristica2,
//			(v1,p,v2)->100.,
			EquipoVertex::getSolucion,
			EquipoVertex::copy,
			BTType.Max);
		alg_pdr.search();
		System.out.println(alg_pdr.getSolution());
	}

	private static void testAStar(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== A* ======================== ");
		AStar<EquipoVertex, EquipoEdge> alg_star = GraphAlg.aStar(
			grafo,
			EquipoHeuristic::heuristica_neg
//			(v1,p,v2)->-100.
			);
		System.out.println(EquipoVertex.getSolucion(alg_star.search().get()));
	}

}