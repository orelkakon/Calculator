package Scalars;
/*
 * Interface that define the base format of scalar .
 * */
public interface Scalar {
	Scalar add(Scalar s); // +
	
	Scalar mul(Scalar s); // *
	
	Scalar neg(Scalar s); // -
	
	Scalar pow(int exponent); // ^
	
	boolean equals(Scalar s); // ==
	
}
