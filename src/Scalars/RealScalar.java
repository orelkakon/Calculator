package Scalars;

public class RealScalar implements Scalar {
	//real scalar
	double _x;

	public RealScalar(double x) {
		this._x = x;
	}

	public RealScalar(String num) {		
		if(num.equals("-"))
			num="-1";		
		//converting from string to double
		this._x=Double.parseDouble(num.trim());
	}

	@Override
	public Scalar add(Scalar s) {
		return new RealScalar(getX() + ((RealScalar) s).getX());
	}

	@Override
	public Scalar mul(Scalar s) {
		return new RealScalar(getX() * ((RealScalar) s).getX());
	}

	@Override
	public Scalar neg(Scalar s) {
		return new RealScalar(-getX());
	}

	@Override
	public Scalar pow(int exponent) {
		return new RealScalar(Math.pow(_x, exponent));
	}
	
	@Override
	public boolean equals(Scalar s) {
		if (!(s instanceof RealScalar))
			return false;
		return getX() == ((RealScalar) s).getX();
	}

	public double getX() {
		return _x;
	}

	public String toString() {
		double remaind= (double)((int)((_x-(int)_x)*1000))/1000;
		if (_x == 0)
			return "0";
		if (remaind == 0)
			return "" + (int) _x;
		else
			return "" + ((int)_x +remaind);
	}
}
