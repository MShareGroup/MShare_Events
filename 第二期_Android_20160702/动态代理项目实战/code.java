package com.test.test;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;



/**
 * 监控所有UI线程中的IO操作
 * @author gaoyihang
 *
 */
public class IOAsyncMonitor implements InvocationHandler {

	private final static String TAG = "TAG_IOAsyncMonitor";

	private static IOAsyncMonitor mInstance;

	private Object old_os; // libcore.io.Os

	private static ExecutorService newSingleThreadExecutor;

	private IOAsyncMonitor() {
	}

	public synchronized static IOAsyncMonitor getInstance() {
		if (mInstance == null) {
			mInstance = new IOAsyncMonitor();
			newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		}
		return mInstance;
	}


	/**
	 * 安装拦截器
	 */
	public void install() {

		Class class_LibCore = null;
		try {
			class_LibCore = Class.forName("libcore.io.Libcore");
		} catch (ClassNotFoundException e) {
			// ingore
		}
		old_os = ReflectUtils.getStaticFieldValue(class_LibCore, "os");

		if (old_os == null) {
			LogUtil.d(TAG, "old_os = null");
			return;
		}

		Class<?> class_os = old_os.getClass();
		Class<?>[] interfaces = getAllInterfaces(class_os);
		Object new_os = Proxy.newProxyInstance(class_os.getClassLoader(), interfaces, this);
		ReflectUtils.writeField(class_LibCore, "os", null, new_os, true);
	}

	/**
	 * 循环遍历该类的所有父类的接口
	 * @param clz
	 * @return
	 */
	private Class<?>[] getAllInterfaces(Class clz) {
		ArrayList<Class> re = new ArrayList<Class>();
		Class<?>[] ifss = clz.getInterfaces();
		for (Class<?> ifs : ifss) {
			if (!re.contains(ifs)) {
				re.add(ifs);
			}
		}

		Class superclass = clz.getSuperclass();
		while (superclass != null) {
			Class<?>[] sifss = superclass.getInterfaces();
			for (Class<?> ifs : sifss) {
				if (!re.contains(ifs)) {
					re.add(ifs);
				}
			}
			superclass = superclass.getSuperclass();
		}
		return re.toArray(new Class[re.size()]);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
			doInterrupt();
		}

		Object invoke = null;
		try {
			invoke = method.invoke(old_os, args);
		} catch (Throwable e) {
			throw e.getCause();
		}
		return invoke;
	}

	
	/**
	 * 上报服务器
	 */
	public void doInterrupt() {
		
	}
	
	
}
