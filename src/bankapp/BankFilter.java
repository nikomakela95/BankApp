package bankapp;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class BankFilter extends FileFilter {

	//Method for accepting the file we are opening
	@Override
	public boolean accept(File f) {
		if(f.isDirectory() || f.getName().toLowerCase().endsWith(".bof")){
			return true;
		}else{
			return false;
		}
				
	}

	@Override
	public String getDescription() {
		return "Bank object file";
	}

}
