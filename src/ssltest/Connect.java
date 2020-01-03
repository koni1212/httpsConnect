package ssltest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class Connect {

	public static void main(String[] args) {
		try {
			if (args == null || args.length != 1) {
				System.out.println("引数にhttps接続するURLを指定してください。");
				return;
			}
			URL url = new URL(args[0]);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.connect();
			dispEnv();
			dispContents(url);
			dispCerts(conn);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void dispEnv() {
		try {
			System.out.print("オペレーティングシステム名(os.name):");
			System.out.println(System.getProperty("os.name"));
			System.out.print("Javaバージョン(java.version):");
			System.out.println(System.getProperty("java.version"));

			InetAddress ia = InetAddress.getLocalHost();
			String ip = ia.getHostAddress();
			String hostname = ia.getHostName();
			System.out.println("IPアドレス(getLocalHost):" + ip);
			System.out.println("ホスト名(hostName):" + hostname);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static void dispCerts(HttpsURLConnection conn) {
		System.out.println("----サーバの証明書チェーンを表示----");
		try {
			Certificate[] certs = conn.getServerCertificates();
			for (Certificate cert : certs) {
				System.out.println(cert);
			}
		} catch (SSLPeerUnverifiedException e) {
			e.printStackTrace();
		}
	}

	public static void dispContents(URL url) {
		System.out.println("----コンテンツを表示----");
		try {
			InputStream strm = url.openStream();
			InputStreamReader in = new InputStreamReader(strm);
			BufferedReader inb = new BufferedReader(in);
			String line;
			while ((line = inb.readLine()) != null) {
				System.out.println(line);
			}
			inb.close();
			in.close();
			strm.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
