import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

public class indexgen {

	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		String line = "";
		String file = sc.nextLine();
		while (!line.equals("**")){
			file += line;
			line = sc.nextLine();	
		}
		String pattern = "(\\&|\\*|\\{.*?\\})";
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(file);

		ArrayList <ArrayList<String>> documents = new ArrayList<>();
		documents.add(new ArrayList<String>());
		int document = 0;
		while (m.find()){
			line = m.group(0);
			if (line.equals("*")){
				document++;
				documents.add(new ArrayList<String>());
			} else {
				documents.get(document).add(line);
			}
		}
		
		for (int i = 0; i < documents.size()-1; i++){
			System.out.println("DOCUMENT " + (i+1));
			ArrayList <String> doc = documents.get(i);
			HashMap <String, key> keys = new HashMap<>();
			int page = 1;
			for (int d = 0; d < doc.size(); d++){
				String token = doc.get(d);
				if (token.equals("&")){
					page++;
				} else {
					String text = extractText(token);
					String primary = extractPrimary(token);
					String secondary = extractSecondary(token);
					if (keys.containsKey(text)){
						keys.get(text).insert(text, primary, secondary, page);
					} else {
						key k = new key();
						k.insert(text, primary, secondary, page);
						keys.put(text, k);
					}
				}
			}
			System.out.println("pls");
		}
	}
	
	public static String extractText(String in){
		String text = "";
		in = in.substring(1);
		if (in.startsWith(" ")){
			in = in.substring(1);
		}
		for (int i = 0; i < in.length()-1; i++){
			char t = in.charAt(i);
			switch (t) {
			case ('%'):
				if (text.endsWith(" ")){
					text = text.substring(0, text.length()-1);
				}
				return text;
			case ('$'):
				if (text.endsWith(" ")){
					text = text.substring(0, text.length()-1);
				}
				return text;
			default:
				text += t;
			}
		}
		if (text.endsWith(" ")){
			text = text.substring(0, text.length()-1);
		}
		return text;
	}
	public static String extractPrimary(String in){
		String text = "";
		if (in.contains("%")){
			in = in.split(Pattern.quote("%"))[1];
			if (in.startsWith(" ")){
				in = in.substring(1);
			}
			for (int i = 0; i < in.length()-1; i++){
				char t = in.charAt(i);
				switch (t) {
				case ('$'):
					if (text.endsWith(" ")){
						text = text.substring(0, text.length()-1);
					}
					return text;
				default:
					text += t;
				}
			}
		}
		if (text.endsWith(" ")){
			text = text.substring(0, text.length()-1);
		}
		return text;		
	}
	public static String extractSecondary(String in){
		String text = "";
		if (in.contains("$")){
			in = in.split(Pattern.quote("$"))[1];
			for (int i = 1; i < in.length()-1; i++){
				text += in.charAt(i);				
			}
		}
		if (text.startsWith(" ")){
			text = text.substring(1);
		}
		if (text.endsWith(" ")){
			text = text.substring(0, text.length()-1);
		}
		return text;	
	}
}

class key {
	ArrayList <Integer> pages = new ArrayList<>();
	HashMap <String, key> children = new HashMap<>();
	String primary = "";
	
	public void insert(String text, String pr, String sec, int page){
		primary = pr;
		if (!sec.equals("")){
			key k = new key();
			k.insert(sec, "", "", page);
			children.put(text, k);
		} else {
			if (!pages.contains(page)){
				pages.add(page);
			}
		}
	}
}