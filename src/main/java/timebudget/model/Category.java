package timebudget.model;

import java.time.LocalDateTime;

public class Category {

	public static final int NO_CATEGORY_ID = -1;
	public static final int NO_Category_ID = -1;
	public static final int NO_DELETED_AT = -1;

	private int categoryID = NO_Category_ID;
	private int id = NO_CATEGORY_ID; // auto created by database, may be null
	private String description = null;
	private int deletedAt = NO_DELETED_AT;

	public static final int AUTH_TOKEN_SIZE = 16;

	/**
	 * Default constructor for serialization
	 *
	 * @post a new Category has been created.
	 */
	private Category(){}

	/**
	 * Constructor that creates a Category with all fields popoulated
	 * @param id the category's id
	 * @param description the category's description
	 * @param CategoryID the category's CategoryID
	 * @pre Categoryname and password parameters are not null;
	 * @post a new Category is created with the given Categoryname and password
	 */
	public Category(int id, String description, int categoryID) {
		this.id = id;
		this.description = description;
		this.categoryID = categoryID;
	}

	/**
	 * Constructor that creates a new category with a given Auth token
	 * @param token the auth token for the Category to be created.
	 *
	 * @pre auth token param is not null
	 * @post a new Category is created with the given auth token
	 */
	// public Category(String token) { this.token = token; }

	/**
	 * A copy constructor for a new Category.
	 * @param Category the Category object to be copied.
	 *
	 * @pre Category param is not null
	 * @post A new Category is created that is a copy of the provided Category.
	 * @Post the new Category has the same Categoryname, password, fullname, authToken,
	 * and categoryID as the provided Category.
	 */
	public Category(Category category) {
		this.id = category.getCategoryID();
		this.description = category.getDescription();
		this.userID = category.getUserID();
		this.deleted_at = category.getDeletedAt();
	}


	/**
	 * Returns this Category's name.
	 * @return the value of this Category's Categoryname
	 *
	 * @post retVal == this.Categoryname
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns this Category's password.
	 * @return the value of this Category's password
	 *
	 * @post retVal == this.password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns this Category's fullname.
	 * @return the value of this Category's fullname
	 *
	 * @post retVal == this.fullname
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Method to set the fullname of this Category
	 * @param fullName the value to be stored as this Category's fullName
	 *
	 * @post retVal == this.fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Returns this Category's CategoryID.
	 * @return the value of this Category's CategoryID
	 *
	 * @post retVal == this.CategoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int id) {
		categoryID = id;
	}

	/**
	 * Method to compare two objects to determine if the other is the same as this one.
	 * @param o the other object to be compared to this Category
	 * @return true/false depending on whether both this Category and o are the same object
	 *
	 * @post this == o || this != o
	 */
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		Category category = (Category) object;
		return categoryID == category.categoryID &&
				id == category.id &&
				deletedAt == category.deletedAt &&
				java.util.Objects.equals(description, category.description);
	}

	/**
	 * This method creates a hashing code for this CategoryObject based on this.Categoryname, this.password,
	 * and this.CategoryID.
	 * @return the hash code for this Category object
	 *
	 * @pre this != null
	 * @pre this.Categoryname != null
	 * @post a unique hash code for this Category has been generated.
	 */
	@Override
	public int hashCode() {
		int result = Categoryname.hashCode();
		result = 31 * result + password.hashCode();
		result = 31 * result + CategoryID;
		return result;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "Category{" +
				"categoryID=" + categoryID +
				", id=" + id +
				", description='" + description + '\'' +
				", deletedAt=" + deletedAt +
				'}';
	}
}

