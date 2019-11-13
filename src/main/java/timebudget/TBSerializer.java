package timebudget;

import com.google.gson.Gson;

public class TBSerializer {

	public static String ObjToJson(Object obj){
		return new Gson().toJson(obj);
	}

	public static Object jsonToObj(String str, Class c){
		Gson gson = new Gson();
		return gson.fromJson(str, c);
	}
}
