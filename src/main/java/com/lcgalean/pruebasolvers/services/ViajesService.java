package com.lcgalean.pruebasolvers.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lcgalean.pruebasolvers.controller.ViajesContrroller;

@Service
public class ViajesService {
	
	@Value("${minimoDias}")
	private int minimoDias;
	
	@Value("${maximoDias}")
	private int maximoDias;
	
	@Value("${minimoNumeroObjetosDia}")
	private int minimoNumeroObjetosDia;
	
	@Value("${maximoNumeroObjetosDia}")
	private int maximoNumeroObjetosDia;
	
	@Value("${minimoPesoOjeto}")
	private int minimoPesoOjeto;
	
	@Value("${maximoPesoObjeto}")
	private int maximoPesoObjeto;



	private static final Logger LOGGER=LoggerFactory.getLogger(ViajesContrroller.class);


	public String calcularNumeroMaximoViajes(String datosEntrada) {		
		String[] datosSeparados = separarString(datosEntrada);
		List<String> listaDatosSeparados = Arrays.asList(datosSeparados);
		List<Integer> listadeEnteros = obtenerListaEnterosDeListaString(listaDatosSeparados);		
		String viajesDia = calcularNumeroViajesDia(listadeEnteros);		
		return viajesDia;
	}

	public String[] separarString(String string) {
		String[] StringSeparado = string.split("\n");
		return StringSeparado;
	}

	public List<Integer> obtenerListaEnterosDeListaString(List<String> listadeString){
		List<Integer> ListaEnteros = new ArrayList<>();		
		ListaEnteros = listadeString.stream(
				).map(Integer::parseInt).collect(Collectors.toList());		
		return ListaEnteros;	
	}

	public String calcularNumeroViajesDia(List<Integer> listadeEnteros){
		int iterador = 2;
		int diaActual = 1;
		int numeroElementos = listadeEnteros.get(1);
		int proximoDia = numeroElementos + 2;
		List<Integer> ObjetosDia = new ArrayList<>();
		int finDatos = listadeEnteros.size();
		String respuesta = "";
		if (listadeEnteros.get(0)<minimoDias|listadeEnteros.get(0)>maximoDias) {
			return "El numero de dias debe ser mayor que "+minimoDias+" y menor que "+maximoDias;
		}
		while(iterador<=proximoDia) {
			if(iterador==proximoDia) {						
				LOGGER.info( "Objetos del dia  " + ObjetosDia.toString());
				if(ObjetosDia.size()<minimoNumeroObjetosDia|ObjetosDia.size()>maximoNumeroObjetosDia) {
					return "El numero de elementos por dia debe ser mayor que "+minimoNumeroObjetosDia+" y menor que "+maximoNumeroObjetosDia;
				}
				
				boolean validarPeso = validarPeso(ObjetosDia);
				if(validarPeso == false) {
					return "El peso de cada elemento debe estar mayor  "+minimoPesoOjeto+" y menor que "+maximoPesoObjeto;				
				}
				int maximoNumeroDeViajes = clacularMaximoNumeroViajes(ObjetosDia);
				respuesta = respuesta +"Case #"+diaActual+": "+maximoNumeroDeViajes + "\n";
				diaActual++;
			
				LOGGER.info( "Maximo numero de viajes " + maximoNumeroDeViajes);
				if( proximoDia == finDatos){				
					iterador++;						
				}else {
					proximoDia =  proximoDia + listadeEnteros.get(proximoDia)+1;
					iterador++;
					
					ObjetosDia.clear();						
				}					
			}else {				
				ObjetosDia.add(listadeEnteros.get(iterador));
											
				iterador++;
			}
		}
		LOGGER.info(respuesta);		
		return respuesta;
	}

	public int clacularMaximoNumeroViajes(List<Integer> listadeObjetos) {
		Collections.sort(listadeObjetos,Collections.reverseOrder());		
		int pesoMinimo = 50;
		int pesoEnBolsa = 0;
		int elementosEnBolsa = 1;
		int NumeroDeViajes  = 0;	

		while(listadeObjetos.size() != 0) {
			if(listadeObjetos.get(0) >=pesoMinimo) {
				
				listadeObjetos.remove(0);
				NumeroDeViajes++;				
			}else {
				pesoEnBolsa = listadeObjetos.get(0)*elementosEnBolsa;
				if(pesoEnBolsa >= pesoMinimo ) {
					NumeroDeViajes++;
					pesoEnBolsa = 0;
					listadeObjetos.remove(0);
					elementosEnBolsa = 1;
				}else {
					elementosEnBolsa++;
					listadeObjetos.remove(listadeObjetos.size()-1);
				}				
			}				
		}		
		return NumeroDeViajes;
	}
	
	boolean validarPeso(List<Integer> ObjetosDia) {
		boolean pesoValidado = true;
		for(int i=0;i<=ObjetosDia.size()-1;i++) {			
			if(ObjetosDia.get(i)<minimoPesoOjeto|ObjetosDia.get(i)>maximoPesoObjeto) {
				pesoValidado =  false;
			}			
		}	
		return pesoValidado;
	}
}
