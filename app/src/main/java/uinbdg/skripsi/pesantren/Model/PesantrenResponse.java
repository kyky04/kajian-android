package uinbdg.skripsi.pesantren.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PesantrenResponse{

	@SerializedName("data")
	private List<DataItemPesantren> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemPesantren> data){
		this.data = data;
	}

	public List<DataItemPesantren> getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"PesantrenResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}