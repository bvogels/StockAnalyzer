package stockanalyzer.ctrl;

import yahooApi.YahooFinance;

import java.util.ArrayList;
import java.util.List;

public class Controller {


	// Method process, takes a string as input.

	public void process(String ticker) {
		System.out.println("Start process");

		//TODO implement Error handling 

		//TODO implement methods for

		/* I think the data type we are looking for here is the YahooResponse.
		* 1. Put data into a list. */
		List<String> stockSelection = new ArrayList<>();
		stockSelection.add(ticker);
		YahooFinance newQuery = new YahooFinance();
		String query = newQuery.requestData(stockSelection);
		System.out.println(query);


		//1) Daten laden
		//2) Daten Analyse

	}

	/* This methods takes the parameter searchString and returns an object */

	public Object getData(String searchString) {

		return null;
	}


	public void closeConnection() {
		
	}
}
