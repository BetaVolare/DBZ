package com.didispace.controller.upload;

import config.ResponseCodeCanstants;
import config.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

@Api(value = "/upload/upload", description = "文件上传")
@RestController
//@RequestMapping("/api/upload")
@RequestMapping("/upload/upload")
public class FileUploadController {

    @Value("${server.context.url}")
    private String server_url;

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Token验证码", name = "Authorization", paramType = "header"),
    })
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult uploadFile(
            HttpServletRequest request,
            HttpServletResponse response,
            @ApiParam(access = "internal") @RequestParam("uploadfile") MultipartFile uploadfile) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String directory = "/uploads/pictures";
            String filepath = Paths.get(directory).toString();
            /** 得到文件保存目录的真实路径**/
            String logoRealPathDir = request.getSession().getServletContext().getRealPath(filepath);
            File saveFile = new File(logoRealPathDir);
            if (!saveFile.exists()){
                saveFile.mkdirs();
            }uploadfile.getName();
            String result = directory+"/"+executeUpload(logoRealPathDir, uploadfile);
            System.out.println("上传路径="+result);
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,result,"上传成功");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseResult(ResponseCodeCanstants.FAILED,e.getMessage(),"上传失败");
        }
    }

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Token验证码", name = "Authorization", paramType = "header"),
    })
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult uploadFiles(
            HttpServletRequest request,
            HttpServletResponse response,
            @ApiParam(access = "internal") @RequestParam("uploadfile") MultipartFile[] uploadfile) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String directory = "/uploads/picture";
            String filepath = Paths.get(directory).toString();
            /** 得到文件保存目录的真实路径**/
            String logoRealPathDir = request.getSession().getServletContext()
                    .getRealPath(filepath);
            File saveFile = new File(logoRealPathDir);
            if (!saveFile.exists()){
                saveFile.mkdir();
            }
            StringBuffer resultPaths = new StringBuffer();
            for (int i = 0; i < uploadfile.length; i++) {
                if (uploadfile[i] != null){
                    String result = executeUpload(logoRealPathDir, uploadfile[i]);
                    resultPaths.append(directory+"/"+result+",");
                }
            }
            System.out.println("上传路径="+resultPaths.toString());
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,resultPaths.toString(),"上传成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseCodeCanstants.FAILED,"上传失败");
        }
    }

    /**
     * 上传方法
     * @param uploadDir
     * @param file
     * @return
     * @throws Exception
     */
    private String executeUpload(String uploadDir,MultipartFile file) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        String filename = sdf.format(new Date())+UUID.randomUUID()+suffix;
        String filename = file.getOriginalFilename();
        File serverFile = new File(uploadDir+"/"+filename);
        file.transferTo(serverFile);
        return filename;
    }
}
