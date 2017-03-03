import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import ResponseComparision.JsonComp;
import ResponseComparision.XmlComp;


public class TestClass {
	
	public String convertResponseIntoString(HttpResponse res) {
		String buff;
		String line="";
		BufferedReader brd = null;
		try {
			brd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
			while((buff = brd.readLine()) != null) {
//				System.out.println(buff1);
					line = line+buff;
				}
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return line;
	}

	private TrustManager[] getTrustingManager() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                // Do nothing
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                // Do nothing
            }

        } };
        return trustAllCerts;
    }
	
	void compareResponse(File f1, File f2) throws FileNotFoundException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
		FileReader fr1 = new FileReader(f1);
		FileReader fr2 = new FileReader(f2);
		BufferedReader br1 = new BufferedReader(fr1);
		BufferedReader br2 = new BufferedReader(fr2);
		String s1 = "";
		String s2 = "";
		int count = 1;
		Status st = new Status();
		HttpGet req1,req2;
		HttpResponse res1, res2;
		JsonComp jc = new JsonComp();
		XmlComp xc = new XmlComp();
		boolean isJson;
		boolean isXml=false;
		boolean flag=false;
		try {
			while((s1=br1.readLine()) != null) {
				s2 = br2.readLine();
				if(s2 == null) {
					System.out.println("different number of requests in both files.");
					st.setFlag(false);
					st.setReasons("number of records are more in file1");
					break;
				}
				HttpClient client1 = new DefaultHttpClient();
				/*SSLContext sc = SSLContext.getInstance("SSL");
		        sc.init(null, getTrustingManager(), new java.security.SecureRandom());
		        SSLSocketFactory socketFactory = new SSLSocketFactory(sc);
		        Scheme sch = new Scheme("https", 443, socketFactory);
		        client1.getConnectionManager().getSchemeRegistry().register(sch);*/
				req1 = new HttpGet(s1);
				res1 = client1.execute(req1);
				String res1Str = convertResponseIntoString(res1);
				isJson = jc.isJsonExp(res1Str);
				if(!isJson)
					isXml = xc.isXmlExp(res1Str);
				req1.releaseConnection();
				req2 = new HttpGet(s2);
				res2 = client1.execute(req2);
				String res2Str = convertResponseIntoString(res2);
				req2.releaseConnection();
				if(isJson) {
					flag = jc.compare(res1Str, res2Str);
				} else if (isXml) { 
					flag = xc.compare(res1Str, res2Str);
				}
				if(flag)
					System.out.println(s1+" equals "+s2);
				else {
					st.setFlag(false);
					st.setReasons("Response does not match for row number :"+count+" with Request at file 1 :"+s1+" NOT EQUALS Request at file 2 :"+s2);
					System.out.println(s1+" not equals "+s2);
				}
				
				count++;
				client1.getConnectionManager().closeExpiredConnections();
			}
			if((s2=br2.readLine()) != null) {
				System.out.println("different number of requests in both files.");
				st.setFlag(false);
				st.setReasons("number of records are more in file2");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n\n");
		System.out.println(st.isFlag()+", "+st.getReasons());
	}
	
	public static void main(String[] args) throws FileNotFoundException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
		// TODO Auto-generated method stub
		
		File f1 = new File("src/RequestFiles/f1.txt");
		File f2 = new File("src/RequestFiles/f2.txt");
		
		TestClass tc = new TestClass();
		tc.compareResponse(f1, f2);

	}

}
