package com.neural;

public class LinearActivationFunction implements ActivationFunction {

	public double calculate(double x)
	{
		return x;
	}
	
	public double derive(double f)
	{
		return 1.0;
	}
}
