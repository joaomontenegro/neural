package com.neural;

import java.util.Vector;

public class NeuralNetwork {
	Vector<InputNeuron> inputs;
	Vector<LayerNeuron> outputs;
	
	public NeuralNetwork() 
	{
		inputs = new Vector<InputNeuron>();
		outputs = new Vector<LayerNeuron>();
	}
	
	public int addInputNeuron(InputNeuron n)
	{
		inputs.add(n);
		return inputs.size();
	}
	
	public int addOutputNeuron(LayerNeuron n)
	{
		outputs.add(n);
		return outputs.size();
	}
	
	public void setInput(int n, double value) throws ArrayIndexOutOfBoundsException
	{
		if (n > inputs.size())
		{
			throw new ArrayIndexOutOfBoundsException("Neural Network Inputs < " + n);
		}
		
		InputNeuron inNeuron = inputs.get(n);
		inNeuron.setValue(value);
	}
	
	public double getOutput(int n) throws ArrayIndexOutOfBoundsException
	{
		if (n > outputs.size())
		{
			throw new ArrayIndexOutOfBoundsException("Neural Network Outputs < " + n);
		}
		
		LayerNeuron outNeuron = outputs.get(n);
		return outNeuron.getOutput();
	}
}
