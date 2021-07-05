package com.pblogteam.pblog.controller;

import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.service.UserService;
import com.pblogteam.pblog.util.FtpUtil;
import com.pblogteam.pblog.vo.ResultVO;
import com.pblogteam.pblog.vo.UserNewVO;
import com.pblogteam.pblog.vo.UserVO;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
public class UserController
{
    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //@GetMapping
    //@PutMapping
    //@DeleteMapping
    //@RequestMapping(value = "/user/login", method = RequestMethod.POST)  等价于@PostMapping
    @PostMapping("/user/signin")
    public ResultVO<UserVO> signin(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   Map<String, Object> map,
                                   HttpServletRequest request, HttpServletResponse response)
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        UserVO userVO = userService.findByUserName(username);
        if (userVO == null)
        {
            return ResultVO.throwError(400, "用户名不存在");
        }
        else if (!userService.checkPassword(userVO, password))
        {
            return ResultVO.throwError(400, "密码错误");
        }
        else
        {
            HttpSession session = request.getSession();
            System.out.println("signin: " + session.getId());
            session.setAttribute("userId", userVO.getId());
            session.setAttribute("privilege", userVO.getPrivilege());
            stringRedisTemplate.opsForValue().set("User" + userVO.getId(), session.getId());
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, userVO);
        }
    }

    @PostMapping(value = {"/admin/signin", "/admin"})
    public ResultVO<UserVO> signin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        UserVO userVO = userService.findByUserName(username);
        if(userVO.getPrivilege() == 0) {
            return ResultVO.throwError(400, "没有权限");
        }else if(userVO == null) {
            return ResultVO.throwError(400, "用户名不存在");
        }else if(!userService.checkPassword(userVO, password)) {
            return ResultVO.throwError(400, "密码错误");
        }else {
            HttpSession session = request.getSession();
            System.out.println("管理员signin: " + session.getId());
            session.setAttribute("userId", userVO.getId());
            session.setAttribute("adminId", userVO.getId());
//            session.setAttribute("privilege", userVO.getPrivilege());
            stringRedisTemplate.opsForValue().set("User" + userVO.getId(), session.getId());
            stringRedisTemplate.opsForValue().set("admin" + userVO.getId(), session.getId());
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, userVO);
        }
    }

    @GetMapping("/user/findByUserId")
    public ResultVO<UserVO> findByUserId(@RequestParam("id") Integer id,
                                         HttpSession session)
    {
        UserVO userVO = userService.findByUserId(id, (Integer) session.getAttribute("userId"));
        if (userVO == null)
            return ResultVO.throwError(400, "用户id不存在");
        else {
            System.out.println(userVO);
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, userVO);
        }
    }

    @PostMapping(value = {"/user/signout", "/admin/signout"})
    public ResultVO signout(HttpServletRequest request)
    {
        // 退出登录
        request.getSession().removeAttribute("userId");
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @PostMapping("/user/signup")
    public ResultVO signup(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") @Email String email,
                           HttpServletRequest request,HttpServletResponse response)
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        if (userService.signup(username, password, email))
        {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        }
        else
        {
            return ResultVO.throwError(400, "用户名已存在");
        }
    }

    @GetMapping("/users/attentionList")
    public ResultVO<PageInfo<UserVO>> attentionList(@RequestParam("id") Integer id, int pageNum)
    {
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, userService.myAttentionList(id, pageNum));
    }

    @PutMapping("/user/changeAttention")
    public ResultVO changeAttention(@RequestParam("id") Integer id,
                                    HttpServletRequest request)
    {
        userService.changeAttention(id,(Integer) request.getSession().getAttribute("userId"));
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @PutMapping("/user/updateinfo")
    public ResultVO updateInfo(@RequestBody UserNewVO userNewVO, HttpSession session)
    {
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("userId= " +userId);
        System.out.println(userNewVO);
        if(!userService.checkNewUsernameLegality(userId, userNewVO))
        {
            return ResultVO.throwError(400, "用户名已存在");
        }
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, userService.updateInfo(userId, userNewVO));
    }

    @PostMapping("/user/changePhoto")
    public ResultVO<Boolean> changePhoto(@RequestParam("photo") MultipartFile file,
                                         HttpSession session) throws IOException
    {
        InputStream inputStream = file.getInputStream();

        String filename = file.getOriginalFilename();
        //截取后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        //使用UUID拼接后缀，定义一个不重复的文件名
        String finalname = UUID.randomUUID() + suffix;

        boolean flag = FtpUtil.uploadFile(finalname, inputStream);
        if (flag == true)
            userService.changePhoto((Integer) session.getAttribute("userId"), finalname);
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, flag);
    }
    @GetMapping("/admin/changeUserPrivilege")
    public ResultVO changeUserPrivilege(Integer id, HttpServletRequest request) {
        int curId = (int) request.getSession().getAttribute("userId");
        if(id == curId) {
            return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
        }
        userService.changeAdmin(id);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @GetMapping("/user/showPhotoById")
    public void showPicture(HttpServletRequest request, HttpServletResponse response, @RequestParam("userId") int userId)
    {
        FTPClient ftp = null;
        InputStream in = null;
        OutputStream os = null;
        String imgPath = userService.selectByPrimaryKey(userId).getPhotoUrl();
        try
        {
            ftp = FtpUtil.initFTP(ftp);
            in = ftp.retrieveFileStream(new String(imgPath.getBytes("UTF-8"), "iso-8859-1"));
            String picType = imgPath.split("\\.")[1];
            BufferedImage bufImg = null;
            bufImg = ImageIO.read(in);
            os = response.getOutputStream();
            ImageIO.write(bufImg, picType, os);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                    FtpUtil.destroy(ftp);
                }
                catch (IOException e)
                {
                }
            }
        }
    }
}
