package stockanalyzer.ctrl;

import org.json.JSONObject;
import stockanalyzer.ui.UserInterface;
import yahooApi.YahooFinance;
import yahooApi.beans.Quote;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.Result;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {


	// Method process, takes a string as input.

	public void process(String ticker) {
		System.out.println("Start process");

		//TODO implement Error handling 

		//TODO implement methods for

		/* I think the data type we are looking for here is the YahooResponse.*/

		QuoteResponse quoteResponse;
		quoteResponse = getData(ticker); // Fetch data for one or more symbols.
		Stock stock;
		stock = historicValues(ticker);
		List<Result> qr;
		qr = quoteResponse.getResult();
		double quote = qr.get(0).getBookValue();
		String name = qr.get(0).getShortName();
		UserInterface.displaySingleQuote(quote, name);
		try {
			double median = stock.getHistory().stream()
					.count()

			System.out.println("Count" + median);
			stock.getHistory().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}


		//1) Daten laden
		//2) Daten Analyse

	}

	/* This methods takes the parameter searchString and returns an object */

	public QuoteResponse getData(String searchString) {
		List<String> stockSelection = new ArrayList<>();
		stockSelection.add(searchString);
		YahooFinance newYahooQuery = new YahooFinance();
		YahooResponse query = newYahooQuery.getCurrentData(stockSelection);
		return query.getQuoteResponse();
	}

	public Stock historicValues(String searchString) {
		Stock stock = null;
		try {
			stock = yahoofinance.YahooFinance.get(searchString);
			stock.getHistory();//.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stock;
	}


	public void closeConnection() {
		
	}
}
