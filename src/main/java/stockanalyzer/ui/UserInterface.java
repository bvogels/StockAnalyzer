package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import stockanalyzer.ctrl.Controller;

public class UserInterface 
{

	private Controller ctrl = new Controller(); // creates a new Controller ctrl


	/* Method getDataFromCtrl1. It calls the method process from the Controller object ans passes
	a string (the ticker). There are 4 Controller instances. I use three of them for the
	stock indices of Twitter (TWTR), Ebay and Kraft Heinz Company (KHC).
	 */

	public void getDataFromCtrl1(){
		ctrl.process("TWTR");
	}

	public void getDataFromCtrl2(){
		ctrl.process("EBAY");
	}

	public void getDataFromCtrl3(){
		ctrl.process("KHC");

	}
	public void getDataFromCtrl4(){

	}

	// This method is not implemented yet.

	public void getDataForCustomInput() {
		
	}


	/* This method displays the user interface.	Corrected typos and changed to English for
	* consistency. It is able to run in a thread, but I guess not necessary. */

	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitle("Choose an option:");
		menu.insert("a", "Choice 1", this::getDataFromCtrl1);
		menu.insert("b", "Choice 2", this::getDataFromCtrl2);
		menu.insert("c", "Choice 3", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("z", "Choice User Input:",this::getDataFromCtrl4);
		menu.insert("q", "Quit", null);
		/* The thread becomes the value of an option. If it is null, the connection
		is closed and the program terminates. */
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		ctrl.closeConnection();
		System.out.println("Program finished");
	}


	/* This method reads some input. Purpose unknown, so far. It takes no arguments. It is used
	* by readDouble to take some string input, which is then converted to double.*/

	protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	/* This methods takes two int arguments.

	 */
	protected Double readDouble(int lowerlimit, int upperlimit) {
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			} catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
