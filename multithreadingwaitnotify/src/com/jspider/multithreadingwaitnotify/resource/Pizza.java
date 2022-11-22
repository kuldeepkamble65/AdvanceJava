package com.jspider.multithreadingwaitnotify.resource;

public class Pizza {
	int noOfPizza;

	public Pizza(int noOfPizza) {
		super();
		this.noOfPizza = noOfPizza;
	}

	public int checkPizza() {
		return this.noOfPizza;	
				
	}
	public synchronized void orderPizza(int noOfPizza) {
		System.out.println("Ordering"+ noOfPizza+"Pizza");
		
		if(noOfPizza > checkPizza()) {
			try {
				System.out.println(noOfPizza+"not available please Wait");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.noOfPizza -= noOfPizza;
		System.out.println(checkPizza()+ "Pizzas available");
	}
	public synchronized void bakePizza(int noOfPizza) {
		this.noOfPizza += noOfPizza;
		System.out.println(checkPizza()+ "Pizzas avaible");
	}
}
