package com.teajey.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FinalClassWrapGenerator {
	private static String text;
	
	public static void main(String[] args) {
		
	}
	
	public static void generate(Class<?> t) {
		text = "package ";
		text += t.getPackage().getName() + ";\n\n";
		
		text += "public class " + t.getSimpleName() + "Wrapper extends " + t.getSuperclass().getSimpleName() + " {\n"
				+ "\t" + t.getSimpleName() + " item;\n\n"
				+ "\tpublic " + t.getSimpleName() + "Wrapper(";
		
		
		Constructor<?>[] constructors = t.getDeclaredConstructors();
		Constructor<?> longest = constructors[0];
		for (int i = 1; i < constructors.length; i++) {
			if (constructors[i].getParameters().length > longest.getParameters().length) {
				longest = constructors[i];
			}
		}
		Class<?>[] params = longest.getParameterTypes();
		for (int i = 0; i < params.length; i++) {
			text += params[i].getSimpleName() + " arg" + i + (i == params.length - 1 ? "" : ", ");
		}
		text += ") {\n"
				+ "\t\titem = new " + t.getSimpleName() + "(";
		for (int i = 0; i < params.length; i++) {
			text += "arg" + i + (i == params.length - 1 ? "" : ", ");
		}
		text += ");\n"
				+ "\t}\n";
		text += addAllAbstractMethods(t);
		text += "}";
		
		String name = t.getSimpleName() + "Wrapper.java";
		File file = new File(name);
		try {
			FileOutputStream stream = new FileOutputStream(file);
			byte[] textInBytes = text.getBytes();
			stream.write(textInBytes);
			stream.flush();
			stream.close();
			System.out.println("FinalClassWrapGenerator: " + name + " file added to project folder.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String addAllAbstractMethods(Class<?> t) {
		String text = "";
		Method[] methods = t.getSuperclass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			int mods = methods[i].getModifiers();
			if ((mods & Modifier.ABSTRACT) == Modifier.ABSTRACT) {
				String returnType = methods[i].getReturnType().getSimpleName();
				text += "\n\t@Override\n"
						+ "\tpublic " + returnType + " " + methods[i].getName() + "(" + addMethodParams(methods[i]) + ") {\n"
							+ "\t\t" + (returnType.equals("void") ? "" : "return ") + "item." + methods[i].getName() + "(";
				for (int j = 0; j < methods[i].getParameterCount(); j++) {
					text += "arg" + j + (j == methods[i].getParameterCount() - 1 ? "" : ", ");
				}
				text += ");\n"
						+ "\t}\n";
			}
		}
		if (!(t instanceof Object)) {
			text += addAllAbstractMethods(t);
		}
		return text;
	}
	
	private static String addMethodParams(Method m) {
		String text = "";
		Class<?>[] params = m.getParameterTypes();
		for (int j = 0; j < params.length; j++) {
			text += params[j].getSimpleName() + " arg" + j + (j == params.length - 1 ? "" : ", ");
		}
		return text;
	}
}
