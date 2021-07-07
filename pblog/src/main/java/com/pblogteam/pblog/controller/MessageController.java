package com.pblogteam.pblog.controller;

import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.entity.Message;
import com.pblogteam.pblog.entity.User;
import com.pblogteam.pblog.service.BlackListService;
import com.pblogteam.pblog.service.MessageService;
import com.pblogteam.pblog.vo.ResultVO;
import com.pblogteam.pblog.vo.UserVO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private BlackListService blackListService;

    @GetMapping("/message/find")
    public ResultVO<PageInfo<Message>> findMessage(Integer fromId, Integer toId, Integer pageNum, HttpServletRequest request) {
        if (!isMyId(fromId, request)) return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, messageService.findMessage(fromId, toId, pageNum));
    }

    @GetMapping("/message/getMyUser")
    public ResultVO<List<UserVO>> findMyUser(Integer id, HttpServletRequest request) {
        if(!isMyId(id, request)) return ResultVO.throwError(ResponseState.SIGNATURE_NOT_MATCH);
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, messageService.findMyUser(id));
    }


    @PutMapping("/message/new")
    public ResultVO newMessage(Message message, HttpServletRequest request) {
        if (!isMyId(message.getFromId(), request)) {
            return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        }
        if (blackListService.isInMyBlackList(message.getFromId(), message.getToId())) {
            return ResultVO.throwError(400, "您已被拉黑");
        }
        messageService.addMessage(message);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @DeleteMapping("/message/delete")
    public ResultVO deleteMessage(Integer id, HttpServletRequest request) {
        if (!isMyId(id, request)) return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        messageService.deleteMessage(id);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    public boolean isMyId(Integer id, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        return userId.equals(id);
    }
}
