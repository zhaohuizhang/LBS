package anlaysisPage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ApInfo {
	public String ip;
	public String mac;
	public String location;
	public String time;
	public String SSID;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public DBObject toMongoDBObj() {
		DBObject apinfo = new BasicDBObject();
		apinfo.put("time", this.getTime());
		apinfo.put("ip", this.getIp());
		apinfo.put("mac", this.getMac());
		apinfo.put("location", this.getLocation());
		apinfo.put("SSID", this.getSSID());
		return apinfo;
	}

}
