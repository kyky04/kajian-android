package uinbdg.skripsi.kajian.Model;


import com.google.gson.annotations.SerializedName;


public class KajianItem{

	@SerializedName("tema")
	private String tema;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("pemateri")
	private String pemateri;

	@SerializedName("waktu_kajian")
	private String waktuKajian;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_mosque")
	private int idMosque;

	@SerializedName("deleted_at")
	private Object deletedAt;

	public void setTema(String tema){
		this.tema = tema;
	}

	public String getTema(){
		return tema;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPemateri(String pemateri){
		this.pemateri = pemateri;
	}

	public String getPemateri(){
		return pemateri;
	}

	public void setWaktuKajian(String waktuKajian){
		this.waktuKajian = waktuKajian;
	}

	public String getWaktuKajian(){
		return waktuKajian;
	}

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setIdMosque(int idMosque){
		this.idMosque = idMosque;
	}

	public int getIdMosque(){
		return idMosque;
	}

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	@Override
 	public String toString(){
		return 
			"KajianItem{" + 
			"tema = '" + tema + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",pemateri = '" + pemateri + '\'' + 
			",waktu_kajian = '" + waktuKajian + '\'' + 
			",waktu = '" + waktu + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",id_mosque = '" + idMosque + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			"}";
		}
}