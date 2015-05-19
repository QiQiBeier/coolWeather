package model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import db.MySqliteOpenHelper;
//该类封装了常用的数据库操作
public class CoolWeatherDB {
	//数据库名
	public static final String DB_NAME="cool_weather";
	//数据库版本
	public static final int VERSION=1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	//构造方法私有化
	private CoolWeatherDB(Context context){
		MySqliteOpenHelper dbHelper=new MySqliteOpenHelper(context, DB_NAME, null, VERSION);
		SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
	}
	public synchronized CoolWeatherDB getInstance(Context context) {
		if (coolWeatherDB==null) {
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	//将provience的一个实例存储到数据库
	public void saveProvience(Provience provience){
		if (provience!=null) {
			ContentValues values=new ContentValues();
			values.put("province_name", provience.getProvienceName());
			values.put("province_code", provience.getProvienceCode());
			db.insert("province", null, values);
		}
	}
	//从数据库读取全国所有的省份信息
	public List<Provience> loadProviences() {
		List<Provience> list=new ArrayList<Provience>();
		Cursor cursor=db.query("province", null, null, null,null,null, null);
		if (cursor.moveToFirst()) {
			Provience province=new Provience();
			province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			province.setProvienceName(cursor.getString(cursor.getColumnIndex("privince_name")));
			province.setProvienceCode(cursor.getString(cursor.getColumnIndex("privince_code")));
			list.add(province);
		}
		return list;
	}
	//将city实例存储到数据库相应的表中
	public void saveCity(City city) {
		 if (city!=null) {
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvienceId());
			db.insert("city", null, values);
		}
	}
	//从数据库读取某省下所有的城市信息
	public List<City> loadCities(int provinceId) {
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("city", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do{
			City city=new City();
			city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvienceId(cursor.getInt(cursor.getColumnIndex("province_id")));
			list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
	}
//将county实例存储到数据库
	public void saveCounty(County county) {
		if (county!=null) {
			ContentValues values=new ContentValues();
			values.put("county_id", county.getId());
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("county", null, values);
		}
	}
//从数据库读取某城市下所有的县信息
	public List<County> loadCounties(City city) {
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("county", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				County county=new County();
				county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				list.add(county);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
