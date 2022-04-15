package com.sharing;

import com.sharing.Utils.IllegalWordDisposeUtil;
import com.sharing.Utils.TextTypeFileUtil;
import com.sharing.config.MyEmailSenderConfig;
import com.sharing.mapper.UserMapper;
import com.sharing.pojo.Focus;
import com.sharing.pojo.UserAndResource;
import com.sharing.service.FocusService;
import com.sharing.service.ResourceDetailService;
import com.sharing.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
//@MapperScan("com.sharing.mapper")
class CampusresourcemanagerApplicationTests {

    @Autowired
    private FocusService focusService;

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyEmailSenderConfig sender;

    @Autowired
    private ResourceDetailService resourceDetailService;

    @Test
    void contextLoads() throws Exception {

//        org.apache.poi.poifs.filesystem.OfficeXmlFileException
//        org.apache.poi.POIXMLException
        String word = "D:\\基地项目\\项目二\\1.需求分析说明书（模板）.doc";
        String wordx = "D:\\基地项目\\项目二\\项目介绍模板.docx";
        String ppt = "D:\\基地项目\\项目二\\2018401329-李福生-校园资料分享平台设计与实现.ppt";
        String pptx = "D:\\基地项目\\项目二\\2018401329-李福生-校园资料分享平台设计与实现.pptx";
        String pdf = "D:\\基地项目\\项目二\\24365智慧就业操作.pdf";
        String vsdx = "D:\\基地项目\\项目二\\用例图.vsdx";
        String vsd = "D:\\基地项目\\项目二\\用例图.vsd";

        String string = TextTypeFileUtil.getFileString(pdf, TextTypeFileUtil.PDF);
        System.out.println(string);
//        System.out.println(TextTypeFileUtil.pptxString(ppt));


    }

}
