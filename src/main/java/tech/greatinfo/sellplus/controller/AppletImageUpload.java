package tech.greatinfo.sellplus.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.config.StaticConfig;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.SellerCode;
import tech.greatinfo.sellplus.repository.SellerCodeRepository;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.DateUtil;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 小程序端图片上传
 */
@RestController
public class AppletImageUpload {

    private static final Logger logger = LoggerFactory.getLogger(AppletImageUpload.class);

    private final TokenService tokenService;

    private final SellerCodeRepository sellerCodeRepository;

    @Autowired
    public AppletImageUpload(TokenService tokenService, SellerCodeRepository sellerCodeRepository) {
        this.tokenService = tokenService;
        this.sellerCodeRepository = sellerCodeRepository;
    }


}
