package com.terrasky.sod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * SkyOnDemandからプロジェクトをダウンロード（ZIP形式）し、解凍する。
 * そのディレクトリ下にあるスクリプトファイルについて差分比較できるファイルに変換する。
 * 生成した結果は、サフィックスが.comparableとなって出力される。
 * 
 * @author Satoshi Takezawa of TerraSky Co., Ltd.
 */
public class Crawler {
	/** スタイルシート */
	public static final String XSL_PATH = Crawler.class.getResource("style.xsl").toString();
	/** 変換後のサフィックス */
	public static final String PURIFIED_SUFFIX = ".comparable";
	
	/**
	 * メインプログラム
	 * @param args プロジェクトを解凍したパス
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Error: Target directory must be specified.");
			System.exit(1);
		} 
		checkFilesUnder(args[0]);
	}
	
	/**
	 * path下のファイル群について、スクリプトファイルであれば差分比較できるファイルに変換する。
	 * 
	 * @param path
	 */
	public static void checkFilesUnder(String path) {
		String[] files = new File(path).list();
		if(files != null) {	//means path is not a file
			for(String file : files) {
				File target = new File(path + "/" + file);
				if(target.isFile() && isScriptFile(target.getName())) {
					try {
						transform(XSL_PATH, target.getAbsolutePath(), target.getAbsolutePath() + PURIFIED_SUFFIX);
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else if(target.isDirectory()){
					checkFilesUnder(target.getAbsolutePath());
				}
			}
		}
	}
	
	/**
	 * スクリプトファイルかどうかを判定する。
	 * 
	 * @param name 判定するファイル名
	 * @return スクリプトファイルの場合、true
	 */
	private static boolean isScriptFile(String name) {
		return name.endsWith(".script") 
			|| name.endsWith(".conf")
			|| name.equals("project.xml");
	}
	
	/**
	 * スタイルシートを元にXMLを変換する。
	 * @param xslFile XSLファイル
	 * @param sourceFile 変換元XMLのパス
	 * @param destFile 変換したものを出力するXMLのパス
	 * @throws FileNotFoundException ファイルが見つからない場合
	 * @throws TransformerException 変換エラー
	 */
	public static void transform(String xslFile, String sourceFile, String destFile) 
			throws FileNotFoundException, TransformerException { 
		StreamSource xsl = new StreamSource(xslFile);
		StreamSource source  = new StreamSource(sourceFile);
		StreamResult dest  = new StreamResult(new FileOutputStream(destFile));
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(xsl);
		transformer.transform(source, dest);
	}
}
