package shop.service;

import java.util.HashMap;
import java.util.Map;

public class CheckWeightUnitService {
	
	Map<String,String> weightUnitMap=new HashMap<String,String>();
	
	
	CheckWeightUnitService()
	{
		weightUnitMap.put("kg","gram");
		weightUnitMap.put("gram","mg");
		weightUnitMap.put("litre","ml");
	}

	
	
	String checkForSmallerWeightUnit(String weightUnit)
	{
		
		if(weightUnitMap.containsKey(weightUnit))
		{
			return weightUnitMap.get(weightUnit);
		}
		
		return null;
		
	}
	
	String checkForGreaterWeightUnit(String weightUnit)
	{
		
		  for(Map.Entry<String,String> entry: weightUnitMap.entrySet()){
	            if(weightUnit.equals(entry.getValue())){
	                return entry.getKey();
	            }
	        }


		
		
		return null;
		
	}
}
