//Author: Robert Arnold
//Date: 11/08/2015
//Email: RobertArnold@gmail.com
//Description: A brainfuck interpretor written in Java.


package brainfuck_interpretor;

import java.util.ArrayList;
import java.util.List;

public class Interpretor {

	private List<Integer> storage = new ArrayList<Integer>(); //This list is used as the interpretors "Memory banks", I used a list so it should be big enough for any code you can be bother to write in brainfuck.
	private int pointer = 0; //Which memory cell the code is refering to.
	
	private int currentChar = 0; //What character of source code is currently being interpreted
	private String sourceCode; //The source code itself.
	
	private int inputPointer = 0; //What character of input is to be read next.
	private String inputString; //The input itself.
	
	private String output = ""; //The current output string.
	
	private boolean debugMode = false; //Whether to output debugging information or not.
	
	public String interpret(String inputCode){ //This method takes the inputted brainfuck code and then runs in a character at a time.
		
		sourceCode = inputCode; //Sets the code that was passed as the argument as the code being run.
		
		storage.add(0); //Initializes the first value stored in the interpretors memory.
		
		for(currentChar = 0; currentChar < sourceCode.length(); currentChar++){	//Loop until the current char pointer reaches the end of the code.
			interpretChar(sourceCode.charAt(currentChar)); //Interpret the currently selected character.
		}
		
		return output; //Returns the output string
	}
	
	public String interpret(String inputCode, String inputString){ //Sets the input string then runs the main interpret method.
		this.inputString = inputString;
		return interpret(inputCode);
	}
	
	public String interpret(String inputCode, String inputString, boolean debugMode){ //Sets the debug mode then runs the main interpret method.
		this.debugMode = debugMode;
		return(interpret(inputCode, inputString));
	}

	private void interpretChar(char c){
		
		//This takes the character passed to it and processes it depending on what character it is.
		//NOTE: If the character isn't one of the eight command characters, it is ignored. (So commenting and line spacing is easy)
		
		if(c == '>'){
			if(debugMode)
				System.out.println("Increment Pointer");
			
			incrementPointer();
		}
		else if(c == '<'){
			if(debugMode)
				System.out.println("Decrement Pointer");
			
			decrementPointer();
		}
		else if(c == '+'){
			if(debugMode)
				System.out.println("Add");
			
			incrementValue();
		}
		else if(c == '-'){
			if(debugMode)
				System.out.println("Take");
			
			decrementValue();
		}
		else if(c == '.'){
			if(debugMode)
				System.out.println("Output");
			
			outputCell();
		}
		else if(c == '['){
			if(debugMode)
				System.out.println("Jump Forwards");
			
			jumpForward();
		}
		else if(c == ']'){
			if(debugMode)
				System.out.println("Jump Backwards");
			
			jumpBackwards();
		}
		else if(c == ','){
			if(debugMode)
				System.out.println("Input");
			
			inputByte();
		}
	}
	
	private void incrementPointer(){ //Increment the memory pointer, if this exceeds the current length of the list create a new memory cell.
		pointer++;
		if(storage.size() <= pointer){
			storage.add(new Integer(0));
		}
	}
	
	private void decrementPointer(){ //Decrement the memory pointer, if this goes negative, throw an error.
		pointer--;
		if(pointer < 0){
			throw new NullPointerException("Pointer out of bounds");
		}
	}
	
	private void incrementValue(){ //Increment the value stored in the address the pointer is currently pointing at.
		storage.set(pointer, (storage.get(pointer) + 1));
	}
	
	private void decrementValue(){ //Decrement the value stored in the address the pointer is currently pointing at.
		storage.set(pointer, (storage.get(pointer) - 1));
	}
	
	private void outputCell(){ //Convert whatever the pointer is currently pointing at to ascii and append it to the output string.
		
		int i = storage.get(pointer);
		
		char c = (char)i;
		
		String s = Character.toString(c);
		
		output = output + s;
		
	}
	
	private void jumpForward(){ //Jumps the code forward to the matching ] bracket. Only if the value currently pointed at is 0.
		boolean finished = false;
		int nesting = 0;
		
		if(storage.get(pointer) == 0){
			while(!finished){
				
				currentChar++;
				if(currentChar > sourceCode.length()){
					return;
				}
				
				if(sourceCode.charAt(currentChar) == ']' && nesting == 0){
					finished = true;
				}
				else if(sourceCode.charAt(currentChar) == ']'){
					nesting--;
				}
				
				if(sourceCode.charAt(currentChar) == '['){
					nesting++;
				}
			}
		}
	}
	
	private void jumpBackwards(){ //Jumps the code backwards to the matching [ bracket. Only if the value currently pointed at is NOT 0.
		
		boolean finished = false;
		int nesting = 0;
		
		if(storage.get(pointer) != 0){
			while(!finished){
				
				currentChar--;
				if(currentChar < 0){
					return;
				}
				
				if(sourceCode.charAt(currentChar) == '[' && nesting == 0){
					finished = true;
				}
				else if(sourceCode.charAt(currentChar) == '['){
					nesting--;
				}
				
				if(sourceCode.charAt(currentChar) == ']'){
					nesting++;
				}
				
			}
		}
	}
	
	private void inputByte(){	//Takes a character from the input string and stores it in the current active memory cell.
		if(inputPointer < inputString.length()){
			storage.set(pointer, (int)inputString.charAt(inputPointer));
			inputPointer++;
		}
	}
	
	
	public static void main(String[] args) {
		
		Interpretor i = new Interpretor();
		
		if(args.length == 0){
			throw new NullPointerException("No input code");
		}
		
		String inputCode = args[0];
		
		if(args.length > 1){
			String inputString = args[1];
			System.out.println(i.interpret(inputCode, inputString));
		} else {
			System.out.println("Result: " + i.interpret(inputCode));
		}

	}
}
