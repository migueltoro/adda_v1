package us.lsi.alg.typ.manual;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;

public record TyPProblem(Integer index,List<Integer> cargas, Integer maxCarga,Integer npMax,Integer npMin) {

	public static TyPProblem of(Integer index,List<Integer> cargas) {
		List<Integer> cargasC = Collections.unmodifiableList(cargas);
		Integer npMax = IntStream.range(0,DatosTyP.m)
				.boxed()
				.max(Comparator.comparing(i->cargasC.get(i)))
				.get();
		Integer npMin = IntStream.range(0,DatosTyP.m)
				.boxed()
				.min(Comparator.comparing(i->cargasC.get(i)))
				.get();	
		Integer maxCarga = cargasC.get(npMax);
		return new TyPProblem(index,cargasC,maxCarga,npMax,npMin);
	}
	
	public static TyPProblem first() {
		return TyPProblem.of(0,Lists2.copy(0,DatosTyP.m));
	}
	
	public List<Integer> acciones() {
		if(this.index == DatosTyP.n) return Lists2.of();
		return Lists2.rangeList(0,DatosTyP.m);
	}
	
	public Integer greadyAction() {
		return this.npMin();
	}
	
	public TyPProblem vecino(Integer a) {
		Integer nd = this.cargas().get(a)+DatosTyP.tareas.get(this.index).duracion(); 
		List<Integer> nc = Lists2.setElement(this.cargas(),a,nd);
		TyPProblem v = TyPProblem.of(index+1, nc);
		return v;
	}
	
}