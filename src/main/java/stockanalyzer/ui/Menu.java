package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* The class MenuEntry takes generic type T. It has a method MenuEntry (the single menu items), which
consist of two Strings k, t and the generic type T r

 */

class MenuEntry<T> {
	protected MenuEntry(String k, String t, T r) {
		key = k;
		text = t;
		element = r;
	}
	protected String key;
	protected String text;
	protected T element;
}

/* The class Menu also has the generic type T as parameter. This class displays the Menu itself.

 */

public class Menu<T> {

	private static final String NEW_LINE = "\n";
	
	private String title; /* Renamed from titel for consistency. */

	/* This parameter includes the MenuEntries as a list, called menuEntries (renamed from
	menüEinträge for consistency. Each menuEntry is a title.
	 */
	private List<MenuEntry<T>> menuEntries;

	/* Construtor Menu, which takes the string title as an argument. menuEntries is specified
	as an ArrayList. titel is this.setTitle.*/
	Menu(String title) {
		menuEntries = new ArrayList<>();
		this.setTitle(title); // Renamed from setTitel for consistency.
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getTitle() { // Renamed from getTitel for consistency.
		return title;
	}

	void insert(String key, String text, T element) {
		menuEntries.add(new MenuEntry<>(key, text, element));
	}

	/* Prints out te menu */

	/* This method is currently not used.*/

	T exec() {
		System.out.println(NEW_LINE + NEW_LINE + title);
		for (int i = 0; i < title.length(); i++)
			System.out.print("*");

		/* Iterates over the menuEntries via a forEach loop. */

		System.out.print(NEW_LINE);
		menuEntries.forEach(m -> System.out.println(m.key + ")\t" + m.text));
		System.out.print(NEW_LINE);
		
		/* Takes the user input for a specific function */

		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		do {
			String value = "\0";
			System.out.print(">");
			try {
				value = inReader.readLine();
			} catch (IOException e) {
				System.out.println("Error reading from cmd:" + e.toString());
				System.out.println(NEW_LINE);
				System.out.println("Exit with Ctrl C");
			}
			if (value.length() > 0) {
				for (MenuEntry<T> m : menuEntries)
					if (m.key.trim().equalsIgnoreCase(value.trim()))
						return m.element;

			}
			System.out.println("Wrong input");
		} while (true);
	}

	/* This method has no function yet. */
	@Override
	public String toString() {
		return getTitle();
	}
}
