/*
	NumberToEnglishDemo Takes in a number between 0 and 999999999 and returns the equivalent written english

 */
package numbertoenglishdemo;

import java.util.Scanner;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * 
 * @author Tony Chambers
 */
public class NumberToEnglishDemo {

	// Declare variables

	private String convertedString;
	private String inputtedNumber;
	private final String[] lowVals = { "zero", "one", "two", "three", "four",
			"five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
			"thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
			"eighteen", "nineteen" };
	private final String[] tensVals = { "", "ten", "twenty", "thirty", "forty",
			"fifty", "sixty", "seventy", "eighty", "ninety" };
	private final String[] largeVals = { "hundred", "thousand", "million" };

	// Constructor

	public NumberToEnglishDemo() {
		inputtedNumber = "";
		convertedString = "";
	}

	/**
	 * Uses the command line to allow a user to enter a number and returns the
	 * input
	 * 
	 * @return input
	 */
	public String addInput() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a number below:");
		String input;
		input = in.nextLine();
		return input.toString();
	}

	/**
	 * Allows manual input of a number string
	 * 
	 * @param num
	 */
	public void exec(String num) {
		inputtedNumber = num;
		if (inputtedNumber.length() == 0) {
			System.out.println("Please enter a number");
			return;
		} 
		if (!this.isNum(inputtedNumber))
		{
			System.out.println("Input must be a number");
			return;
		}
		if (Integer.parseInt(inputtedNumber) > 999999999
				|| Integer.parseInt(inputtedNumber) < 0) {
			System.out.println("The number must be between 0 and 999,999,999");
			return;
		}
		if (Character.toString(inputtedNumber.charAt(0)).equals("0") && inputtedNumber.length() > 1)
		{
			System.out.println("The number must not start with 0");
			return;
		}
		this.convertNumber(inputtedNumber);
	}

	
	/**
	 * Checks that the inputed string is a number
	 * @param str
	 * @return
	 */
	private boolean isNum(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}	
	
	
	
	/**
	 * Takes in a number string, assesses its length, and produces a string of
	 * the number in written English
	 * 
	 * @param currentNum
	 */
	private void convertNumber(String currentNum) {
		int remainder;
		if (currentNum.length() < 1) {
			this.displayResult(convertedString);
		} else if (currentNum.length() <= 2 && currentNum.length() > 0) {
			convertedString = convertedString + this.getDigits(currentNum);
			currentNum = "";
			this.convertNumber(currentNum);
		} else if (currentNum.length() == 3) {
			convertedString = convertedString + this.getHundreds(currentNum)
					+ " and ";
			currentNum = currentNum.substring(1, currentNum.length());
			this.convertNumber(currentNum);
		} else if (currentNum.length() >= 4 && currentNum.length() <= 6) {
			remainder = currentNum.length() - 3;
			convertedString = convertedString + this.getThousands(currentNum);
			currentNum = currentNum.substring(remainder, currentNum.length());
			this.convertNumber(currentNum);
		} else if (currentNum.length() >= 7 && currentNum.length() <= 9) {
			remainder = currentNum.length() - 6;
			convertedString = convertedString + this.getMillions(currentNum);
			currentNum = currentNum.substring(remainder, currentNum.length());
			this.convertNumber(currentNum);
		}

	}

	/**
	 * Prints out the resulting string in English to the console
	 */
	private void displayResult(String result) {
		System.out.println(result);
	}

	/**
	 * Takes in a string of maximum 2 in length and returns the number in
	 * written English
	 * 
	 * @param currentNum
	 * @return value of digits as string in written English
	 */
	private String getDigits(String currentNum) {
		String returnVal = "";
		if (currentNum.length() <= 2) {
			int convNumber = Integer.parseInt(currentNum);
			int indexPos;
			if (Integer.parseInt(inputtedNumber) == 0) {
				return lowVals[0];
			} else if (convNumber <= 19 && convNumber > 0) {
				indexPos = Integer.parseInt(currentNum);
				returnVal = lowVals[indexPos];
			} else if (convNumber > 19 && convNumber <= 99) {
				indexPos = Integer.parseInt(Character.toString(currentNum
						.charAt(0)));
				returnVal = tensVals[indexPos]
						+ " "
						+ this.getDigits(Character.toString(currentNum
								.charAt(1)));
			}
		}
		return returnVal;
	}

	/**
	 * Takes in a number string of maximum 3 in length and returns the number in
	 * written English
	 * 
	 * @param currentNum
	 * @return value of digits as string in written English
	 */
	private String getHundreds(String currentNum) {
		String returnVal = "";
		int indexPos = Integer
				.parseInt(Character.toString(currentNum.charAt(0)));
		if (indexPos > 0) {
			returnVal = lowVals[indexPos];
			returnVal = returnVal + " " + largeVals[0];
		}
		return returnVal;
	}

	/**
	 * Takes in a number string of maximum 6 in length and returns the number in
	 * written English
	 * 
	 * @param currentNum
	 * @return value of digits as string in written English
	 */
	private String getThousands(String currentNum) {
		String returnVal = "";
		int thousandsPortion = currentNum.length() - 3;
		int indexPos;
		String target = currentNum.substring(0, thousandsPortion);
		if (target.length() == 1)
		{
			indexPos = Integer
					.parseInt(Character.toString(currentNum.charAt(0)));
			returnVal = lowVals[indexPos];
			returnVal = returnVal + " " + largeVals[1] + " ";
		}
		if (target.length() == 2) {
			returnVal = this.getDigits(target);
			returnVal = returnVal + " " + largeVals[1] + " ";
		}
		if (target.length() == 3) {
			String digitsPortion = currentNum.substring(1, thousandsPortion);
			returnVal = this.getHundreds(Character.toString(currentNum
					.charAt(0)))
					+ " "
					+ this.getDigits(digitsPortion)
					+ " "
					+ largeVals[1] + " ";
		}
		return returnVal;
	}

	/**
	 * Takes in a number string of maximum 9 in length and returns the number in
	 * written English
	 * 
	 * @param currentNum
	 * @return value of digits as string in written English
	 */
	private String getMillions(String currentNum) {
		String returnVal = "";
		int millionsPortion = currentNum.length() - 6;
		int indexPos;
		String target = currentNum.substring(0, millionsPortion);
		if (target.length() == 1) {
			indexPos = Integer
					.parseInt(Character.toString(currentNum.charAt(0)));
			returnVal = lowVals[indexPos];
			returnVal = returnVal + " " + largeVals[2] + " ";
		}
		if (target.length() == 2) {
			returnVal = this.getDigits(target);
			returnVal = returnVal + " " + largeVals[2] + " ";
		}
		if (target.length() == 3) {
			String digitsPortion = currentNum.substring(1, millionsPortion);
			returnVal = this.getHundreds(Character.toString(currentNum
					.charAt(0)))
					+ " and "
					+ this.getDigits(digitsPortion)
					+ " " + largeVals[2] + " ";
		}
		return returnVal;
	}

	public static void main(String args[]) {
		NumberToEnglishDemo demo = new NumberToEnglishDemo();
		String number = demo.addInput();
		demo.exec(number);
	}

}
