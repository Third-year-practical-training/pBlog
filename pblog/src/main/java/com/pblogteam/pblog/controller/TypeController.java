package com.pblogteam.pblog.controller;

import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.entity.ArticleType;
import com.pblogteam.pblog.service.impl.TypeServiceImpl;
import com.pblogteam.pblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/")
public class TypeController {
    @Autowired
    private TypeServiceImpl typeServiceImpl;

    @GetMapping("/type/findall")
    public ResultVO<List<ArticleType>> getArticleTypeList() {
        List<ArticleType> articleTypeList = typeServiceImpl.ListType();
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTypeList);
    }

    @PutMapping("/admin/updateTypeById")
    public ResultVO updateTypeById(Integer id, String type, HttpSession session) {
        if (typeServiceImpl.updateTypeById(type, id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        }
    }

    @DeleteMapping("/admin/deleteTypeById")
    public ResultVO deleteTypeById(Integer id) {
        if (typeServiceImpl.deleteTypeById(id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        }
    }

    // 增加type
    @PutMapping("/admin/saveType")
    public ResultVO saveType(String type) {
        if (typeServiceImpl.saveType(type)) {
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
