package com.neural;

import java.util.Iterator;
import java.util.Vector;

public class Neuron 
{
	public static int lastId = 0;
	public int id;	

	protected Vector<Synapse> inputSynapses;
	protected Vector<Synapse> outputSynapses;
	
	private ActivationFunction func;
	
	public Neuron()
	{
		this.inputSynapses = new Vector<Synapse>();
		this.outputSynapses = new Vector<Synapse>();
		this.func = new LinearActivationFunction();
		this.id = lastId++; 
	}
	
	public Neuron(ActivationFunction func)
	{
		this();
		this.func = func;
	}
	
	public String toString()
	{
		return "N" + id;
	}
	
	public void addInputSynapse(Synapse s)
	{
		inputSynapses.add(s);
	}
	
	public void addOutputSynapse(Synapse s)
	{
		outputSynapses.add(s);
	}
	
	public Iterator<Synapse> getInputsIterator()
	{
		return inputSynapses.iterator();
	}
	
	public Iterator<Synapse> getOutputsIterator()
	{
		return outputSynapses.iterator();
	}	
	
	public static Synapse connect(Neuron a, Neuron b, double weight)
	{
		Synapse syn = new Synapse(a, b, weight);
		
		a.addOutputSynapse(syn);
		b.addInputSynapse(syn);
		
		return syn;
	}
	
	public static Synapse connect(Neuron a, Neuron b)
	{
		return connect(a, b, Math.random() - 0.5); // random weight between [-0.5, 0.5]
	}
	
	public ActivationFunction getActivationFunction()
	{
		return func;
	}
}
