package com.neural;

public interface ActivationFunction {

	public double calculate(double x);
	
	public double derive(double f);
}