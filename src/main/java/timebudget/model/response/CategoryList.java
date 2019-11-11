package timebudget.model.response;

import java.util.List;

import com.google.gson.annotations.Expose;

import timebudget.model.Category;

public class CategoryList {

	@Expose
	private List<Category> categories = null;

	public CategoryList() {}

	public CategoryList(List<Category> categories) {
		this.categories = categories;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}