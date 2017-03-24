package com.msj.network.netty.serial;

import java.io.Serializable;

/**
 * 真实情况，如果服务端和客户端是两个服务，则服务端和客户端都需要此对象，并且包名，类名要一样
 */
public class Resp implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String responseMessage;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	

}
