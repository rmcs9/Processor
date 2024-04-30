package ICSI404;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class AssemblerMain{
	public static void main(String[] args){
		if(args.length != 2){
			throw new RuntimeException("invalid arguments provided... must provide [ASM FILE], [OUTPUT FILE NAME]");
		}

		String asmFile = args[0];
		String outputFile = args[1];
		Path filepath = Paths.get(asmFile);
		String filestring;
		try{
			filestring = new String(Files.readAllBytes(filepath));
		}
		catch(Exception e){
			throw new RuntimeException(e);	
		}
		Lexer lexer = new Lexer(filestring);		
		lexer.Lex();

		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructions = parser.parse();
		
		File instructFile = new File(outputFile);
		try{
			if(instructFile.createNewFile()){
				System.out.println("instruction file created: " + instructFile.getName());
			}
			FileWriter fw = new FileWriter(outputFile);
			for(String instruct : instructions){
				fw.write(instruct + "\n");
			}
			fw.close();
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		System.out.println("instructions written to file: " + outputFile);
	}
}
