package gitee.com.ericfox.ddd.domain.gen.factory;


import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.XmlUtil;
import lombok.SneakyThrows;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class GenDocumentFactory {
    private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    /**
     * 从文件解析
     *
     * @param file
     * @return
     */
    @SneakyThrows
    public Document deserialization(File file) {
//        XmlUtil.rea;
//        documentBuilderFactory.setSchema();
//        return documentBuilderFactory.newDocumentBuilder().parse(file);
        return XmlUtil.readXML(file);
    }
}
