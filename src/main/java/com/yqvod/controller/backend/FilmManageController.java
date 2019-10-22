package com.yqvod.controller.backend;

import com.google.common.collect.Maps;
import com.yqvod.common.Const;
import com.yqvod.common.ResponseCode;
import com.yqvod.common.ServerResponse;
import com.yqvod.pojo.Film;
import com.yqvod.pojo.User;
import com.yqvod.service.IFileService;
import com.yqvod.service.IFilmService;
import com.yqvod.service.IUserService;
import com.yqvod.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName $ {NAME}
 * @Description TODO
 * @Author fjw
 * @Date 2019/10/21 2:44 PM
 * @Version 1.0
 **/
@Controller
@RequestMapping("/manage/film")
public class FilmManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFilmService iFilmService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse filmSave(HttpSession session, Film film){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加影片的业务逻辑
            return iFilmService.saveOrUpdateFilm(film);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");

        }

    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer filmId, Integer status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iFilmService.setSaleStatus(filmId,status);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");

        }

    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer filmId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iFilmService.manageFilmDetail(filmId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");

        }

    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1")int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iFilmService.getFilmList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");

        }

    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse filmSearch(HttpSession session,String filmName,Integer filmId, @RequestParam(value = "pageNum",defaultValue = "1")int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iFilmService.searchFilm(filmName,filmId,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");

        }

    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);

        }else {

            return ServerResponse.createByErrorMessage("无权限操作");

        }

    }


}
