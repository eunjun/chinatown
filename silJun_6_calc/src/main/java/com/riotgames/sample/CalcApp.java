package Calcu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CalcApp {
	
	private static final Map<Character, Integer> basic = new HashMap<Character, Integer>();
	static{
		basic.put('-', 1);
		basic.put('+', 1);
		basic.put('*', 2);
		basic.put('/', 2);
		basic.put('(', 0);
	}
	
	

	public double calc(String[] tokens){
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tokens.length; i++) {
			
			if(isNumeric(tokens[i])){
				sb.append(tokens[i]);
			}
			else if(tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")
			|| tokens[i].equals("(") || tokens[i].equals(")")){
				sb.append(tokens[i]);
			}
		}
		String str = toSuffix(sb.toString());
		
		return Double.parseDouble(dealEquation(str));
	}
	
	
	public static void main(String[] args) {
		final CalcApp app = new CalcApp();
		final StringBuilder outputs = new StringBuilder();
		Arrays.asList(args).forEach(value -> outputs.append(value + " "));
		System.out.println("Addition of values: " + outputs + " = ");
		System.out.println(app.calc(args));
	}
	
	public String toSuffix(String infix){
		List<String> queue = new ArrayList<String>();
		List<Character> stack = new ArrayList<Character>();
		char[] charArr = infix.trim().toCharArray();
		String standard = "*/+-()";
		
		char ch = '&'; 
		int len = 0;
		
		for (int i = 0; i < charArr.length; i++) {
			ch = charArr[i]; 
			
			if(Character.isDigit(ch)){ 
				len++;
			}
			else if(Character.isLetter(ch)){ 
				len++;
			}
			else if(ch == '.'){ 
				len++;
			}
			else if(Character.isSpaceChar(ch)){
				
				if(len > 0){
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));
					len = 0;
				}
				continue;   
			}
			else if(standard.indexOf(ch) != -1){ 
				if(len > 0){
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i -len, i)));
					len = 0;
				}
				if(ch == '('){ 
					stack.add(ch);
					continue;
				}
				if(!stack.isEmpty()){ 
					int size = stack.size()-1; 
					boolean flag = false; 
					while(size >=0 && ch ==')' && stack.get(size) != '('){
						queue.add(String.valueOf(stack.remove(size)));
						size--;
						flag = true;
					}
					while(size >= 0 && !flag && basic.get(stack.get(size)) >= basic.get(ch)){
						queue.add(String.valueOf(stack.remove(size)));
						size--;
					}
				}
				if(ch != ')'){ 
					stack.add(ch);
				}
				else{ 
					stack.remove(stack.size() - 1);
				}
				
			}
			if(i == charArr.length -1){ 
				if(len > 0){
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i-len+1, i+1)));
				}
				int size = stack.size()-1; 
				while(size >= 0){
					queue.add(String.valueOf(stack.remove(size)));
					size --;
				}
			}
		}
		return queue.stream().collect(Collectors.joining(","));
		
	}


}
