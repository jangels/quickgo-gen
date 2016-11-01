package com.qitoon.framework.view;


import com.qitoon.framework.param.Parameter;

import java.net.URLEncoder;

/**
 * @author : huangjie
 * @since : 16/9/7
 */
public class PdfView extends ByteArrayView{
    private String fileName;
    public PdfView(byte[] data,String fileName) {
        super(data);
        this.fileName = fileName;
    }

    public PdfView(byte[] data) {
        super(data);
    }



    @Override
    public void doRepresent(Parameter parameter) throws Exception {
        super.setHeader("Content-Type","application/pdf");
        super.setHeader("Content-Disposition","attachment; filename=\""+ URLEncoder.encode(fileName,"UTF-8")+"\"");
        super.doRepresent(parameter);
    }
}
