package shop.dto;

public class DaoQueries {
	
	public static  String queryProductFilterWeight="select prod from Product prod  join ProductAvail prodAvail on prod.code=prodAvail.productId join SubCategory subCat on prod.subId=subCat.id join Category cat on subCat.categoryId=cat.id where ((prodAvail.weightUnit=:endWeightUnit and prodAvail.weight<=:endWeight) or (prodAvail.weightUnit=:startWeightUnit and prodAvail.weight>=:startWeight)) and cat.id=:categoryId and subCat.id=:subCategoryId";
}
