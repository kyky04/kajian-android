package uinbdg.skripsi.pesantren.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItemPesantren implements Serializable {

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("jumlah_pengajar")
	private String jumlahPengajar;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jumlah_santri")
	private String jumlahSantri;

	@SerializedName("foto")
	private String foto;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("updated_by")
	private String updatedBy;

	@SerializedName("pemilik")
	private String pemilik;

	@SerializedName("id")
	private int id;

	@SerializedName("no_telp")
	private String noTelp;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("jarak")
	private double jarak;

	public double getJarak() {
		return jarak;
	}

	public void setJarak(double jarak) {
		this.jarak = jarak;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setJumlahPengajar(String jumlahPengajar){
		this.jumlahPengajar = jumlahPengajar;
	}

	public String getJumlahPengajar(){
		return jumlahPengajar;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJumlahSantri(String jumlahSantri){
		this.jumlahSantri = jumlahSantri;
	}

	public String getJumlahSantri(){
		return jumlahSantri;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUpdatedBy(String updatedBy){
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy(){
		return updatedBy;
	}

	public void setPemilik(String pemilik){
		this.pemilik = pemilik;
	}

	public String getPemilik(){
		return pemilik;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setNoTelp(String noTelp){
		this.noTelp = noTelp;
	}

	public String getNoTelp(){
		return noTelp;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"DataItemPesantren{" +
			"latitude = '" + latitude + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",jumlah_pengajar = '" + jumlahPengajar + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",nama = '" + nama + '\'' + 
			",jumlah_santri = '" + jumlahSantri + '\'' + 
			",foto = '" + foto + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",pemilik = '" + pemilik + '\'' + 
			",id = '" + id + '\'' + 
			",no_telp = '" + noTelp + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}