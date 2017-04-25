package xianming.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xianming.dto.AttachDto;
import xianming.model.Document;
import xianming.model.SystemContext;
import xianming.model.User;
import xianming.service.IDocumentService;
import xianming.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestDocument {

	@Resource(name="userService")
	private IUserService userService;
	
	@Resource(name="documentService")
	private IDocumentService documentService;
	
	@Before
	public void init(){
		User u = userService.load(2);
		SystemContext.setLoginUser(u);
	}
	
	@Test
	public void testAdd() {
		try {
			Integer[] depIds = new Integer[]{1,2,3,4};
			Document doc = new Document();
			doc.setContent("猪八戒");
			doc.setTitle("一只猪八戒");
			documentService.add(doc, depIds, new AttachDto(false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRead(){
		User u = userService.load(3);
		SystemContext.setLoginUser(u);
		Document doc = documentService.updateRead(1, 0);
		System.out.println(doc.getContent());
	}
	
	@Test
	public void testGetSendDocument(){
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		List<Document> docs = documentService.findSendDocument(2).getDatas();
		for(Document d:docs){
			System.out.println(d.getContent());
		}
	}
	
	@Test
	public void testGetReadDocument(){
		User u = userService.load(3);
		SystemContext.setLoginUser(u);
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		List<Document> docs = documentService.findReadDocument("小", 2).getDatas();
		for(Document d:docs){
			System.out.println(d.getContent());
		}
	}
	
	@Test
	public void testGetNotReadDocument(){
		User u = userService.load(3);
		SystemContext.setLoginUser(u);
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		List<Document> docs = documentService.findNotReadDocument("猪", 0).getDatas();
		for(Document d:docs){
			System.out.println(d.getContent());
		}
	}

}
