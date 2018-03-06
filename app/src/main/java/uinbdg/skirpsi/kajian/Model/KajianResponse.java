package uinbdg.skirpsi.kajian.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class KajianResponse{

	@SerializedName("data")
	private List<DataItemKajian> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<DataItemKajian> data){
		this.data = data;
	}

	public List<DataItemKajian> getData(){
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
			"KajianResponse{" + 
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}