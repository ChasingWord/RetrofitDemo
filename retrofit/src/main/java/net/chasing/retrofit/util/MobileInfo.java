package net.chasing.retrofit.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.UUID;

public class MobileInfo {
	private static MobileInfo mMobileInfo = new MobileInfo();
	private HashMap<String, Object> deviceInfo;
	
	private MobileInfo(){
		deviceInfo = new HashMap<>();
	}

	public static MobileInfo getInstance(){
		return mMobileInfo;
	}
	
	public static final String PLATFORM = "platform";
	public static final String APP_VERSION = "appVersion";
	public static final String SYS_VERSION = "sysVersion";
	public static final String MODEL = "model";
	public static final String IMEI = "imei";
	public static final String NET_TYPE = "netType";
	public static final String MANUFACTURER = "manufacturer";
	public static final String SESSION = "session";
	public static final String USER_ID = "userId";
	public static final String SECURECODE = "secureCode";
	public static final String TOKEN = "token";

	/**
	 * 初始化设备信息
	 * 获取手机信息
	 * 
	 * @return
	 */
	public void initDeviceInfo(Context ctx) {
		String appVersion;
		PackageInfo packInfo = getPackageInfo(ctx);
		if (packInfo != null) {
			appVersion = packInfo.versionName;
		} else {
			appVersion = "";
		}

		deviceInfo.put(PLATFORM, "android");
		deviceInfo.put(APP_VERSION, appVersion); // string 当前客户端的所属的版本号
		deviceInfo.put(SYS_VERSION, Build.VERSION.RELEASE); //string  用户系统环境对应的版本号
		deviceInfo.put(MANUFACTURER, Build.MANUFACTURER); // string 手机制作商
		deviceInfo.put(MODEL, Build.MODEL); // string 手机型号
		deviceInfo.put(IMEI, getDeviceUuid(ctx).toString());//string 手机唯一标识
		deviceInfo.put(NET_TYPE, getCurrentNetTypeInt(ctx)); //int
		deviceInfo.put(MobileInfo.SESSION, "");
		deviceInfo.put(MobileInfo.USER_ID, 0l);
//		infos.put(SECURECODE, getSecureCode(ctx,packInfo));
	}

	public void setUserInfo(String session, long userId){
		deviceInfo.put(MobileInfo.SESSION, session);
		deviceInfo.put(MobileInfo.USER_ID, userId);
	}

	public HashMap<String, Object> getDeviceInfo(){
		return deviceInfo;
	}

	public static PackageInfo getPackageInfo(Context ctx){
		PackageInfo info = null;
		try {
			info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return  info;
	}

	public static String getSecureCode(Context ctx, PackageInfo pInfo) {
		String secureCode = ";" + ctx.getPackageName() ;
		Signature[] signatures = pInfo.signatures;
		try {
			byte[] cert = signatures[0].toByteArray();
			InputStream input = new ByteArrayInputStream(cert);
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			X509Certificate c = (X509Certificate) cf.generateCertificate(input);
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] publicKey = md.digest(c.getEncoded());
			secureCode = byte2HexFormatted(publicKey) + secureCode;
		} catch ( Exception e ) {
			secureCode = "49:F1:9D:6B:C0:2A:43:5F:0F:08:95:E0:22:B4:0A:1B:4A:57:47:90"+secureCode;
			e.printStackTrace();
		}
		return secureCode;
	}

	private static String byte2HexFormatted(byte[] arr) {
		StringBuilder str = new StringBuilder(arr.length * 2);
		for (int i = 0; i < arr.length; i++) {
			String h = Integer.toHexString(arr[i]);
			int l = h.length();
			if (l == 1)
				h = "0" + h;
			if (l > 2)
				h = h.substring(l - 2, l);
			str.append(h.toUpperCase());
			if (i < (arr.length - 1))
				str.append(':');
		}
		return str.toString();
	}


	/***
	 * 获取uuid
	 * 
	 * @param context
	 * @return
	 */
	public static UUID getDeviceUuid(Context context) {
		UUID uuid;


		final String androidId = Secure.getString(
				context.getContentResolver(), Secure.ANDROID_ID);

		try {
			if (!"9774d56d682e549c".equals(androidId)) {
				uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
			} else {
				final String deviceId = ((TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE))
						.getDeviceId();
				uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId
						.getBytes("utf8")) : UUID.randomUUID();
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return uuid;
	}
	
	/**
	 * 得到当前的手机网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static String getCurrentNetType(Context context) {
		String type = "";
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) {
			type = "null";
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			type = "wifi";
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			int subType = info.getSubtype();
			if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
					|| subType == TelephonyManager.NETWORK_TYPE_EDGE) {
				type = "2g";
			} else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
				type = "3g";
			} else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
				type = "4g";
			}
		}
		return type;
	}
	
	public static final int NET_TYPE_NULL = 0;
	public static final int NET_TYPE_WIFI = 1;
	public static final int NET_TYPE_2G = 2;
	public static final int NET_TYPE_3G = 3;
	public static final int NET_TYPE_4G = 4;
	
	public static int getCurrentNetTypeInt(Context context){
		switch(getCurrentNetType(context)){
		case "null":
			return NET_TYPE_NULL;
		case "wifi":
			return NET_TYPE_WIFI;
		case "2g":
			return NET_TYPE_2G;
		case "3g":
			return NET_TYPE_3G;
		case "4g":
			return NET_TYPE_4G;
			default:
				return NET_TYPE_NULL;
		}
	}
}
