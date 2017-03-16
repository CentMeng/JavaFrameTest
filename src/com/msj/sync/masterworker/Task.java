package com.msj.sync.masterworker;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/15 下午3:25
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc Master-Worker模式 任务
 */
public class Task {

	private int id;
	private int price ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	} 
	
}
