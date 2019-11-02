package timebudget;

import com.google.gson.Gson;
import timebudget.model.Event;
import timebudget.model.TimePeriod;
import timebudget.model.User;
import timebudget.model.request.GetUsingCategoryIDRequest;
import timebudget.model.request.GetUsingEventIDRequest;
import timebudget.model.request.GetUsingUserIDRequest;
import timebudget.model.request.LoginRequest;

public class TBSerializer {
	
	public String ObjToJson(Object obj){
		return new Gson().toJson(obj);
	}
	
	public User jsonToUser(String userJson){
		Gson gson = new Gson();
		return gson.fromJson(userJson, User.class);
	}
	
	public LoginRequest jsonToLoginRequest(String loginJson){
		Gson gson = new Gson();
		return gson.fromJson(loginJson, LoginRequest.class);
	}
	
	public TimePeriod jsonToTimePeriod(String timePeriodJson){
		Gson gson = new Gson();
		return gson.fromJson(timePeriodJson, TimePeriod.class);
	}
	
	public Event jsonToEvent(String eventJson){
		Gson gson = new Gson();
		return gson.fromJson(eventJson, Event.class);
	}
	
	public GetUsingCategoryIDRequest jsonToCategoryIDRequest(String requestJson){
		Gson gson = new Gson();
		return gson.fromJson(requestJson, GetUsingCategoryIDRequest.class);
	}
	
	public GetUsingEventIDRequest jsonToEventIDRequest(String requestJson){
		Gson gson = new Gson();
		return gson.fromJson(requestJson, GetUsingEventIDRequest.class);
	}
	
	public GetUsingUserIDRequest jsonToUserIDRequest(String requestJson){
		Gson gson = new Gson();
		return gson.fromJson(requestJson, GetUsingUserIDRequest.class);
	}
}
