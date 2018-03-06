package com.YooFood.images.model;

import java.io.Serializable;

public class Imagen64Model implements Serializable{

	private String id;
	private String encodedImage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEncodedImage() {
		return encodedImage;
	}
	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}
	
	
}
