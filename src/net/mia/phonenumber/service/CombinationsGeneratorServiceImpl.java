package net.mia.phonenumber.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.mia.phonenumber.model.PhoneNumberResults;

@Service
public class CombinationsGeneratorServiceImpl implements CombinationsGeneratorService{
	
	static Map<Character, String> keyBoard = new HashMap<>();

	static {
		keyBoard.put('0', "0");
		keyBoard.put('1', "1");
		keyBoard.put('2', "2abc");
		keyBoard.put('3', "3def");
		keyBoard.put('4', "4ghi");
		keyBoard.put('5', "5jkl");
		keyBoard.put('6', "6mno");
		keyBoard.put('7', "7pqrs");
		keyBoard.put('8', "8tuv");
		keyBoard.put('9', "9wxyz");
	}
	
	@Override
	public PhoneNumberResults getAllResults(String input) {
		PhoneNumberResults allCombinations = new PhoneNumberResults();
		LinkedList<String> results = new LinkedList<>();
		results.add("");
		for (int i = 0; i < input.length(); i++) {
			String chars = keyBoard.get(input.charAt(i));
			for (int j = results.size(); j > 0; j--) {
				String tmp = results.poll();
				for (int k = 0; k < chars.length(); k++) {
					results.add(tmp + chars.charAt(k));
				}
			}
		}
		allCombinations.setTotal(results.size());
		allCombinations.setData(results);
		allCombinations.setNumbers(input);
		return allCombinations;
	}
	
	@Override
	public PhoneNumberResults getResultsPerPage(int pageNumber, String numbers){
		PhoneNumberResults allCombinations = getAllResults(numbers);
		allCombinations.setCurrentPage(pageNumber);
		allCombinations.setData(allCombinations.getData().subList(PhoneNumberResults.rowPerPage * pageNumber - PhoneNumberResults.rowPerPage, PhoneNumberResults.rowPerPage * pageNumber > allCombinations.getData().size()
				? allCombinations.getData().size() : PhoneNumberResults.rowPerPage * pageNumber));
		return allCombinations;
	}
}
