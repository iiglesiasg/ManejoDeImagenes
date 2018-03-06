package com.YooFood.images.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.YooFood.images.model.Imagen64Model;
import com.YooFood.images.model.PublicacionModel;

@RestController
@RequestMapping("/images")
public class ImagesController {

	private String ruta = "C:\\imagenes";
	
	@CrossOrigin
	@RequestMapping(value = "/upload/publicacion/{publicacion}", method = RequestMethod.POST)
	public @ResponseBody PublicacionModel post(@RequestParam("file") MultipartFile archivo,@PathVariable String publicacion) {
		
		System.out.println("inicia carga imagen");
		PublicacionModel result=null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		String nombreFoto = publicacion +"_"+ LocalDateTime.now().format(formatter);
		String filename = ruta + File.separator + nombreFoto+ ".jpeg";
		
		try {
			BufferedImage image = ImageIO.read(archivo.getInputStream());
			
			int y = image.getHeight();
			int x = image.getWidth();
			if(y>x) {
				if(y>500) {
					x=x*500/y;
					y=500;
				}							
			}else if(x>y) {
				if(x>500) {
					y=y*500/x;
					x=500;
				}			
			}else {
				if(x>500) {
					x=500;
					y=500;
				}				
			}
			BufferedImage resized = resize(image, y, x);
			
			File output = new File(filename);
	        ImageIO.write(resized, "jpeg", output);
	        System.out.println("finaliza carga imagen");
	        
	        final String uri = "http://localhost:8092/publicaciones/imagen/"+nombreFoto+"/publicacion/"+publicacion;
	      //  RestTemplate restTemplate = obtenerRestSSL();
	        /*
	    //    TrustStrategy a; 
	        System.out.println("comienza comunicacion Rest + ssl");
	        SSLContext sslContext = SSLContextBuilder
	        		.create().
	        	//	.loadKeyMaterial(ResourceUtils.getFile("classpath:keystore.p12"), "equiporeapro".toCharArray(),"equiporeapro".toCharArray())
	        		.loadTrustMaterial(ResourceUtils.getFile("classpath:keystore.p12"),"equiporeapro".toCharArray())
	        		.build();
	        
	        SSLConnectionSocketFactory socketFactory = 
	                new SSLConnectionSocketFactory(sslContext);
	        
	        HttpClient client = HttpClients.custom()
	                .setSSLContext(sslContext)
	                .build();
	        HttpClient httpClient = HttpClients.custom()
	                .setSSLSocketFactory(socketFactory).build();
	        HttpComponentsClientHttpRequestFactory factory = 
	                new HttpComponentsClientHttpRequestFactory(httpClient);
	        
		 */ RestTemplate restTemplate = new RestTemplate(/*new HttpComponentsClientHttpRequestFactory(httpClient)*/);	 
		    result = restTemplate.getForObject(uri, PublicacionModel.class);
		    
		    System.out.println("imagen asociada a publicacion");
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/upload/publicacion64", method = RequestMethod.POST)
	public @ResponseBody PublicacionModel post64(@RequestBody Imagen64Model imagen) {
		
		System.out.println("inicia carga imagen");
		PublicacionModel result=null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		String nombreFoto = imagen.getId() +"_"+ LocalDateTime.now().format(formatter);
		String filename = ruta + File.separator + nombreFoto+ ".jpeg";
		
		byte[] imageByte=Base64.decodeBase64(imagen.getEncodedImage());
		
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageByte));
			
			int y = image.getHeight();
			int x = image.getWidth();
			if(y>x) {
				if(y>500) {
					x=x*500/y;
					y=500;
				}							
			}else if(x>y) {
				if(x>500) {
					y=y*500/x;
					x=500;
				}			
			}else {
				if(x>500) {
					x=500;
					y=500;
				}				
			}
			BufferedImage resized = resize(image, y, x);
			
			File output = new File(filename);
	        ImageIO.write(resized, "jpeg", output);
	        System.out.println("finaliza carga imagen");
			
	        final String uri = "http://localhost:8092/publicaciones/imagen/"+nombreFoto+"/publicacion/"+imagen.getId();
	        
	        RestTemplate restTemplate = new RestTemplate(/*new HttpComponentsClientHttpRequestFactory(httpClient)*/);	 
		    result = restTemplate.getForObject(uri, PublicacionModel.class);
		    
		    System.out.println("imagen asociada a publicacion");
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return result;
	}
	
	@RequestMapping(value = "/download/imagen/{imagen}", method = RequestMethod.GET)
	public ResponseEntity download(@PathVariable String imagen) throws IOException {
	    String filePath = ruta + File.separator + imagen+".jpeg";
	    InputStream inputStream = new FileInputStream(new File(filePath));
	    InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentLength(Files.size(Paths.get(filePath)));
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
	}
	
	private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
	
	
	private static RestTemplate obtenerRestSSL() {
		
		
		
		
		return null;
		
	}
}
