package stockanalyzer.ui;


import stockanalyzer.ctrl.Controller;
import stockanalyzer.downloader.Downloader;
import stockanalyzer.downloader.ParallelDownloader;
import stockanalyzer.downloader.SequentialDownloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class UserInterface
{

	private Controller ctrl = new Controller(); // creates a new Controller ctrl


	/* Method getDataFromCtrl1. It calls the method process from the Controller object ans passes
	a string (the ticker). There are 4 Controller instances. I use three of them for the
	stock indices of Twitter (TWTR), Ebay and Kraft Heinz Company (KHC).
	 */

	public void getDataFromCtrl1() throws IOException {
		List<String> tickers = Arrays.asList("TWTR", "EBAY", "KHC");
		Downloader sd = new SequentialDownloader();
		ctrl.process(tickers, sd);
	}

	public void getDataFromCtrl2() throws IOException {
		List<String> tickers = Arrays.asList("TSLA", "FB", "VTRS");
		Downloader pd = new ParallelDownloader();
		ctrl.process(tickers, pd);
	}

	public void getDataFromCtrl3() throws IOException {
		List<String> tickers = Arrays.asList("BBY", "AMZN", "COIN");
		Downloader sd = new SequentialDownloader();
		ctrl.process(tickers, sd);

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
		menu.insert("a", "Twitter, Ebay, Kraft/Heinz Company (Sequential Download)", () -> {
			try {
				getDataFromCtrl1();
			} catch (IOException e) {
				UserInterface.errors(e);
			}
		});
		menu.insert("b", "Tesla, Facebook, Viatris (Parallel Download)", () -> {
			try {
				getDataFromCtrl2();
			} catch (IOException e) {
				UserInterface.errors(e);
			}
		});
		menu.insert("c", "Best Buy, Amazon, Coinbase (Sequential Download)", () -> {
			try {
				getDataFromCtrl3();
			} catch (IOException e) {
				UserInterface.errors(e);
			}
		});
		menu.insert("s", "Currently unused:",this::getDataForCustomInput);
		menu.insert("p", "Currently unused:",this::getDataFromCtrl4);
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

	public static void displaySingleQuote(double quote, String name, double lastFiftyDays, double change) {
		System.out.println("The current quote of " + name + " stock is: " + quote + " Dollar.");
		System.out.println("The average within the last 50 days was " + lastFiftyDays + ". The means the average change is " + change + " .");
	}

	public static void displayYearlyAverage(double median, String name) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		System.out.println("The average value of " + name + " is " + decimalFormat.format(median) + ".");
	}

	public static void errors(Exception e) {
		System.out.println("There was an unknown error. Check your internet connection. Going back to menu");
		System.out.println("System message: "+ e);
		UserInterface ui = new UserInterface();
		ui.start();
	}
}
