package stockanalyzer.ctrl;

import org.h2.engine.User;
import org.json.JSONObject;
import stockanalyzer.ui.UserInterface;
import yahooApi.YahooFinance;
import yahooApi.beans.Quote;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.Result;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static java.lang.Math.round;

public class Controller {


	// Method process, takes a string as input.

	public void process(String ticker) throws IOException {
		System.out.println("Start process");
		singleQuote(ticker);
		yearlyAverage(ticker);

		//TODO implement Error handling 

		//TODO implement methods for

		/* I think the data type we are looking for here is the YahooResponse.*/
	}

	private void singleQuote(String ticker) throws IOException {
		QuoteResponse quoteResponse;
		quoteResponse = getData(ticker); // Fetch data for one or more symbols.
		List<Result> qr;
		qr = quoteResponse.getResult();
		double quote = qr.get(0).getBookValue();
		double lastFiftyDays = qr.get(0).getFiftyDayAverage();
		double change = qr.get(0).getFiftyDayAverageChange();
		String name = qr.get(0).getShortName();
		UserInterface.displaySingleQuote(quote, name, lastFiftyDays, change);
	}

	private void yearlyAverage(String ticker) {
		Stock stock;
		stock = historicValues(ticker);
		try {
			double median = stock.getHistory().stream()
					.mapToDouble(closingValue -> closingValue.getClose().doubleValue())
					.average()
					.orElse(0.0);
			UserInterface.displayYearlyAverage(median, ticker);
		} catch (IOException e) {
			UserInterface.errors(e);
		}




		//1) Daten laden
		//2) Daten Analyse

	}

	/* This methods takes the parameter searchString and returns an object */

	public QuoteResponse getData(String searchString) throws IOException {
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
