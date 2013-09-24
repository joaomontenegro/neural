package com.neural;

public class InputNeuron extends Neuron
{
	private double value;
	
	public InputNeuron(double value)
	{
		super();
		setValue(value);
	}
	
	public void setValue(double value)
	{
		this.value = value;
	}
	
	public double getOutput() 
	{
		return value;
	}
}