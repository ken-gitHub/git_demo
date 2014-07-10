package com.wk.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class HelloLucene {

	private IndexSearcher searcher;

	/**
	 * 建立索引
	 * 
	 * @throws IOException
	 * @throws LockObtainFailedException
	 * @throws CorruptIndexException
	 */
	public void index() {
		// 创建一个Directory
		// Directory dir = new RAMDirectory();// 创建到内存中
		// 创建到磁盘上
		Directory dir = null;
		IndexWriter writer = null;
		try {
			dir = FSDirectory.open(new File("D:/lucene/index"));

			// 创建IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new StandardAnalyzer(Version.LUCENE_35));
			writer = new IndexWriter(dir, iwc);

			// 创建Document对象
			Document doc = null;

			File f = new File("D:/lucene/temp");

			for (File file : f.listFiles()) {
				doc = new Document();
				doc.add(new Field("content", new FileReader(file)));
				// Field.Index.NOT_ANALYZED 是否需要进行分词
				// Field.Store.YES 是否把全名存储到磁盘中
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("path", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				writer.addDocument(doc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(writer != null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public void Search(String searyKey){
		
		try {
			Directory dir = FSDirectory.open(new File("D:/lucene/index"));
			
			IndexReader reader = IndexReader.open(dir);
			
			searcher = new IndexSearcher(reader);
			
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
			
			Query query = parser.parse(searyKey);
			
			TopDocs tds =  searcher.search(query, 100);
			
			ScoreDoc[] s =  tds.scoreDocs;
			
			for (ScoreDoc sd:s) {
				Document document =  searcher.doc(sd.doc);
				System.out.println(document.get("filename")+"["+document.get("path")+"]");
			}
			// close reader
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
