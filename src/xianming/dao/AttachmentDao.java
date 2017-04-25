package xianming.dao;


import org.springframework.stereotype.Repository;

import xianming.model.Attachment;

@Repository("attachmentDao")
public class AttachmentDao extends BaseDao<Attachment> implements IAttachmentDao {

}
