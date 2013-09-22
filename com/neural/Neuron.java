package com.neural;

import java.util.Iterator;
import java.util.Vector;

public abstract class Neuron {

	protected Vector<Synapse> inputSynapses;
	protected Vector<Synapse> outputSynapses;
	
	public abstract double getOutput();
		
	public Neuron()
	{
		this.inputSynapses = new Vector<Synapse>();
		this.outputSynapses = new Vector<Synapse>();
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
}
