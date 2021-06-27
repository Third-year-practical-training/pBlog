package com.pblogteam.pblog.controller;

import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.entity.ArticleType;
import com.pblogteam.pblog.service.impl.TypeServiceImpl;
import com.pblogteam.pblog.service.impl.UserServiceImpl;
import com.pblogteam.pblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/")
public class TypeController {
    @Autowired
    private TypeServiceImpl typeServiceImpl;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/type/findall")
    public ResultVO<List<ArticleType>> getArticleTypeList() {
        List<ArticleType> articleTypeList = typeServiceImpl.ListType();
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTypeList);
    }

    @GetMapping("/type/updateTypeById")
    public ResultVO updateTypeById(Integer id, String type, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        if(!userService.isAdmin(userId)) return ResultVO.throwError(ResponseState.SIGNATURE_NOT_MATCH);
        if(typeServiceImpl.updateTypeById(type, id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        }
    }

    @GetMapping("/type/deleteTypeById")
    public ResultVO deleteTypeById(Integer id) {
        if(typeServiceImpl.deleteTypeById(id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        }
    }

    // 增加type
    @GetMapping("/type/saveType")
    public ResultVO saveType(String type, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        if(!userService.isAdmin(userId)) return ResultVO.throwError(ResponseState.SIGNATURE_NOT_MATCH);
        if(typeServiceImpl.saveType(type)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        }
    }

    @GetMapping("/type/findTypeByName")
    public ResultVO<ArticleType> findTypeByName(String type) {
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, typeServiceImpl.findTypeByName(type));
    }
}
