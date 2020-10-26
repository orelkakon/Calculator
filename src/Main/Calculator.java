package Main;
/*
 * The main class of project
 * */
import java.util.Scanner;
import Polynomial.Polynomial;
import Scalars.*;
public class Calculator {

	private static Scanner in = new Scanner(System.in);
		
	public static void main(String[] args) {		 
			start();
	}

	private static void start() {
		while(true) {
			System.out.println("Please select an opertion:\n1.Addition\r\n" + "2.Multiplication\r\n" + "3.Evaluation\r\n"
					+ "4.Derivate\r\n" + "5.Exit");
			String opCode = in.next();
			if(opCode.equals("5"))
				System.exit(0);
			System.out.println(
					"Please select the scalar field\r\nRational (Q) or Real (R)");
			String typeScalar = in.next();
			switch (typeScalar) {
			case "R":
				doOperation(0,opCode);
				break;
			case "Q":
				doOperation(1,opCode);
				break;
			}
		}
	}

	private static void doOperation(int typeScalar,String opCode) {
		boolean kindScalar;
		if(typeScalar == 0)
			kindScalar = false;
		else
			kindScalar = true;
		switch (opCode) {
		case "1":
			addition(kindScalar);
			break;
		case "2":
			multiplication(kindScalar);
			break;
		case "3":
			evaluation(kindScalar);
			break;
		case "4":
			derivative(kindScalar);
			break;
		default:
			System.out.println("opCode from ERROR range. Try Again\n");
			start();
			break;
		}
	}

	private static void derivative(boolean typeScalar) {
		System.out.println("You have selected:  Derivate\r\n" + "Please insert polynomial");
		String Phrase = in.next();
		Polynomial poly = new Polynomial(Phrase,typeScalar);
		System.out.println("The derivative polynomial is: " + (poly.derivative()).toString());
		
	}

	private static void evaluation(boolean typeScalar) {
		System.out.println("Please insert polynomial");
		String Phrase = in.next();
		System.out.println("Please insert the scalar");
		String scalar = in.next();
		Scalar s;
		if(typeScalar)
			s=new RationalScalar(scalar);
		else
			s=new RealScalar(scalar);
		
		Polynomial poly1 = new Polynomial(Phrase,typeScalar);		
		System.out.println("The solution is: \r\n" + (poly1.evaluate(s)).toString());

	}

	private static void multiplication(boolean typeScalar) {
		System.out.println("Please insert the first polynomial");
		String Phrase1 = in.next();
		System.out.println("Please insert the second polynomial");
		String Phrase2 = in.next();

		Polynomial poly1 = new Polynomial(Phrase1,typeScalar);
		Polynomial poly2 = new Polynomial(Phrase2,typeScalar);

		System.out.println("The solution is: \r\n" + (poly1.mul(poly2)).toString());
	}

	private static void addition(boolean typeScalar) {
		System.out.println("Please insert the first polynomial");
		String Phrase1 = in.next();
		System.out.println("Please insert the second polynomial");
		String Phrase2 = in.next();
		
		Polynomial poly1 = new Polynomial(Phrase1,typeScalar);
		Polynomial poly2 = new Polynomial(Phrase2,typeScalar);
			
		
		System.out.println("The solution is: \r\n" + (poly1.add(poly2)).toString());
		
	}

}
