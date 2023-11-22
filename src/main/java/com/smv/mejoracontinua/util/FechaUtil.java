package com.smv.mejoracontinua.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class FechaUtil {

	public String obtenerFechaConHoraActual() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(date);
	}
	
	public String obtenerFechaActual() {
		return this.obtenerFechaConHoraActual().substring(0, 10);
	}
}
