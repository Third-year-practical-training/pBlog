package com.pblogteam.pblog.controller;

import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.entity.Notice;
import com.pblogteam.pblog.service.NoticeService;
import com.pblogteam.pblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/admin/notice/getAll")
    public ResultVO<PageInfo<Notice>> getAllNotice(Integer pageNum) {
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, noticeService.getAllNotice(pageNum));
    }

    /**
     *
     * @param notice, 只需要传入date，content
     * @return
     */
    @PutMapping("/admin/notice/new")
    public ResultVO addNotice(Notice notice) {
        noticeService.publicNotice(notice);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @DeleteMapping("/admin/notice/delete")
    public ResultVO deleteNotice(Integer id) {
        if(noticeService.deleteNotice(id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        }else{
            return ResultVO.throwError(ResponseState.NOT_FOUND);
        }
    }
}
