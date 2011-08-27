package anlaysisPage;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ParseTest {

	/**
	 * @param args
	 * @throws MongoException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		// TODO Auto-generated method stub
		Mongo mongo = new Mongo("10.50.15.201");
		DB db = mongo.getDB("LBSDatabase");
		DBCollection lbsinfo = db.getCollection("lbsinfo");
		DBObject query = new BasicDBObject();
		try {
			Document doc = Jsoup.connect(
					"http://traffic.sjtu.edu.cn/ap/history/").get();
			// System.out.println(doc);
			Elements links = doc.getElementsByTag("a");
			for (Element link : links) {
				String linkText = link.text();

				if (linkText.contains(".html")) {
					String timeString = linkText.substring(0,
							linkText.indexOf("."));
					query.put("time", timeString);
					System.out.println(query);
					if (lbsinfo.findOne(query) == null) {
						ApInfo apInfo = new ApInfo();
					    System.out.println(linkText.substring(0,linkText.indexOf(".")));

						apInfo.setTime(linkText.substring(0,
								linkText.indexOf(".")));
						Document doc1 = Jsoup.connect(
								"http://traffic.sjtu.edu.cn/ap/history/"
										+ linkText).get();
						Elements addrs = doc1.select("dt");
						Elements ipinfos = doc1.select("dd");
						int size = 0;
						int t = 0;
						for (Element addr : addrs) {

							if (addr.text().contains("]:")) {
								String apaddrinfo[] = addr.text().split(":");
								 System.out.println(apaddrinfo[0]);
								apInfo.setLocation(apaddrinfo[0]);
								size = Integer.parseInt(apaddrinfo[1]);
								// System.out.println(size);
								for (int i = 0; i < size; i++) {
									// System.out.println(ipinfos.get(t).text().trim());
									String ipinfo[] = ipinfos.get(t).text()
											.trim().split("->");
									apInfo.setIp(ipinfo[0].trim());

									String macssid = ipinfo[1].trim();
									String macaddr = macssid.substring(0,
											macssid.indexOf("("));
									// System.out.println(macaddr);
									apInfo.setMac(macaddr);
									// System.out.println(macssid.substring(macssid.indexOf("(")+1,
									// macssid.indexOf(")")));
									apInfo.setSSID(macssid.substring(
											macssid.indexOf("(") + 1,
											macssid.indexOf(")")));
									System.out.println(apInfo.toMongoDBObj());

									lbsinfo.insert(apInfo.toMongoDBObj());
									t++;

								}

							}
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
