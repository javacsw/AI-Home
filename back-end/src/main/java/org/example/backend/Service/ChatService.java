package org.example.backend.Service;

import org.example.backend.Entity.pojo.*;

import java.util.Date;
import java.util.List;

public interface ChatService {
    Integer AddChat(FriendChat friendChat);
    List<FriendChat> GetChatById(Integer sender, Integer toUserId);
    Integer CreateGroup(GroupInformation groupInformation);
    Integer JoinGroup(Integer groupId, Integer userId, Date time);
    List<GroupInformationAndUser> GetGroupById(Integer userId);
    Integer SendMessageToGroup(GroupMessage groupMessage);
    List<User> GetGroupMember(Integer groupId);
    List<GroupMessage> GetGroupMessageByGroupId(Integer groupId);
    List<FriendChat> GetFriendChatByToUserId(Integer toUserId);
    Integer ReadFriendChat(Integer sender,Integer toUserId);
}