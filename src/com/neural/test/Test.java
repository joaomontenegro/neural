package com.neural.test;

import java.util.Vector;

import com.neural.LinearActivationFunction;
import com.neural.NeuralNetwork;
import com.neural.Neuron;
import com.neural.ActivationFunction;
import com.neural.SigmoidActivationFunction;
import com.neural.ValuesList;

class Test
{
	public static NeuralNetwork net1()
	{
		/*  0.35
		 * |I0| ---0.1--- |N0|
		 *     \         /    \
		 *     0.4     /       0.3
		 *        \  /            \
		 *         X             |nOut| 
		 *        /  \            /
		 *     0.8     \       0.9
		 *     /         \    /
		 * |I1| ---0.6--- |N1|
		 *  0.9
		 */
		
		ActivationFunction func = new SigmoidActivationFunction();
		Neuron nIn0 = new Neuron(func);
		Neuron nIn1 = new Neuron(func);
		
		
		Neuron n0   = new Neuron(func);
		Neuron.connect(nIn0, n0);
		Neuron.connect(nIn1, n0);
		
		Neuron n1   = new Neuron(func);
		Neuron.connect(nIn0, n1);
		Neuron.connect(nIn1, n1);
		
		Neuron nOut = new Neuron(func);
		Neuron.connect(n0, nOut);
		Neuron.connect(n1, nOut);
	
		NeuralNetwork net = new NeuralNetwork();
		net.addInputNeuron(nIn0);
		net.addInputNeuron(nIn1);
		net.addHiddenNeuron(n0);
		net.addHiddenNeuron(n1);
		net.addOutputNeuron(nOut);
		
		return net;
	}
	
	
	public static NeuralNetwork net2()
	{
		/*  
		 * |I0| --------- 
		 *     \         \
		 *      \         \ 
		 *        \        \
		 *         |N0|---- |nOut|
		 *        /        /
		 *      /         / 
		 *     /         /
		 * |I1| ---------   
		 * 
		 */
		ActivationFunction func = new SigmoidActivationFunction();
		Neuron nIn0 = new Neuron(func);
		Neuron nIn1 = new Neuron(func);
		Neuron n0   = new Neuron(func);
		Neuron nOut = new Neuron(func);
		
		Neuron.connect(nIn0, nOut);
		Neuron.connect(nIn0, n0);
		Neuron.connect(nIn1, nOut);
		Neuron.connect(nIn1, n0);
		Neuron.connect(n0, nOut);
	
		NeuralNetwork net = new NeuralNetwork();
		net.addInputNeuron(nIn0);
		net.addInputNeuron(nIn1);
		net.addOutputNeuron(nOut);
		
		return net;
	}
	
	private static double testfunc(double a, double b)
	{
		double result = 0;
		
		if (a < 0.5 && b > 0.5 || a > 0.5 && b < 0.5)// XOR
		//if (a > 0.5 || b > 0.5)// XOR
		{
			result = 1.0;
		}
	    
	    return result;
	}
	
	public static void testBool(NeuralNetwork net, int nTrains, double learningRate)
	{		
		for (int i = 0; i < nTrains; i++)
		{
			double i0 = Math.round(Math.random());
			double i1 = Math.round(Math.random());
			double target = testfunc(i0, i1);
						
			ValuesList inputs = new ValuesList();
			inputs.add(i0);
			inputs.add(i1);
			
			ValuesList targets = new ValuesList();
			targets.add(target);
		
			net.backPropagation(inputs, targets, learningRate);
		}
			
		ValuesList inputs;
		ValuesList outputs;
		double o;
		
		inputs = new ValuesList();
		inputs.add(new Double(0.0));
		inputs.add(new Double(0.0));
		outputs = net.calculateOutputs(inputs); 
		o = outputs.get(0).doubleValue();
		System.out.println ("o: " + inputs + " --> " + o);
		
		inputs = new ValuesList();
		inputs.add(new Double(1.0));
		inputs.add(new Double(0.0));
		outputs = net.calculateOutputs(inputs); 
		o = outputs.get(0).doubleValue();
		System.out.println ("o: " + inputs + " --> " + o);
		
		inputs = new ValuesList();
		inputs.add(new Double(0.0));
		inputs.add(new Double(1.0));
		outputs = net.calculateOutputs(inputs); 
		o = outputs.get(0).doubleValue();
		System.out.println ("o: " + inputs + " --> " + o);
		
		inputs = new ValuesList();
		inputs.add(new Double(1.0));
		inputs.add(new Double(1.0));
		outputs = net.calculateOutputs(inputs); 
		o = outputs.get(0).doubleValue();
		System.out.println ("o: " + inputs + " --> " + o);
		
		System.out.println("");
		//System.out.println(net);
		System.out.println("-----------------\n\n");
		
	}
	
	public static NeuralNetwork net3()
	{
		return new NeuralNetwork(2, 1, 1, 2);
	}
	
	public static NeuralNetwork net4()
	{
		return new NeuralNetwork(2, 1, 1, 4);
	}
	
	public static void main(String args[])
	{
		for (int i = 0; i < 10; i++)
		{
			testBool(net4(), 5000, 0.9);
		}
		
		//System.out.println(net1() + "\n\n");
		//System.out.println(net3() + "\n\n");
		
		
	}
}