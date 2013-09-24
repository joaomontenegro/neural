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
