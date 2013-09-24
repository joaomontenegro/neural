package com.neural;

public class SigmoidActivationFunction implements ActivationFunction {

	public double calculate(double x) 
	{
		return 1 / (1 + Math.pow(Math.E, -x));
	}

	public double derive(double f)
	{
		return f * (1 - f);
	}

}
