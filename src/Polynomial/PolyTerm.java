package Polynomial;
/*
 * 14.4.19 by orelkakon
 * We used in instanceof , we cant success understand how to do it without it. 
 * */

import Scalars.RationalScalar;
import Scalars.RealScalar;
import Scalars.Scalar;

public class PolyTerm implements Comparable<PolyTerm> {
	private int _power;
	private Scalar _coeff;

	public PolyTerm(int power, Scalar coef) {
		this._coeff = coef;
		checkPower(power);
		this._power = power;

	}

	public PolyTerm(String term, boolean typeScalar) {
		Scalar s;
		//A case where the power is 1 thus ^ is missing.
		if (term.indexOf("x") >= 0 & term.indexOf("^") == -1) {
			String[] terms = term.split("x");			
			if (typeScalar) {
				if(terms.length!=0)
					s = new RationalScalar(terms[0]);
				else
					s = new RationalScalar(1,1);
			} else {
				if(terms.length!=0)
					s = new RealScalar(terms[0]);
				else
					s= new RealScalar(1);
			}
			this._coeff = s;
			this._power = 1;
			}
		else {
			String[] terms = term.split("x\\^");
			//only a scalar is in the argument.
			if (terms.length == 1) {
				if (typeScalar) {
					s = new RationalScalar(terms[0]);
				} else {
					s = new RealScalar(terms[0]);
				}
				this._coeff = s;
				this._power = 0;
			} else {//a power exists in the argument.
				if (typeScalar) {
					if(terms[0].length()!=0)
						s = new RationalScalar(terms[0]);
					else
						s = new RationalScalar("1");
				} else {
					if(terms[0].length()!=0)
						s = new RealScalar(terms[0]);
					else
						s = new RealScalar("1");
				}
				this._coeff = s;
				this._power = Integer.parseInt(terms[1]);
			}
		}
	}

	private void checkPower(int power) {
		if (power < 0)
			throw new IllegalArgumentException("Power must greater than 0");
	}

	private boolean canAdd(PolyTerm pt) {
		//two polyterms can be added if and only if their powers are equivalent 
		return getPower() == pt.getPower();
	}

	public PolyTerm add(PolyTerm pt) {
		if (!canAdd(pt))
			throw new IllegalArgumentException("Can't add those");
		if (_coeff instanceof RealScalar) {
			RealScalar r = (RealScalar) _coeff;
			RealScalar r2 = (RealScalar) pt.getCoef();
			return new PolyTerm(getPower(), r.add(r2));//powers don't change , and adding coefficients. 
		} else {
			RationalScalar r = (RationalScalar) _coeff;
			RationalScalar r2 = (RationalScalar) pt.getCoef();
			return new PolyTerm(getPower(), r.add(r2));
		}
	}

	public PolyTerm mul(PolyTerm pt) {
		if (_coeff instanceof RealScalar) {
			RealScalar r = (RealScalar) _coeff;
			RealScalar r2 = (RealScalar) pt.getCoef();
			return new PolyTerm(getPower() + pt.getPower(), r.mul(r2));//multiplying coefficients and adding powers.
		} else {
			RationalScalar r = (RationalScalar) _coeff;
			RationalScalar r2 = (RationalScalar) pt.getCoef();
			return new PolyTerm(getPower() + pt.getPower(), r.mul(r2));//multiplying coefficients and adding powers.
		}
	}

	public Scalar evaluate(Scalar scalar) {
		if (_coeff instanceof RealScalar) {
			RealScalar r = (RealScalar) scalar;// number=r
			RealScalar r2 = (RealScalar) getCoef();// coef=r2
			return new RealScalar(r2.getX() * Math.pow(r.getX(), getPower()));// coef*(Scalar)^power
		} else {
			RationalScalar r = (RationalScalar) scalar;
			RationalScalar r2 = (RationalScalar) getCoef();
			return RationalScalar.cancel(r2.getA() * (int) Math.pow(r.getA(), getPower()),
					r2.getB() * (int) Math.pow(r.getB(), getPower()));//multyping fractions and then canceling them out.
			// returns coef*(a/b)^power
		}

	}

	public PolyTerm derivative() {
		if (_coeff instanceof RealScalar) {
			RealScalar rCoef = (RealScalar) getCoef();// coef
			RealScalar rPow = new RealScalar(getPower());// power
			//if we're deriving a constant the derivative is 0.
			if (getPower() == 0)
				return new PolyTerm(0, new RealScalar(0));
			return new PolyTerm(_power - 1, rCoef.mul(rPow));
		}

		else {
			RationalScalar rCoef = (RationalScalar) getCoef();
			RationalScalar rPower = new RationalScalar(_power, 1);
			//if we're deriving a constant the derivative is 0.
			if (getPower() == 0)
				return new PolyTerm(0, new RationalScalar(0, 1));
			return new PolyTerm(_power - 1, rCoef.mul(rPower));
		}
	}

	public boolean equals(PolyTerm pt) {
		return pt.getPower() == getPower() && pt.getCoef().equals(getCoef());
	}

	public int getPower() {
		return _power;
	}

	public Scalar getCoef() {
		return _coeff;
	}

	public int compareTo(PolyTerm term) {
		return this.getPower() - term.getPower();
	}
	public String toString(){
		//when power==0 the term is a scalar with no x.
		if(_power==0)
			return _coeff.toString();
		return _coeff.toString()+"x^"+_power+"";
	}

}
