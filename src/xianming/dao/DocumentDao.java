package xianming.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import xianming.model.Document;
import xianming.model.Pager;

@Repository("documentDao")
public class DocumentDao extends BaseDao<Document> implements IDocumentDao {


}
