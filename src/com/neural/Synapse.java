package com.neural;

public class Synapse {
	private Neuron a;
	private Neuron b;
	private double weight;
	
	public Synapse(Neuron a, Neuron b, double weight)
	{
		this.a = a;
		this.b = b;
		this.weight = weight;
	}
	
	public Synapse(Neuron a, Neuron b)
	{
		this.a = a;
		this.b = b;
		this.weight = (Math.random() * 2) - 1; // random between -1.0 and 1.0
	}
	
	public Neuron getA()
	{
		return a;
	}
	
	public Neuron getB()
	{
		return b;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
}
