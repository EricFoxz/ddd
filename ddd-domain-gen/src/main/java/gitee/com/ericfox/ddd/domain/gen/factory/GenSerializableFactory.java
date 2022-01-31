package gitee.com.ericfox.ddd.domain.gen.factory;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.XmlUtil;
import org.w3c.dom.Document;

import java.io.File;

public abstract class GenSerializableFactory {
    /**
     * 从文件反序列化
     */
    public abstract Document deserialization(File file);

    /**
     * 序列化xml数据
     */
    public abstract String serializationMeta(Document document);

    public static GenSerializableFactory getDefaultInstance() {
        return new GenSerializableFactory() {
            @Override
            public Document deserialization(File file) {
                return XmlUtil.readXML(file);
            }

            @Override
            public String serializationMeta(Document document) {
                return XmlUtil.mapToXmlStr(XmlUtil.xmlToMap(document));
            }
        };
    }
}