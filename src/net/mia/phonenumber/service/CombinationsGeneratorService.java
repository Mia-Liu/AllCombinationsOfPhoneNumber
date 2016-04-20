package net.mia.phonenumber.service;

import net.mia.phonenumber.model.PhoneNumberResults;

public interface CombinationsGeneratorService {

	public PhoneNumberResults getAllResults(String digits);

	public PhoneNumberResults getResultsPerPage(int pageNumber, String numbers);

}
