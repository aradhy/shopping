package shop.daoservice;

public class DaoQueries {
	
	public static  String queryProductFilterWeight="select prod from Product prod  join ProductAvail prod_avail on prod.code=prod_avail.productId join SubCategory subCat on prod.subId=subCat.id join Category cat on subCat.categoryId=cat.id where cat.id=:categoryId and subCat=:subCategoryId and prod_avail.weightUnit>=:startWeight and prod_avail.weight=:startWeightUnit"
	+"union  select prod from Product prod  join ProductAvail prod_avail on prod.code=prod_avail.productId join SubCategory subCat on prod.subId=subCat.id join Category cat on subCat.categoryId=cat.id where cat.id=:categoryId and subCat=:subCategoryId and prod_avail.weightUnit<=:endWeight and prod_avail.weight=:endWeightUnit";
}
