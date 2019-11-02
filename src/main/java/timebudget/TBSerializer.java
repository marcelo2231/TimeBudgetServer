package timebudget;

import com.google.gson.Gson;
import timebudget.model.Category;
import timebudget.model.Event;
import timebudget.model.TimePeriod;
import timebudget.model.User;
import timebudget.model.request.GetUsingCategoryIDRequest;
import timebudget.model.request.GetUsingEventIDRequest;
import timebudget.model.request.GetUsingUserIDRequest;
import timebudget.model.request.LoginRequest;

public class TBSerializer {
	
	public static String ObjToJson(Object obj){
		return new Gson().toJson(obj);
	}
	
	public static User jsonToUser(String userJson){
		Gson gson = new Gson();
		return gson.fromJson(userJson, User.class);
	}
	
	public static LoginRequest jsonToLoginRequest(String loginJson){
		Gson gson = new Gson();
		return gson.fromJson(loginJson, LoginRequest.class);
	}
	
	public static TimePeriod jsonToTimePeriod(String timePeriodJson){
		Gson gson = new Gson();
		return gson.fromJson(timePeriodJson, TimePeriod.class);
	}
	
	public static Event jsonToEvent(String eventJson){
		Gson gson = new Gson();
		return gson.fromJson(eventJson, Event.class);
	}
	
	public static Category jsonToCategory(String categoryJson){
		Gson gson = new Gson();
		return gson.fromJson(categoryJson, Category.class);
	}
	
	public static GetUsingCategoryIDRequest jsonToCategoryIDRequest(String requestJson){
		Gson gson = new Gson();
		return gson.fromJson(requestJson, GetUsingCategoryIDRequest.class);
	}
	
	public static GetUsingEventIDRequest jsonToEventIDRequest(String requestJson){
		Gson gson = new Gson();
		return gson.fromJson(requestJson, GetUsingEventIDRequest.class);
	}
	
	public static GetUsingUserIDRequest jsonToUserIDRequest(String requestJson){
		Gson gson = new Gson();
		return gson.fromJson(requestJson, GetUsingUserIDRequest.class);
	}
}
