package shop.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import shop.model.BrandFilterMetaData;
import shop.model.FilterMetaData;
import shop.model.PriceFilterMetaData;
import shop.model.WeightFilterMetaData;

public class FilterParamParser {

	public static FilterMetaData parse(List<String> weightFilters, List<String> priceFilters,
			Set<String> brandFilters) {
		FilterMetaData filterMetaData = new FilterMetaData();
		if ((weightFilters == null || weightFilters.isEmpty()) && (priceFilters == null || priceFilters.isEmpty())
				&& (brandFilters == null || brandFilters.isEmpty())) {
			return filterMetaData;
		}

		if (weightFilters != null && weightFilters.size() > 0) {

			List<WeightFilterMetaData> listWeightRangeFilterMetaData = weightFilters.parallelStream()
					.map(weightFilterString -> createWeightFilterMetaData(weightFilterString))
					.collect(Collectors.toList());
			filterMetaData.setWeightFilters(listWeightRangeFilterMetaData);

		}
		if (priceFilters != null && priceFilters.size() > 0) {
			Set<PriceFilterMetaData> listPriceFilterMetaData = priceFilters.parallelStream()
					.map(weightFilterString -> createPriceFilterMetaData(weightFilterString))
					.collect(Collectors.toSet());
			filterMetaData.setPriceFilters(listPriceFilterMetaData);

		}
		if (brandFilters != null && brandFilters.size() > 0) {

			Set<BrandFilterMetaData> brandFilterMetaDataSet = brandFilters.parallelStream()
					.map(brandName -> createBranFilterMetaData(brandName)).collect(Collectors.toSet());
			filterMetaData.setBrandFilters(brandFilterMetaDataSet);
		}
		return filterMetaData;

	}

	static BrandFilterMetaData createBranFilterMetaData(String brand) {
		BrandFilterMetaData brandFilterMetaData = new BrandFilterMetaData();
		brandFilterMetaData.setBrandName(brand);
		brandFilterMetaData.setFlag(true);
		return brandFilterMetaData;
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
		priceFilterMetaData.setFlag(true);
		return priceFilterMetaData;
	}

	private static PriceFilterMetaData createGreaterThanPriceMetaData(String weightFilterString) {
		PriceFilterMetaData priceFilterMetaData = new PriceFilterMetaData();
		priceFilterMetaData.setV1(Integer.parseInt(weightFilterString.substring(1, weightFilterString.length())));
		priceFilterMetaData.setFilterCriteria("gt");
		priceFilterMetaData.setFlag(true);
		return priceFilterMetaData;
	}

	static WeightFilterMetaData createLesserThanWeightMetaData(String weightFilterString) {
		WeightFilterMetaData weightFilterMetaData = createWeightDefaultCaseFilterMetaData(
				weightFilterString.substring(1, weightFilterString.length()));
		weightFilterMetaData.setFilterCriteria("lt");
		weightFilterMetaData.setFlag(true);
		return weightFilterMetaData;

	}

	static WeightFilterMetaData createGreaterThanWeightMetaData(String weightFilterString) {
		WeightFilterMetaData weightFilterMetaData = createWeightDefaultCaseFilterMetaData(
				weightFilterString.substring(1, weightFilterString.length()));
		weightFilterMetaData.setFilterCriteria("gt");
		weightFilterMetaData.setFlag(true);
		return weightFilterMetaData;

	}

	static WeightFilterMetaData createWeightDefaultCaseFilterMetaData(String weightFilterString) {
		WeightFilterMetaData weightFilterMetaData = parseWeightFilterMetaData(weightFilterString);
		if (weightFilterMetaData.getFilterCriteria() == null)
			weightFilterMetaData.setFilterCriteria("eq");
		weightFilterMetaData.setFlag(true);
		return weightFilterMetaData;
	}

	static PriceFilterMetaData createPriceDefaultCaseFilterMetaData(String weightFilterString) {
		PriceFilterMetaData priceFilterMetaData = parsePriceFilterMetaData(weightFilterString);
		if (priceFilterMetaData.getFilterCriteria() == null)
			priceFilterMetaData.setFilterCriteria("eq");
		priceFilterMetaData.setFlag(true);
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
		weightFilterMetaData.setFlag(true);
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
		priceFilterMetaData.setFlag(true);
		return priceFilterMetaData;
	}

}
