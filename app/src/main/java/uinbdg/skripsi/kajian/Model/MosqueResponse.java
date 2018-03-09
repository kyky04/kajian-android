package uinbdg.skripsi.kajian.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class MosqueResponse{

	@SerializedName("data")
	private List<DataItemMosque> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<DataItemMosque> data){
		this.data = data;
	}

	public List<DataItemMosque> getData(){
		return data;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"MosqueResponse{" + 
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}