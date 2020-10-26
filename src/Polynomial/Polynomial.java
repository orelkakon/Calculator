package Polynomial;

import java.util.Arrays;
import Scalars.Scalar;

public class Polynomial {
	private boolean _rational;
	private PolyTerm[] _terms;

	public Polynomial(String terms, boolean rational) {
		this._rational = rational;
		this._terms =polyToString(terms, rational);
		Arrays.sort(this._terms);		
	}
	public Polynomial(PolyTerm[] terms, boolean rational){
		this._terms=refresh(terms);
		this._rational=rational;
	}
	//functions that receives a polynomial as a String and converts it to a polyTerm array.
	private PolyTerm[] polyToString(String poly, boolean rational) {	
		poly = poly.replace("-", "+-");
		if(poly.charAt(0)=='+')
			poly=poly.substring(1);
		String[] S = poly.split("\\+");	
		PolyTerm[] a = new PolyTerm[S.length];
		for (int i = 0; i < a.length; i++) {
			a[i] = new PolyTerm(S[i], rational);
		}
		return a;
	}
	//function that adds two polynomials.
	public Polynomial add(Polynomial poly) {
		int lengthMine=getTerms().length;
		int lengthOther = poly.getTerms().length;
		int pointer=0;
		int placeMine=0;
		int placeOther=0;
		PolyTerm[] output= new PolyTerm[lengthMine+lengthOther];		
		while(placeMine<lengthMine&placeOther<lengthOther){
			int compare=(int) Math.signum(getTerms()[placeMine].compareTo(poly.getTerms()[placeOther]));
			switch(compare){
			case 1:
				output[pointer]=poly.getTerms()[placeOther];
				pointer++;
				placeOther++;
				break;
			case -1:
				output[pointer]=getTerms()[placeMine];
				pointer++;
				placeMine++;
				break;
			case 0:
				output[pointer]=getTerms()[placeMine].add(poly.getTerms()[placeOther]);
				pointer++;
				placeMine++;
				placeOther++;
				break;
			}			
		}
		while(placeMine<lengthMine){
		output[pointer]=getTerms()[placeMine];
		pointer++;
		placeMine++;
		}
		while(placeOther<lengthOther){
			output[pointer]=poly.getTerms()[placeOther];
			pointer++;
			placeOther++;
		}
		int update=0;		
		while(update<output.length&&output[update]!=null){
			update++;
		}
		if(update!=output.length) {
		PolyTerm[] updated= new PolyTerm[update];		
		for(int i=0;i<update;i++)
			updated[i]=output[i];
		Arrays.sort(updated);
		return new Polynomial(updated, this.isRational());
		}
		Arrays.sort(output);
		return new Polynomial(output, this.isRational());
	}

	public boolean isRational() {
		return _rational;
	}

	public PolyTerm[] getTerms() {
		return _terms;
	}
	//funtion that multiplies two polynomials.
	public Polynomial mul(Polynomial poly) {
		Polynomial[] mults= new Polynomial[getTerms().length];
		PolyTerm[] cross;//for multiplying all the elements. 
		for(int i=0;i<mults.length;i++) {
			cross= new PolyTerm[poly.getTerms().length];
			for(int j=0;j<poly.getTerms().length;j++) {
				cross[j]=getTerms()[i].mul(poly.getTerms()[j]);
			}
		mults[i]=new Polynomial(cross, _rational);
		}
		Polynomial output;
			output = mults[0];//assume there's at least one.
		
		for(int i=1;i<mults.length;i++)
			output=output.add(mults[i]);
		return output;
	}
	//function that removes zero terms from a given polyTerm array.
	private PolyTerm[] refresh(PolyTerm[] pol) {
		int size=pol.length;
		for(int i=0;i<pol.length;i++) {
			if(pol[i].getCoef().toString().equals("0"))
				size--;
		}
		PolyTerm[] refreshed = new PolyTerm[size];
		int pointer=0;
		for(int i=0;i<pol.length;i++) {
			if(!pol[i].getCoef().toString().equals("0")) {
				refreshed[pointer]=pol[i];
				pointer++;
			}
		}
		if(refreshed.length==0) {		
		refreshed= new PolyTerm[1];
		refreshed[0]= new PolyTerm("0",isRational());
		}
		return refreshed;
	}
	public void setTerms(PolyTerm[] terms) {
		this._terms = terms;
	}
	//function that evaluates the polynomial at a given point.
	public Scalar evaluate(Scalar scalar) {
		Scalar eval= _terms[0].evaluate(scalar);
		for(int i=1;i<getTerms().length;i++)
			eval=eval.add(_terms[i].evaluate(scalar));
		return eval;
	}
	//function that differentiates the polynomial. 
	public Polynomial derivative() {
		PolyTerm[] output;
		if(_terms[0].getPower()==0){
			output= new PolyTerm[_terms.length-1];
			for(int i=1;i<_terms.length;i++)
				output[i-1]=_terms[i].derivative();
		}
		else{
			output= new PolyTerm[_terms.length];
			for(int i=0;i<_terms.length;i++)
				output[i]=_terms[i].derivative();
		}
		return new Polynomial(output, _rational);
	}
	public String toString(){
		String output;
		if(_terms.length==0)
			output="0";
		else
			output=_terms[0].toString();
		for(int i=1;i<_terms.length;i++){
			if(_terms[i].getCoef().toString()!=""&&_terms[i].getCoef().toString().charAt(0)=='-')
				output+=_terms[i].toString();
			else
			output+="+"+_terms[i].toString();
		}
		return output;
			
	}
}
