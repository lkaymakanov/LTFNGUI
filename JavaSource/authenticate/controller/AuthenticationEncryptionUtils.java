package authenticate.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import clframe.GEEngineUtils;
import token.ByteArrays;
import token.IDecoderFactory;
import token.IEncoderFactory;

public class AuthenticationEncryptionUtils {
	
	private static ClassLoader cl;
	static{
		List<byte[]> clBytes = new ArrayList<>();
		clBytes.add(ByteArrays.decoderFactory);
		clBytes.add(ByteArrays.encoderFactory);
		clBytes.add(ByteArrays.encoderDecoder);
		try {
			cl =  GEEngineUtils.getClassLoader(clBytes, null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object getInstance(Class type,
					Class<?>[] argtypes,
					Object[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException,  NoSuchMethodException, InvocationTargetException  {
	    if (argtypes == null || args == null)
		return type.newInstance();
	    return type.getConstructor(argtypes).newInstance(args);
	}


	public static IEncoderFactory getEncoderFactory(String pass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException{
		IEncoderFactory ef = (IEncoderFactory)getInstance(cl.loadClass("token.SimpleOffsetEncoderFactory"), new Class[]{String.class}, new Object[]{pass});
		return ef;
	}
	
	public static IDecoderFactory getDecoderFactory(String pass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException{
		IDecoderFactory def = (IDecoderFactory)getInstance(cl.loadClass("token.SimpleOffsetDecoderFactory"), new Class[]{String.class}, new Object[]{pass});
		return def;
	}
}
