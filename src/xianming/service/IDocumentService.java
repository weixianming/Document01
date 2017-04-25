package xianming.service;

import java.io.IOException;
import java.util.List;

import xianming.dto.AttachDto;
import xianming.model.Attachment;
import xianming.model.Document;
import xianming.model.Pager;

public interface IDocumentService {
	public void add(Document doc,Integer[] depIds,AttachDto ad) throws IOException;
	
	
	public void delete(int id);
	
	public Document updateRead(int id,Integer isRead);
	
	/**
	 * 获取某个用所发送的公文
	 * @param userId 
	 * @return
	 */
	public Pager<Document> findSendDocument(int userId);
	
	/**
	 * 获取某个部门中的公文
	 * @param con 查询条件
	 * @param depId 某个部门，如果为0或者null表示所有部门
	 * @return
	 */
	public Pager<Document> findReadDocument(String con,Integer depId);
	
	/**
	 * 获取某个部门中的未读公文
	 * @param con
	 * @param depId
	 * @return
	 */
	public Pager<Document> findNotReadDocument(String con,Integer depId);
	
	/**
	 * 获得某个公文的附件信息
	 * @param docId
	 * @return
	 */
	public List<Attachment> listAttachmentByDocument(int docId);
}
