package com.mx.Personas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mx.Personas.dao.PersonaDao;
import com.mx.Personas.dto.Respuesta;
import com.mx.Personas.entidad.Persona;

@Service
public class PersonaService {
	@Autowired
	PersonaDao dao;
	
	public ResponseEntity<List<Persona>> getPersonaAll(){
		if(dao.findAll().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(dao.findAll());
	}
	
	public Respuesta guardar(Persona persona) {
		Respuesta rs = new Respuesta();
		try {
			if(dao.existsById(persona.getCurp())) {
				rs.setMensaje("La persona no se agrego a la base de datos");
				rs.setSuccess(false);
				rs.setObj(persona.getCurp());
				return rs;
			}
			dao.save(persona);
			rs.setMensaje("La persona se agrego a la base");
			rs.setSuccess(true);
			rs.setObj(persona);
			return rs;
		}catch(Exception e) {
			rs.setMensaje("Error intenta mas tarde");
			rs.setSuccess(false);
			rs.setObj(persona);
			return rs;
		}
		
	}
	
	public Respuesta editar(Persona persona) {
		Respuesta rs = new Respuesta();
		try {
			if(dao.existsById(persona.getCurp())) {
				dao.save(persona);
				rs.setMensaje("La persona ha sido editada");
				rs.setSuccess(true);
				rs.setObj(persona);
				return rs;
			}
			rs.setMensaje("La persona que se edito no existe");
			rs.setObj(persona.getCurp());
			rs.setSuccess(false);
			return rs;
		}catch(Exception e) {
			rs.setMensaje("Error al editar");
			rs.setSuccess(false);
			rs.setObj(persona);
			return rs;
		}
	}
	
	public Respuesta eliminar(Persona persona) {
		Respuesta rs = new Respuesta();
		try {
			if(dao.existsById(persona.getCurp())) {
				rs.setObj(dao.findById(persona.getCurp()));
				dao.delete(persona);
				rs.setMensaje("La persona ha sido eliminada");
				rs.setSuccess(true);
				return rs;
			}
			rs.setMensaje("La persona no existe");
			rs.setSuccess(false);
			rs.setObj(persona.getCurp());
			return rs;
		}catch (Exception e) {
			rs.setMensaje("Error al eliminar ");
			rs.setSuccess(false);
			rs.setObj(persona.getCurp());
			return rs;
		}
	}
	
	public ResponseEntity<Persona> getPersona(String curp){
		Persona persona = dao.findById(curp).orElse(null);
		if(persona == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(persona);
	}
}
