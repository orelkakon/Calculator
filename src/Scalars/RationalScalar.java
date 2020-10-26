package Scalars;


public class RationalScalar implements Scalar {
	// rational number; a/b where a and b are natural numbers and b!=0

	private int a;
	private int b;

	public RationalScalar(String num) {		
		this(fromString(num).getA(),fromString(num).getB());
	}
	public RationalScalar(int a, int b) {		
		checkB(b);// throws an error if b==0
		if(b<0) {
			this.b = -b;
			this.a = -a;
		}
		else {
			this.b = b;
			this.a = a;
		}
			
	}

	private void checkB(int b) {
		if (b == 0)
			throw new IllegalArgumentException("Can't divide by zero!");
	}

	@Override
	public Scalar add(Scalar s) {
		int c = ((RationalScalar) s).getA();
		int d = ((RationalScalar) s).getB();
		int x;
		int y;

		if (b == d) {
			// special case, when both denominators are equal.
			x = a + c;
			y = b;
		} else {
			x = a * d + b * c;
			y = b * d;
		}

		return cancel(x, y);
	}
	//The function receives a fraction composed of (x/y) and cancels all common divisors. 
	public static Scalar cancel(int x, int y) {
		// checking for common factors and then removing them from both numbers.
		if (x % y == 0) {
			// speical case, when the numerator and the denominator divide each other with
			// no remainder.
			x = x / y;
			y = 1;

		} else {

			int divisor = 2;
			while ((divisor <= y) & (divisor <= x)) {
				// if we found a common factor.
				if ((x % divisor == 0) & (y % divisor == 0)) {
					x = x / divisor;
					y = y / divisor;
				} else {
					divisor = divisor + 1;
				}
			}
		}
		return new RationalScalar(x, y);
	}

	@Override
	public Scalar mul(Scalar s) {
		int x = getA() * ((RationalScalar) s).getA();
		int y = getB() * ((RationalScalar) s).getB();
		return cancel(x, y);
	}

	@Override
	public Scalar neg(Scalar s) {
		return new RationalScalar(-getA(), getB());
	}

	@Override
	public Scalar pow(int exponent) {
		return new RationalScalar((int)Math.pow(getA(),exponent),(int)Math.pow(getB(), exponent));
	}

	@Override
	public boolean equals(Scalar s) {
		if (!(s instanceof RationalScalar))
			return false;
		RationalScalar other = (RationalScalar) s;
		return other.getA() == getA() & other.getB() == getB();
	}
	//function returns a rational scalar after extracting it from a String.
	public static RationalScalar fromString(String terms) {
		if(terms.length()==0)
			return new RationalScalar(0,1);
		if(terms.indexOf(".")>=0) {
			int z=(int)(Double.parseDouble(terms)*1000);
			return new RationalScalar(z,1000);
		}		
		else {
		if (terms.indexOf('/') == -1)
			return new RationalScalar(Integer.parseInt(terms), 1);		
		String[] splt = terms.split("/");
		return new RationalScalar(Integer.parseInt(splt[0]), Integer.parseInt(splt[1]));
		}
	}

	public void setA(int a) {
		this.a = a;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}
	public String toString(){
		if(a==0)
			return "0";
		if(b==1)
			return a+"";		
		return a+"/"+b;
	}
}
