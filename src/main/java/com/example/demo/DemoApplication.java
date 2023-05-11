package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {

	private String date;
	private String name;
	private String autor;    
    private String cantidadNumeros;    
    private String sumaNumeros; 	

	String fileName = "/usr/share/nginx/html/oscarmas";
    String nombre = "";
    int numCount = 0;
    int sum = 0;

	@CrossOrigin(origins = "*")
	@GetMapping("/")
	public Values getInfo() throws UnknownHostException {				
		Values values = new Values();
		getHora();
		getName();				
		numCount=0;
		sum=0;
		metodoParcial();
		values.setDate(date);
		values.setName(name);		
		values.setAutor(autor);
		values.setCantidadNumeros(cantidadNumeros);
		values.setSumaNumeros(sumaNumeros);
		return values;
	}

	public void getHora() {
		ZoneId zonaHorariaNY = ZoneId.of("America/Bogota");
		ZonedDateTime fechaHoraNY = ZonedDateTime.now(zonaHorariaNY);	
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z");
		String fechaHoraFormateada = fechaHoraNY.format(formato);		
		date = fechaHoraFormateada;
	}

	public void getName() throws UnknownHostException {
		name = InetAddress.getLocalHost().getHostName();
	}

    
	public void metodoParcial(){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Leer el nombre de la primera línea
            nombre = br.readLine();
            // Leer los números y calcular la suma
            String line;
            while ((line = br.readLine()) != null) {
                int num = Integer.parseInt(line.trim());
                numCount++;
                sum += num;
            }
        } catch (IOException e) {
            System.err.format("Error al leer el archivo: %s%n", e);
        }
        // Mostrar los resultados
		autor = nombre;
		cantidadNumeros = String.valueOf(numCount);
		sumaNumeros = String.valueOf(sum);
    }
	
	public void resetValues(){
		
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);		
	}
}
