package com.shop.api.utils.QrCode;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


/**
 * 二维码生成工具类
 * @author wel.xiao
 * @date 2015-4-8
 *
 */

public class QrCodeUtil {

    
    /**
     * 二维码生成
     * @param contents 内容
     * @param width 图片宽度
     * @param height 图片高度
     * @param imgPath 图片路径
     * @return 二维码图片
     */
    public static File encode(String contents, int width, int height, String imgPath) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        try {
            File file = new File(imgPath);
            File filePath = new File(file.getParent());
            if(!filePath.exists()  && !filePath.isDirectory()){
            	filePath.mkdirs();
            }
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToPath(bitMatrix, "png", file.toPath());
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        QrCodeUtil.encode("http://www.baidu.com", 100, 100, "d:/aaaaaa.png");
    }
}
