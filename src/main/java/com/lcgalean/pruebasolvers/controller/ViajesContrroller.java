package com.lcgalean.pruebasolvers.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lcgalean.pruebasolvers.services.ViajesService;


@RestController
@RequestMapping("/viajes")
public class ViajesContrroller {
	
	@Autowired
	private ViajesService viajesService;
	
	@PostMapping("/ViajesMaximos")
	public ResponseEntity<?> ObtenerMaximoViajes(@RequestParam("file") MultipartFile file) {
			String respuesta = "";
			try {
				String datosEntrada = new String(file.getBytes());				
				respuesta = viajesService.calcularNumeroMaximoViajes(datosEntrada);			
			} catch (IOException e) {				
				e.printStackTrace();				
			}
			return new ResponseEntity<String>(respuesta, HttpStatus.OK);
			
	}
	
}
