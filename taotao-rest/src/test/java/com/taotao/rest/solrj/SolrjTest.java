package com.taotao.rest.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {

	@Test
	public void addDucument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.130:8080/solr");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test004");
		document.addField("item_title", "修改商品4");
		document.addField("item_price", 54321);
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.130:8080/solr");
		//solrServer.deleteById("test004");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
	
	@Test
	public void queryDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.130:8080/solr");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setStart(20);
		query.setRows(5);
		QueryResponse response = solrServer.query(query);
		SolrDocumentList list = response.getResults();
		long numFound = list.getNumFound();
		System.out.println("共查询到： " + numFound + " 条记录");
		for (SolrDocument solrDocument : list) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
		}
	}
	
	
}
