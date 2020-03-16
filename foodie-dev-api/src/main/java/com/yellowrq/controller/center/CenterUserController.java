package com.yellowrq.controller.center;

import com.sun.org.apache.regexp.internal.RE;
import com.yellowrq.bo.center.CenterUserBO;
import com.yellowrq.controller.BaseController;
import com.yellowrq.pojo.Users;
import com.yellowrq.resource.FileUpload;
import com.yellowrq.service.center.CenterUserService;
import com.yellowrq.utils.CookieUtils;
import com.yellowrq.utils.DateUtil;
import com.yellowrq.utils.JSONResult;
import com.yellowrq.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:CenterUserController
 * Package:com.yellowrq.controller.center
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/10 17:54
 */

@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "centerUserBO", value = "个人中心用户业务对象", required = true)
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {

        // 判断BindingResult是否保存错误的验证信息，如果有直接return
        if (result.hasErrors()) {
            Map<String, String> map = getError(result);
            return JSONResult.errorMap(map);
        }
        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);
        // to后续要改,增加令牌token，会整合进redis，分布式会话
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
            MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 定义头像保存地址
//        String filePath = IMAGE_USER_FACE_LOCATION;
        String filePath = fileUpload.getImageUserFaceLocation();
        String uploadPathPrefix = File.separator + userId;

        // 开始上传文件
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
                // 获得文件的上传名称
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {

                    // 文件重命名 huang-face.png --> ["huang-face","png"]
                    String[] fileNameArr = fileName.split("\\.");
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    if (!suffix.equalsIgnoreCase("png")
                            && !suffix.equalsIgnoreCase("jpg")
                            && !suffix.equalsIgnoreCase("jpeg")) {
                        return JSONResult.errorMsg("图片格式不正确!");
                    }

                    // face-{userId}.png 文件名重组 覆盖式上传【增量式：额外拼接当前时间】
                    String newFileName = "face-" + userId + "." + suffix;

                    // 上传头像的最终保存位置
                    String finalFacePath = filePath + uploadPathPrefix + File.separator + newFileName ;

                    // 用于提供给web服务的访问地址
                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    // 文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return JSONResult.errorMsg("文件不能为空!");
        }
        // 获取图片地址
        String imageServerUrl = fileUpload.getImageServerUrl();
        //由于浏览器可能存在缓存情况，所以在这里需要加时间戳，以保证更新后的图片可以及时刷新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        // 更新用户头像到数据库
        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);

        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);
        // to后续要改,增加令牌token，会整合进redis，分布式会话
        return JSONResult.ok();
    }



    private Users setNullProperty(Users userResult){
        userResult.setCreatedTime(null);
        userResult.setRealname(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        return userResult;
    }

    private Map<String, String> getError(BindingResult result){
        HashMap<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            //对应的错误属性
            String errorField = error.getField();
            //对应的错误信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }
}
