package com.fanmila.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.fanmila.service.decorator.AbstractAppServiceDecorator;
import org.springframework.stereotype.Service;

@Service
public class AppServiceCombination {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends AbstractAppServiceDecorator> AppServiceFace decorator(AppServiceFace service, Class... decorators) {
		AppServiceFace newSevice = null;
		for (Class<T> clazz : decorators) {
			Constructor<T> con = null;
			try {
				con = clazz.getConstructor(AppServiceFace.class);
				if (newSevice == null) {
					newSevice = (AppServiceFace) con.newInstance(service);
				} else {
					newSevice = (AppServiceFace) con.newInstance(newSevice);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return newSevice;
	}

}
