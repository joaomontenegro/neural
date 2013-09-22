package com.neural.test;

import java.util.Vector;

import com.neural.InputNeuron;
import com.neural.LayerNeuron;
import com.neural.NeuralNetwork;
import com.neural.Neuron;
import com.neural.Synapse;
import com.neural.TransferFunction;

class TestTransferFunction implements TransferFunction
{
	public double calculate(double x) 
	{
		return x;
	}
}

class Test
{
	public static void main(String args[])
	{
		TestTransferFunction t = new TestTransferFunction();
		
		/*
		 * |I0| ---0.1--- |N0|
		 *     \         /    \
		 *     0.3     /       0.5
		 *        \  /            \
		 *         X             |Out| 
		 *        /  \            /
		 *     0.2     \       0.6
		 *     /         \    /
		 * |I1| ---0.4--- |N1|
		 */
		
		InputNeuron nIn0 = new InputNeuron(0.0);
		InputNeuron nIn1 = new InputNeuron(0.0);
		LayerNeuron n0 = new LayerNeuron(t);
		LayerNeuron n1 = new LayerNeuron(t);
		LayerNeuron nOut = new LayerNeuron(t);
		
		Neuron.connect(nIn0, n0, 0.1);
		Neuron.connect(nIn1, n0, 0.2);
		Neuron.connect(nIn0, n1, 0.3);
		Neuron.connect(nIn1, n1, 0.4);
		Neuron.connect(n0, nOut, 0.5);
		Neuron.connect(n1, nOut, 0.6);
	
		NeuralNetwork net = new NeuralNetwork();
		net.addInputNeuron(nIn0);
		net.addInputNeuron(nIn1);
		net.addOutputNeuron(nOut);
		
		net.setInput(0, 0.3);
		net.setInput(1, 0.4);
		
		System.out.println(net.getOutput(0));
	}
}