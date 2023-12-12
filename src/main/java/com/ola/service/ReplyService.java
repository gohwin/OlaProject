package com.ola.service;

import com.ola.entity.Community;
import com.ola.entity.Reply;

public interface ReplyService {
	Reply getReplyByReplyNo(Long replyNo);

	void addReply(Reply reply);

	void deleteReply(Long replyNo);

	Community getCommunityByReplyNo(Long replyNo);
}
