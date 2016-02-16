package sassy.asm.ui;

import java.io.File;
import java.util.ArrayList;

public class FetchClasses {
	// static String sourceDir = "C:/EclipseWorkspaces/CSSE374/SassyASM/src";

	// public static void main(String[] args){
	// String[] arr = getClasses(sourceDir, new File(sourceDir));
	// for(int i=0;i<arr.length;i++){
	// System.out.println(arr[i]);
	// }
	// }


	private static ArrayList<String> toArrayHelper(String sourceDir, File dir) {
		ArrayList<String> classList = new ArrayList<String>();
		for (String name : dir.list()) {
			if (name.contains("Test")) {
				continue;
			}
			if (isJavaClass(name)) {
				// TODO FIXME: this is weird a weird fix, maybe there is another
				// way to remove previous patterns
				int index = dir.getPath().lastIndexOf(sourceDir)
						+ sourceDir.length();
				String s = dir.getPath().substring(index).replace("\\", ".")
						.substring(1)
						+ "." + name.replaceAll(".java", "");
				classList.add(s);
				continue;
			}
			File tempFile = new File(dir.getPath() + "\\" + name);
			if (tempFile.isDirectory()) {
				classList.addAll(toArrayHelper(sourceDir, tempFile));
			}
		}
		return classList;
	}

	public static String[] getClasses(String sourceDir, File dir) {
		ArrayList<String> classList = toArrayHelper(sourceDir, dir);
		String[] newArray = new String[classList.size()];
		newArray = classList.toArray(newArray);
		return newArray;
	}

	private static Boolean isJavaClass(String name) {
		return name.endsWith(".java");
	}
}

// import java.io.File;
// import java.util.ArrayList;
// import java.util.List;
//
// public class FetchClasses {
//
// static String sourceDir = "C:/EclipseWorkspaces/CSSE374/SassyASM/src";
//
// public static void main(String[] args){
// String[] arr = getClasses(sourceDir, new File(sourceDir));
// for(int i=0;i<arr.length;i++){
// System.out.println(arr[i]);
// }
// }
// private static ArrayList<String> toArrayHelper(String sourceDir, File dir,
// String[] validPackages) {
// boolean checkPackages = true;
// if (validPackages[0].equals("")) {
// checkPackages = false;
// }
//
// ArrayList<String> classList = new ArrayList<String>();
// for (String name : dir.list()) {
// if (isJavaClass(name)) {
//
// //TODO FIXME: this is weird a weird fix, maybe there is another way to remove
// previous patterns
// int index = dir.getPath().lastIndexOf(sourceDir)+sourceDir.length();
// String className = dir.getPath().substring(index).replace("\\",
// "/").substring(1)+"/"+name.replaceAll(".java", "");
//
// if (!checkPackages) {
// classList.add(className);
// } else if(isValidPackageName(className, validPackages)) {
// classList.add(className);
// }
//
// } else {
// File tempFile = new File(dir.getPath() + "\\"+name);
// if (tempFile.isDirectory()) {
// classList.addAll(toArrayHelper(sourceDir, tempFile, validPackages));
// }
// }
// }
//
// return classList;
// }
//
// public static List<String> getClasses(String sourceDir, String[]
// packageNames){
// File f = new File(sourceDir);
// if (!f.exists()) {
// System.err.println("Source directory does not exist: " + sourceDir);
// return new ArrayList<String>();
// }
//
// ArrayList<String> classList = toArrayHelper(sourceDir, f, packageNames);
// return classList;
// }
//
// private static boolean isValidPackageName(String name, String[]
// validPackages) {
// for (String pack : validPackages) {
// pack = pack.replace(".", "/");
//
// if (name.contains(pack + "/"))
// return true;
// }
//
// return false;
// }
//
// private static boolean isJavaClass(String name) {
// return name.endsWith(".java");
// }
// }

