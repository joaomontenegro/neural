package com.neural;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class NeuralNetwork {
	public NeuronsList inputNeurons;
	public NeuronsList hiddenNeurons;
	public NeuronsList outputNeurons;
	public HashMap<Neuron, Double> neuronOutputs;
	public HashMap<Neuron, Double> neuronErrors;

	public NeuralNetwork() 
	{
		inputNeurons  = new NeuronsList();
		hiddenNeurons = new NeuronsList();
		outputNeurons = new NeuronsList();
		neuronOutputs = new HashMap<Neuron, Double>();
		neuronErrors  = new HashMap<Neuron, Double>();
	}
	
	public NeuralNetwork(int nInputs, int nOutputs, int nHiddenLayers, int nPerHiddenLayer, ActivationFunction func)
	{
		this();
		
		// Input Layer
		for (int i = 0; i < nInputs; i++)
		{
			Neuron neuron = new Neuron(func);
			inputNeurons.add(neuron);
		}
		
		// Hidden Layers
		NeuronsList hiddenLayer = new NeuronsList();
		NeuronsList previousLayer = inputNeurons;
		
		for (int h = 0; h < nHiddenLayers; h++)
		{
			for (int n = 0; n < nPerHiddenLayer; n++)
			{
				Neuron neuron = new Neuron(func);
				hiddenLayer.add(neuron);
				hiddenNeurons.add(neuron);
				
				// Connect them to the previous layer
				for (int p = 0; p < previousLayer.size(); p++)
				{
					Neuron.connect(previousLayer.get(p), neuron);
				}
			}
			
			previousLayer = hiddenLayer;
			hiddenLayer = new NeuronsList();
		}
		
		// Output Layer
		for (int o = 0; o < nOutputs; o++)
		{
			Neuron neuron = new Neuron(func);
			outputNeurons.add(neuron);
			
			// Connect them to the previous layer
			for (int p = 0; p < previousLayer.size(); p++)
			{
				Neuron.connect(previousLayer.get(p), neuron);
			}
		}
		
	}
	
	public NeuralNetwork(int nInputs, int nOutputs, int nHiddenLayers, int nPerHiddenLayer)
	{
		this(nInputs, nOutputs, nHiddenLayers, nPerHiddenLayer, new SigmoidActivationFunction());
	}
	
	private String layerToString(NeuronsList neuronsList)
	{
		String s = "[";
			
		Iterator<Neuron> itN = neuronsList.iterator();
		
		while(itN.hasNext())
		{
			Neuron n = itN.next();
			s += n + " <- [";
			
			Iterator<Synapse> itSyn = n.getInputsIterator();
			while(itSyn.hasNext())
			{
				Synapse syn = itSyn.next();
				s += syn.getA() ;
				s += "(" + (float)syn.getWeight() + ")";
				if (itSyn.hasNext())
				{
					s += ", ";
				}
			}
			
			s += "] ";
			
			if (itN.hasNext())
			{
				s += "; ";
			}
			
		}
		
		s += "]";
		
		return s;
	}
	
	public String toString()
	{		
		return "Neural Network:" +
				"\ninputs layer: " + layerToString(inputNeurons) +
				"\nhidden layer: " + layerToString(hiddenNeurons) + 
				"\noutput layer: " + layerToString(outputNeurons) + 
				"\nvalues:  " + neuronOutputs +
				"\nerrors:  " + neuronErrors;
	}
	
	
	public void addInputNeuron(Neuron n)
	{
		inputNeurons.add(n);
	}
	
	public void addHiddenNeuron(Neuron n)
	{
		hiddenNeurons.add(n);
	}
	
	public void addOutputNeuron(Neuron n)
	{
		outputNeurons.add(n);
	}
	
	public void setInputs(ValuesList values) throws ArrayIndexOutOfBoundsException
	{
		if (values.size() > inputNeurons.size())
		{
			throw new ArrayIndexOutOfBoundsException("Neural Network Inputs < than number of values. ");
		}
		
		for(int i = 0; i < inputNeurons.size(); i++)
		{
			double value = (i < values.size())? values.get(i).doubleValue(): 0.0;
			Neuron inNeuron = inputNeurons.get(i);
			neuronOutputs.put(inNeuron, value);
		}
	}
	
	public ValuesList calculateOutputs(ValuesList inputs)
	{
		neuronOutputs.clear();
		
		setInputs(inputs);
		
		ValuesList values = new ValuesList();
		for(int i = 0; i < outputNeurons.size(); i++)
		{
			Neuron outNeuron = outputNeurons.get(i);
			values.add(calculateOutput(outNeuron));
		}
		
		return values;
	}
	
	private double calculateOutput(Neuron neuron)
	{
		double output = 0.0;
		
		// Calculate the weighted sum of the outputs of the upstream neurons
		Iterator<Synapse> inputsIt = neuron.getInputsIterator();
		while(inputsIt.hasNext())
		{
			Synapse syn = inputsIt.next();
			Neuron otherNeuron = syn.getA();
			double otherOutput = 0.0;
			
			// Avoid computation of previously calculated neurons
			if (neuronOutputs.containsKey(otherNeuron))
			{
				otherOutput = neuronOutputs.get(otherNeuron).doubleValue();
			}
			else
			{
				otherOutput = calculateOutput(otherNeuron);
			}
			
			output += otherOutput * syn.getWeight();
		}
		
		// Apply the activation function 
		output = neuron.getActivationFunction().calculate(output);
		neuronOutputs.put(neuron, output);
		
		return output;
	}
	
	
	public void backPropagation(ValuesList inputs, ValuesList targets, double learningRate) throws ArrayIndexOutOfBoundsException
	{
		if (inputs.size() > inputNeurons.size())
		{
			throw new ArrayIndexOutOfBoundsException("Neural Network Inputs < than number of inputs values. ");
		}
		
		if (targets.size() > outputNeurons.size())
		{
			throw new ArrayIndexOutOfBoundsException("Neural Network Output < than number of targets. ");
		}
		
		// Clear errors
		neuronErrors.clear();
		
		// Forward Pass
		calculateOutputs(inputs);
		
		// Useful Iterators
		Iterator<Neuron> inIt;
		Iterator<Neuron> outIt;
		Iterator<Double> targetIt;
		Iterator<Synapse> synIt;
				
		// Set the errors on the Output neurons
		outIt = outputNeurons.iterator();
		targetIt = targets.iterator();
		
		while (outIt.hasNext() && targetIt.hasNext())
		{
			Neuron outNeuron = outIt.next();
			double target = targetIt.next().doubleValue();
			
			double error = bpCalculateOutputError(outNeuron, target);
			neuronErrors.put(outNeuron, error);
			
			bpUpdateWeights(outNeuron, error, learningRate); //  Now or at the end?
		}
		
		// Set the errors on the hidden neurons
		inIt = inputNeurons.iterator();
		while (inIt.hasNext())
		{
			Neuron inNeuron = inIt.next();
			synIt = inNeuron.getOutputsIterator();
			
			while (synIt.hasNext())
			{
				Synapse syn = synIt.next();
				Neuron nextNeuron = syn.getB();
				double error = bpCalculateHiddenError(nextNeuron);
				neuronErrors.put(nextNeuron, error);
				
				bpUpdateWeights(nextNeuron, error, learningRate); //  Now or at the end?
			}
		}
		
		/*
		// Update Weights
		outIt = outputNeurons.iterator();
		
		while (outIt.hasNext() && targetIt.hasNext())
		{
			Neuron outNeuron = outIt.next();
			bpUpdateWeightsRecursive(outNeuron, learningRate);
		}*/
	}
	
	private void bpUpdateWeights(Neuron neuron, double error, double learningRate)
	{
		Iterator<Synapse> synIt = neuron.getInputsIterator();
		while (synIt.hasNext())
		{
			Synapse syn = synIt.next();
			Neuron otherNeuron = syn.getA();
			double weight = syn.getWeight();
			weight += learningRate * error * neuronOutputs.get(otherNeuron).doubleValue();
			syn.setWeight(weight);
			
		}
	}
	
	private void bpUpdateWeightsRecursive(Neuron neuron, double learningRate)
	{
		double error = neuronErrors.get(neuron);
		
		Iterator<Synapse> synIt = neuron.getInputsIterator();
		while (synIt.hasNext())
		{
			Synapse syn = synIt.next();
			Neuron otherNeuron = syn.getA();
			double weight = syn.getWeight();
			weight += learningRate * error * neuronOutputs.get(otherNeuron).doubleValue();
			syn.setWeight(weight);
			
			bpUpdateWeightsRecursive(otherNeuron, learningRate);
		}
	}
	
	
	private double bpCalculateOutputError(Neuron neuron, double target)
	{
		if (neuronErrors.containsKey(neuron))
		{
			return neuronErrors.get(neuron).doubleValue();
		}
		
		double output = neuronOutputs.get(neuron).doubleValue();
		double deriv  = neuron.getActivationFunction().derive(output);
		double error  = deriv * (target - output);
		
		neuronErrors.put(neuron, new Double(error));
		
		return error;
	}


	private double bpCalculateHiddenError(Neuron neuron)
	{
		if (neuronErrors.containsKey(neuron))
		{
			return neuronErrors.get(neuron).doubleValue();
		}
		
		double output = neuronOutputs.get(neuron).doubleValue();
		double deriv  = neuron.getActivationFunction().derive(output);
		double error  = 0;
		
		Iterator<Synapse> outputIt = neuron.getOutputsIterator();
		while(outputIt.hasNext())
		{
			Synapse syn = outputIt.next();
			error += syn.getWeight() * bpCalculateHiddenError(syn.getB());
		}
		
		error *= deriv;	
		
		neuronErrors.put(neuron, new Double(error));
		
		return error;
	}

}
