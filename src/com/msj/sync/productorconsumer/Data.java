package com.msj.sync.productorconsumer;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/15 下午4:53
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc
 */
public final class Data {

	private String id;
	private String name;
	
	public Data(String id, String name){
		this.id = id;
		this.name = name;
	}
	
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

	@Override
	public String toString(){
		return "{id: " + id + ", name: " + name + "}";
	}
	
}
