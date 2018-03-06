package com.YooFood.images.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class PublicacionModel implements Serializable{
	
	private String id;
	@ApiModelProperty(value = "Codigo del usuario", required = true)
	private String usrname;
	private String usr_photo;
	@ApiModelProperty(value = "Codigo de localizacion", required = true)
	private String codlocation;
	private String loc_photo;
	private String url_photo;
	private Integer pht_likes;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss",timezone = "GMT-05:00")
	private Date pht_date;
	
	private String pht_name;
	private String pht_description;
	private Boolean pht_liked;
	private List<String> pht_comments;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsrname() {
		return usrname;
	}
	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}
	public String getUsr_photo() {
		return usr_photo;
	}
	public void setUsr_photo(String usr_photo) {
		this.usr_photo = usr_photo;
	}
	public String getCodlocation() {
		return codlocation;
	}
	public void setCodlocation(String codlocation) {
		this.codlocation = codlocation;
	}
	public String getLoc_photo() {
		return loc_photo;
	}
	public void setLoc_photo(String loc_photo) {
		this.loc_photo = loc_photo;
	}
	public String getUrl_photo() {
		return url_photo;
	}
	public void setUrl_photo(String url_photo) {
		this.url_photo = url_photo;
	}
	public Integer getPht_likes() {
		return pht_likes;
	}
	public void setPht_likes(Integer pht_likes) {
		this.pht_likes = pht_likes;
	}
	public Date getPht_date() {
		return pht_date;
	}
	public void setPht_date(Date pht_date) {
		this.pht_date = pht_date;
	}
	public String getPht_name() {
		return pht_name;
	}
	public void setPht_name(String pht_name) {
		this.pht_name = pht_name;
	}
	public String getPht_description() {
		return pht_description;
	}
	public void setPht_description(String pht_description) {
		this.pht_description = pht_description;
	}
	public Boolean getPht_liked() {
		return pht_liked;
	}
	public void setPht_liked(Boolean pht_liked) {
		this.pht_liked = pht_liked;
	}
	public List<String> getPht_comments() {
		return pht_comments;
	}
	public void setPht_comments(List<String> pht_comments) {
		this.pht_comments = pht_comments;
	}
	
	
}
