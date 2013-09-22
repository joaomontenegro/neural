package com.neural;

import java.util.Iterator;

public class LayerNeuron extends Neuron
{
	private TransferFunction func;
	
	public LayerNeuron(TransferFunction func)
	{
		super();
		this.func = func;
	}
	
	private double weightSumInputs()
	{
		double sum = 0;
		Iterator<Synapse> it = inputSynapses.iterator();
		
		while (it.hasNext())
		{
			Synapse syn = it.next();
			sum += syn.getA().getOutput() * syn.getWeight();
		}
		
		return sum;
	}
	
	
	public double getOutput() 
	{		
		double sum = weightSumInputs();
		return func.calculate(sum);
	}
}
