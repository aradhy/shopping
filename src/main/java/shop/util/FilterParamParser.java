package shop.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import shop.model.FilterMetaData;
import shop.model.PriceFilterMetaData;
import shop.model.WeightFilterMetaData;

public class FilterParamParser {

	public static FilterMetaData parse(List<String> weightFilters, List<String> priceFilters,
			Set<String> brandFilters) {
		if (weightFilters!=null && priceFilters!=null && priceFilters!=null &&   weightFilters.isEmpty() && priceFilters.isEmpty()) {
			return null;
		}

		FilterMetaData filterMetaData = new FilterMetaData();
		if (weightFilters != null) {

			List<WeightFilterMetaData> listWeightRangeFilterMetaData = weightFilters.parallelStream()
					.map(weightFilterString -> createWeightFilterMetaData(weightFilterString))
					.collect(Collectors.toList());
			filterMetaData.setWeightFilters(listWeightRangeFilterMetaData);

		}
		if (priceFilters != null) {
			Set<PriceFilterMetaData> listPriceFilterMetaData = priceFilters.parallelStream()
					.map(weightFilterString -> createPriceFilterMetaData(weightFilterString))
					.collect(Collectors.toSet());
			filterMetaData.setPriceFilters(listPriceFilterMetaData);

		}
		if (brandFilters != null) {
			filterMetaData.setBrandFilters(brandFilters);
		}
		return filterMetaData;

	}

	static WeightFilterMetaData createWeightFilterMetaData(String weightFilterString) {
		char filterCheck = weightFilterString.charAt(0);

		switch (filterCheck) {
		case '>':
			return createGreaterThanWeightMetaData(weightFilterString);

		case '<':
			return createLesserThanWeightMetaData(weightFilterString);

		default:
			return createWeightDefaultCaseFilterMetaData(weightFilterString);

		}

	}

	static PriceFilterMetaData createPriceFilterMetaData(String weightFilterString) {
		char filterCheck = weightFilterString.charAt(0);

		switch (filterCheck) {
		case '>':
			return createGreaterThanPriceMetaData(weightFilterString);

		case '<':
			return createLesserThanPriceMetaData(weightFilterString);

		default:
			return createPriceDefaultCaseFilterMetaData(weightFilterString);

		}

	}

	private static PriceFilterMetaData createLesserThanPriceMetaData(String priceFilterString) {
		PriceFilterMetaData priceFilterMetaData = new PriceFilterMetaData();
		priceFilterMetaData.setV1(Integer.parseInt(priceFilterString.substring(1, priceFilterString.length())));
		priceFilterMetaData.setFilterCriteria("lt");
		return priceFilterMetaData;
	}

	private static PriceFilterMetaData createGreaterThanPriceMetaData(String weightFilterString) {
		PriceFilterMetaData priceFilterMetaData = new PriceFilterMetaData();
		priceFilterMetaData.setV1(Integer.parseInt(weightFilterString.substring(1, weightFilterString.length())));
		priceFilterMetaData.setFilterCriteria("gt");
		return priceFilterMetaData;
	}

	static WeightFilterMetaData createLesserThanWeightMetaData(String weightFilterString) {
		WeightFilterMetaData weightFilterMetaData = createWeightDefaultCaseFilterMetaData(
				weightFilterString.substring(1, weightFilterString.length()));
		weightFilterMetaData.setFilterCriteria("lt");
		return weightFilterMetaData;

	}

	static WeightFilterMetaData createGreaterThanWeightMetaData(String weightFilterString) {
		WeightFilterMetaData weightFilterMetaData = createWeightDefaultCaseFilterMetaData(
				weightFilterString.substring(1, weightFilterString.length()));
		weightFilterMetaData.setFilterCriteria("gt");
		return weightFilterMetaData;

	}

	static WeightFilterMetaData createWeightDefaultCaseFilterMetaData(String weightFilterString) {
		WeightFilterMetaData weightFilterMetaData = parseWeightFilterMetaData(weightFilterString);
		if (weightFilterMetaData.getFilterCriteria() == null)
			weightFilterMetaData.setFilterCriteria("eq");
		return weightFilterMetaData;
	}

	static PriceFilterMetaData createPriceDefaultCaseFilterMetaData(String weightFilterString) {
		PriceFilterMetaData priceFilterMetaData = parsePriceFilterMetaData(weightFilterString);
		if (priceFilterMetaData.getFilterCriteria() == null)
			priceFilterMetaData.setFilterCriteria("eq");
		return priceFilterMetaData;
	}

	static WeightFilterMetaData parseWeightFilterMetaData(String weightFilterString) {
		String[] weightFilterArray = weightFilterString.split("-");
		WeightFilterMetaData weightFilterMetaData = new WeightFilterMetaData();
		weightFilterMetaData.setV1(Integer.parseInt(weightFilterArray[0]));
		weightFilterMetaData.setU1(weightFilterArray[1]);
		if (weightFilterArray.length > 2) {
			weightFilterMetaData.setV2(Integer.parseInt(weightFilterArray[2]));
			weightFilterMetaData.setU2(weightFilterArray[3]);
			weightFilterMetaData.setFilterCriteria("bw");
		}

		return weightFilterMetaData;
	}

	static PriceFilterMetaData parsePriceFilterMetaData(String weightFilterString) {
		String[] priceFilterArray = weightFilterString.split("-");
		PriceFilterMetaData priceFilterMetaData = new PriceFilterMetaData();
		priceFilterMetaData.setV1(Integer.parseInt(priceFilterArray[0]));
		if (priceFilterArray.length > 1) {
			priceFilterMetaData.setV2(Integer.parseInt(priceFilterArray[1]));
			priceFilterMetaData.setFilterCriteria("bw");
		}

		return priceFilterMetaData;
	}

}
