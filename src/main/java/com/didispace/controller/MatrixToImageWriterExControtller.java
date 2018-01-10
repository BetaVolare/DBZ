package com.didispace.controller;


import JUtils.QRCode.MatrixToImageConfig;
import JUtils.QRCode.MatrixToImageWriter;
import JUtils.QRCode.MatrixToImageWriterEx;
import JUtils.QRCode.MatrixToLogoImageConfig;
import com.didispace.base.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import config.ResponseCodeCanstants;
import config.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Api(value = "QR", description = "二维码模块")
@RestController
@RequestMapping(value = "/QR/QR")
@ResponseBody
public class MatrixToImageWriterExControtller {

    @RequestMapping(value = {"/CreateQR"}, method = RequestMethod.GET)
    @ApiOperation(value = "生成二维码", notes = "生成二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "二维码内容", name = "content", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "二维码名称", name = "contentName", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "二维码格式", name = "format", required = true, dataType = "String", paramType = "query",defaultValue = "jpg"),
            @ApiImplicitParam(value = "宽度", name = "width", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "高度", name = "height", required = true, dataType = "Integer", paramType = "query"),

    })
    public ResponseResult CreateQR(String content,String contentName,String format, int width, int height) throws WriterException, IOException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
        MatrixToLogoImageConfig logoConfig = new MatrixToLogoImageConfig();
//        writeToFile(bitMatrix,"jpg","D:\\picss\\"+ content+".jpg","D:\\picss\\123.jpg",logoConfig);
        File outputFile = new File("d:" + File.separator + contentName + ".jpg");
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS, "成功");
    }

    @ApiOperation(value = "二维码解码", notes = "二维码解码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Token验证码", name = "Authorization", paramType = "header"),
    })
    @RequestMapping(value = "/parseQR", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult parseQR(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(access = "internal") @RequestParam("QR") MultipartFile uploadfile) {
        String content = "";
        try {
//            File file = new File(filePath);
//            BufferedImage image = ImageIO.read(file);
            BufferedImage image = getNewImage(uploadfile);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            MultiFormatReader formatReader = new MultiFormatReader();
            Result result = formatReader.decode(binaryBitmap, hints);
            System.out.println("result 为：" + result.toString());
            System.out.println("resultFormat 为：" + result.getBarcodeFormat());
            System.out.println("resultText 为：" + result.getText());
            content = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseCodeCanstants.FAILED, e.getMessage(),"解析失败");
        }
        System.out.print("==============================================================" + content);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,content,"解析成功");
    }

    //MultipartFile转image
    private static BufferedImage getNewImage(MultipartFile oldImage) throws IOException {
        /*srcURl 原图地址；deskURL 缩略图地址；comBase 压缩基数；scale 压缩限制(宽/高)比例*/
        ByteArrayInputStream bais = new ByteArrayInputStream(oldImage.getBytes());
        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
        Image src = ImageIO.read(mciis);
        return toBufferedImage(src);
    }

    //image 转StringBuffeImage
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }


    /**
     * 写入二维码、以及将照片logo写入二维码中
     * @param matrix     要写入的二维码
     * @param format     二维码照片格式
     * @param imagePath  二维码照片保存路径
     * @param logoPath   logo路径
     * @param logoConfig logo配置对象
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) throws IOException {
        MatrixToImageWriter.writeToFile(matrix, format, new File(imagePath), new MatrixToImageConfig());
        //添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
        BufferedImage img = ImageIO.read(new File(imagePath));
        MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, logoConfig);
    }

}
