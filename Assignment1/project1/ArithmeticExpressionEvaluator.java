/**
 * Stacks - Evaluating Arithmetic Expressions
 * Created by ZiminGuo on 3/4/16.
 * Write a class ArithmeticExpressionEvaluator that evaluates infix arithmetic expression
 *  1st: convert infix to postfix
 *  2nd: evaluate postfix, write a postfix evaluator
 *  Should print infix expression, postfix expression, and the result
 */

import java.util.Scanner;
import java.util.Stack;

public class ArithmeticExpressionEvaluator {
	
	private String infixExpression;
	private String postfixExpression;
	private int result;
	
	public ArithmeticExpressionEvaluator() {
		this.infixExpression = "";
		this.postfixExpression = "";
	}
	
	public void setInfixExpression(String mathExpression) {
		this.infixExpression = mathExpression;
	}
	
	private int getPriority(char operator) {
		switch(operator) {
		
			case '+':
			case '-': return 1;
			case '*':
			case '/':
			case '%': return 2;
			case '^': return 3;
			case '(': return 4;
			default: return 0;
		
		}
	}
	
	private void removeWhiteSpaces() {
		this.infixExpression = this.infixExpression.replaceAll("\\s", "");
		// first '\' is escape character; '\s' means all white spaces including spaces and tabs
	}
	
	private void infixToPostfix() {
		char a;
		char poppedOperator;
		Stack<Character> operatorStack = new Stack<Character>();
		this.postfixExpression = ""; //reseting variable in case it was previously used
		
		for (int i = 0; i < this.infixExpression.length(); i++) {
			a = this.infixExpression.charAt(i);
			
			if (Character.isDigit(a)) {
				this.postfixExpression += Character.toString(a);
			}
			
			else if (a == ')') {
				poppedOperator = operatorStack.pop().charValue();
				
				while (poppedOperator != '(') { // pop until the left parenthesis
					
					if (!this.postfixExpression.endsWith(" ")) {
						this.postfixExpression += " ";
					} // adds a white space in between operands and/or operators if none
					
					this.postfixExpression += Character.toString(poppedOperator);
					poppedOperator = operatorStack.pop().charValue();
				} // the parenthesis terminates the loops, so it doesn't get appended
			}
			
			else { // a is an operator other than the right parenthesis
				while (!operatorStack.isEmpty() && operatorStack.peek().charValue() != '(' &&
						getPriority(a) <= getPriority(operatorStack.peek().charValue())) {
						// never pops left parenthesis
					
					poppedOperator = operatorStack.pop().charValue();
					
					if (this.postfixExpression.length() != 0 &&
						!this.postfixExpression.endsWith(" ")) {
						this.postfixExpression += " ";
					} // adds a white space in between operands and/or operators if none
					
					this.postfixExpression += Character.toString(poppedOperator);
				}
				
				if (this.postfixExpression.length() != 0 &&
					!this.postfixExpression.endsWith(" ")) {
					this.postfixExpression += " ";
				} // adds a white space in between operands and/or operators if none
				
				operatorStack.push(Character.valueOf(a));
			}
		}
		
		while (!operatorStack.isEmpty()) { // popping the remaining operators
			poppedOperator = operatorStack.pop().charValue();
			
			if (this.postfixExpression.length() != 0 &&
				!this.postfixExpression.endsWith(" ")) {
				this.postfixExpression += " ";
			} // adds a white space in between operands and/or operators if none
			
			this.postfixExpression += Character.toString(poppedOperator);
		}
		
		System.out.println("Postfix Expression:");
		System.out.println(this.postfixExpression);
	}
	
	private void evaluatePostfix() {
		char a;
		int number1, number2, parsedNumber = 0;
		Stack<Integer> integerStack = new Stack<Integer>();
		
		for (int i = 0; i < this.postfixExpression.length(); i++) {
			a = this.postfixExpression.charAt(i);
			
			if (Character.isDigit(a)) {
				parsedNumber = parsedNumber * 10 + Character.getNumericValue(a);
			}
			
			else if (a == ' '){
				if (Character.isDigit(this.postfixExpression.charAt(i - 1))) {
					// checks to see if a number was actually parsed
					integerStack.push(Integer.valueOf(parsedNumber));
					parsedNumber = 0;
				}
			}
			
			else { // a is an operator
				number2 = integerStack.pop().intValue();
				number1 = integerStack.pop().intValue();
				
				switch(a) {
				
				case '+': {
							integerStack.push(Integer.valueOf(number1 + number2));
							break;
				}
				
				case '-': {
							integerStack.push(Integer.valueOf(number1 - number2));
							break;
				}
				
				case '*': {
							integerStack.push(Integer.valueOf(number1 * number2));
							break;
				}
				
				case '/': {
							integerStack.push(Integer.valueOf(number1 / number2));
							break;
				}
				
				case '%': {
							integerStack.push(Integer.valueOf(number1 % number2));
							break;
				}
				
				case '^': {
							integerStack.push(Integer.valueOf((int) Math.pow(number1, number2)));
							break;
				}
				
				}
			}
		}
		
		this.result = integerStack.pop().intValue();
		
		System.out.println("Result: " + this.result);
		
	}
	
	public void evaluateExpression() {
		removeWhiteSpaces();
		System.out.println("Infix Expression:");
		System.out.println(this.infixExpression);
		infixToPostfix();
		evaluatePostfix();
		System.out.println();
	}


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String infix, answer;
		ArithmeticExpressionEvaluator calculator = new ArithmeticExpressionEvaluator();
		boolean restart = true;

		while (restart) {
			System.out.print("Calculate: ");
			infix = sc.nextLine();
			calculator.setInfixExpression(infix);
			calculator.evaluateExpression();

			System.out.print("Calculate again? (Y/N) ");
			answer = sc.nextLine();
			System.out.println();

			if (answer.equalsIgnoreCase("N")) {
				restart = false;
			}
		}

	}
}

/* Program Output
Calculate: (2 + 2)
Infix Expression:
(2+2)
Postfix Expression:
2 2 +
Result: 4

Calculate again? (Y/N) y

Calculate: 2+(2*2)
Infix Expression:
2+(2*2)
Postfix Expression:
2 2 2 * +
Result: 6

Calculate again? (Y/N) n

 */
